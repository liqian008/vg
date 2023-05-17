package com.example.demo.processor.impl;

import com.example.demo.consts.Constants;
import com.example.demo.enums.DbTypeEnum;
import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.model.metadata.MetadataColumnVo;
import com.example.demo.model.metadata.MetadataDatabaseVo;
import com.example.demo.model.metadata.MetadataTableVo;
import com.example.demo.processor.IMetadataDbProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mysql元数据实现类
 *
 * @author liqian483
 */
@Slf4j
@Service(Constants.DB_TYPE_MSYQL + Constants.METADATA_PROCESSOR_SUFFIX)
public class MetadataMysqlProcessor implements IMetadataDbProcessor {

    /** 数据类型映射， TODO  */
    private static final Map<String, Class> DATA_TYPE_MAP = new HashMap<>();
    static{
        //常用基本类型

        //数字
        DATA_TYPE_MAP.put("bigint", java.lang.Long.class);
        DATA_TYPE_MAP.put("int", java.lang.Integer.class);
        DATA_TYPE_MAP.put("smallint", java.lang.Short.class);
        DATA_TYPE_MAP.put("tinyint", java.lang.Byte.class);

        //浮点
        DATA_TYPE_MAP.put("float", java.lang.Float.class);
        DATA_TYPE_MAP.put("double", java.lang.Double.class);

        //字符
        DATA_TYPE_MAP.put("varchar", java.lang.String.class);
        DATA_TYPE_MAP.put("text", java.lang.String.class);
        DATA_TYPE_MAP.put("mediumtext", java.lang.String.class);
        DATA_TYPE_MAP.put("longtext", java.lang.String.class);

        //时间
        DATA_TYPE_MAP.put("timestamp", java.util.Date.class);
        DATA_TYPE_MAP.put("date", java.util.Date.class);
        DATA_TYPE_MAP.put("datetime", java.util.Date.class);

        //其他类型暂时不做支持(均使用String类型)
    }

    @Override
    public DbTypeEnum getDbTypeEnum() {
        return DbTypeEnum.MYSQL;
    }


    /**
     *
     * @param dataSourceEntity
     * @return
     */
    @Override
    public Connection getConnection(DataSourceEntity dataSourceEntity) throws Exception {
        String jdbcUrl = dataSourceEntity.getJdbcUrl();
        String jdbcDriver = dataSourceEntity.getJdbcDriver();
        String username = dataSourceEntity.getUsername();
        String password = dataSourceEntity.getPassword();
        Class.forName(jdbcDriver);
        Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
        return conn;
    }


    @Override
    public List<MetadataDatabaseVo> listDatabases(DataSourceEntity dataSourceEntity) {
        log.info("[MetadataMysqlProcessor][listDatabases]enter, dataSourceEntity:{}", dataSourceEntity);
        List<MetadataDatabaseVo> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection(dataSourceEntity);
            ps = conn.prepareStatement("show databases");
            rs = ps.executeQuery();
            while(rs.next()){
                String dbName = rs.getString(1);
                MetadataDatabaseVo item = new MetadataDatabaseVo();
                item.setDbName(dbName);
                result.add(item);
            }
        } catch (Exception e) {
            log.error("[MetadataMysqlProcessor][listDatabases]error:{}, dataSourceEntity:{}", e, dataSourceEntity);
        }finally {
            releaseResource(conn, ps, rs);
        }
        log.info("[MetadataMysqlProcessor][listDatabases]result:{}, dataSourceEntity:{}", result, dataSourceEntity);
        return result;
    }


    @Override
    public List<MetadataTableVo> listUserTables(DataSourceEntity dataSourceEntity, String dbName) {
        log.info("[MetadataMysqlProcessor][listUserTables]enter, dbName:{}", dbName);
        List<MetadataTableVo> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        MetadataDatabaseVo databaseVo = MetadataDatabaseVo.builder().dbName(dbName).build();
        try {
            conn = getConnection(dataSourceEntity);
            StringBuilder sb = new StringBuilder("select TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT " +
                    "from information_schema.TABLES " +
                    "where TABLE_SCHEMA=?"
            );
            ps = conn.prepareStatement(sb.toString());
            ps.setString(1, dbName);
            rs = ps.executeQuery();
            while(rs.next()){
                MetadataTableVo vo = new MetadataTableVo();
                vo.setDatabase(databaseVo);
                vo.setTableName(rs.getString("TABLE_NAME"));
                vo.setRemark(rs.getString("TABLE_COMMENT"));
                result.add(vo);
            }
        } catch (Exception e) {
            log.error("[MetadataMysqlProcessor][listUserTables]error:{}, dbName:{}", e, dbName);
            e.printStackTrace();
        }finally {
            releaseResource(conn, ps, rs);
        }
        log.info("[MetadataMysqlProcessor][listUserTables]result:{}, dbName:{}", result, dbName);
        return result;
    }



    @Override
    public List<MetadataTableVo> listUserTables(DataSourceEntity dataSourceEntity, String dbName, Set<String> tableNameSet) {
        log.info("[MetadataMysqlProcessor][listUserTables]enter, dbName:{}, tableNameSet:{}", dbName, tableNameSet);

        List<MetadataTableVo> result = null;
        List<MetadataTableVo> metadataTableVos = listUserTables(dataSourceEntity, dbName);
        if(CollectionUtils.isNotEmpty(metadataTableVos)){
            //
            if(CollectionUtils.isNotEmpty(tableNameSet)){
                //指定了表名的数据
                result = metadataTableVos.stream().filter(item -> tableNameSet.contains(item.getTableName())).collect(Collectors.toList());
            }else{
                //全部表
                result = metadataTableVos;
            }

            //获取字段信息
            List<MetadataColumnVo> metadataColumnVos = listTableColumns(dataSourceEntity, dbName, tableNameSet);
            boolean containsDate = false;
            boolean containsDatetime = false;
            for(MetadataTableVo loopTable: result){
                List<MetadataColumnVo> columns = new ArrayList<>();
                for(MetadataColumnVo loopColumn: metadataColumnVos){
                    if(loopTable.getTableName().equalsIgnoreCase(loopColumn.getTable().getTableName())){
                        columns.add(loopColumn);
                        if(!containsDate && loopColumn.getIsDate()){
                            containsDate = true;
                        }
                        if(!containsDatetime && loopColumn.getIsDatetime()){
                            containsDatetime = true;
                        }
                    }
                }
                loopTable.setColumns(columns);
                loopTable.setContainsDate(containsDate);
                loopTable.setContainsDatetime(containsDatetime);
            }
        }
        return result;
    }

    /**
     * 获取字段列表
     * @param dataSourceEntity
     * @param dbName
     * @param tableNameSet
     * @return
     */
    @Override
    public List<MetadataColumnVo> listTableColumns(DataSourceEntity dataSourceEntity, String dbName, Set<String> tableNameSet) {
        log.info("[MetadataMysqlProcessor][listTableColumns]enter, dbName:{}, tableNameSet:{}", dbName, tableNameSet);
        List<MetadataColumnVo> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection(dataSourceEntity);
            StringBuilder inSb=new StringBuilder();
            if(CollectionUtils.isNotEmpty(tableNameSet)){
                for(int i=0;i<tableNameSet.size();i++){
                    if(i==0){
                        inSb.append("?");
                    }
                    else{
                        inSb.append(",?");
                    }
                }
            }

            StringBuilder sb = new StringBuilder("select TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, IS_NULLABLE, COLUMN_KEY, EXTRA, " +
                    "CHARACTER_MAXIMUM_LENGTH, COLUMN_DEFAULT, COLUMN_COMMENT, CHARACTER_SET_NAME " +
                    "from information_schema.COLUMNS " +
                    "where TABLE_SCHEMA=? " +
                    (CollectionUtils.isNotEmpty(tableNameSet)?"and TABLE_NAME in ("+inSb.toString()+")":"") +
                    "order by TABLE_SCHEMA asc, TABLE_NAME asc, ORDINAL_POSITION asc");
            ps = conn.prepareStatement(sb.toString());
            ps.setString(1, dbName);

            int index=2;
            if(CollectionUtils.isNotEmpty(tableNameSet)){
                for(String loopTableName : tableNameSet){
                    ps.setString(index++, loopTableName);
                }
            }

            rs = ps.executeQuery();
            while(rs.next()){
                MetadataColumnVo columnVo = new MetadataColumnVo();

                String tableName = rs.getString("TABLE_NAME");
                //TODO 缓存Table，节省内存
                MetadataTableVo tableVo = MetadataTableVo.builder().tableName(tableName).build();

                columnVo.setTable(tableVo);
                columnVo.setColumn(rs.getString("COLUMN_NAME"));
                columnVo.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
                columnVo.setLength(rs.getLong("CHARACTER_MAXIMUM_LENGTH"));
                columnVo.setCharset(rs.getString("CHARACTER_SET_NAME"));
                columnVo.setRemark(rs.getString("COLUMN_COMMENT"));

                //非空判断
                columnVo.setIsNullable(isNullable(rs.getString("IS_NULLABLE")));

                //字段映射的数据数据类型
                String dataType = rs.getString("DATA_TYPE");
                columnVo.setDataTypeClazz(parseDataType(dataType));

                columnVo.setIsDate(judgeDate(dataType));
                columnVo.setIsDatetime(judgeDatetime(dataType));

                //主键判断
                String columnKey = rs.getString("COLUMN_KEY");
                columnVo.setIsPrimaryKey("PRI".equalsIgnoreCase(columnKey));

                //自增判断
                String extra = rs.getString("EXTRA");
                columnVo.setIsAutoIncrement("auto_increment".equalsIgnoreCase(extra));

                result.add(columnVo);
            }
        } catch (Exception e) {
            log.error("[MetadataMysqlProcessor][listUserTables]error:{}, dbName:{}, tableNameSet:{}", e, dbName, tableNameSet);
            e.printStackTrace();
        }finally {
            releaseResource(conn, ps, rs);
        }
        log.info("[MetadataMysqlProcessor][listTableColumns]result:{}, dbName:{}, tableNameSet:{}", result, dbName, tableNameSet);
        return result;
    }

    /**
     * 释放资源
     * @param conn
     * @param ps
     * @param rs
     */
    private void releaseResource(Connection conn, PreparedStatement ps, ResultSet rs) {
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析mysql的数据类型到基本类型
     * @param dataTypeText
     * @return
     */
    public static Class parseDataType(String dataTypeText){
        return parseDataType(dataTypeText, String.class);
    }

    /**
     * 解析mysql的数据类型到基本类型
     * @param dataTypeText
     * @param defaultClass
     * @return
     */
    public static Class parseDataType(String dataTypeText, Class defaultClass){
        Class result = null;
        if(dataTypeText!=null){
            result = DATA_TYPE_MAP.get(StringUtils.trim(dataTypeText));
        }
        if(result==null){
            result = defaultClass;
        }
        return result;
    }


    /**
     * mysql中判断字段是否为null
     * @param isNullable
     * @return
     */
    private static boolean isNullable(String isNullable){
        return "YES".equalsIgnoreCase(isNullable)?true:false;
    }


    /**
     * 判断是否是日期类型
     * @param dataType
     * @return
     */
    private boolean judgeDate(String dataType) {
        if(StringUtils.equalsAnyIgnoreCase(dataType, "date")){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是时间类型
     * @param dataType
     * @return
     */
    private boolean judgeDatetime(String dataType) {
        if(StringUtils.equalsAnyIgnoreCase(dataType, "datetime", "timestamp")){
            return true;
        }
        return false;
    }

}
