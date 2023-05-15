package com.example.demo.service.impl;


import com.example.demo.enums.DbTypeEnum;
import com.example.demo.holder.ApplicationContextHolder;
import com.example.demo.metadata.processor.IMetadataDbProcessor;
import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.model.metadata.MetadataColumnVo;
import com.example.demo.model.metadata.MetadataDatabaseVo;
import com.example.demo.model.metadata.MetadataTableVo;
import com.example.demo.service.IMetadataService;
import com.example.demo.service.entity.IDataSourceEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.demo.consts.Constants.METADATA_PROCESSOR_SUFFIX;

/**
 * 元数据service实现
 * @author bruce
 */
@Slf4j
@Service
public class MetadataServiceImpl implements IMetadataService, InitializingBean {

	@Autowired
	private IDataSourceEntityService dataSourceEntityService;
	@Autowired
	private ApplicationContextHolder applicationContextHolder;


	@Override
	public IMetadataDbProcessor loadProcessorByDbType(short dbTypeVal) {
		DbTypeEnum dbTypeEnum = DbTypeEnum.valueOf(dbTypeVal);
		String beanName = dbTypeEnum.getName() + METADATA_PROCESSOR_SUFFIX;
		IMetadataDbProcessor result = applicationContextHolder.getApplicationContext().getBean(beanName, IMetadataDbProcessor.class);
		return result;
	}

	@Override
	public List<MetadataDatabaseVo> listDatabases(int userId, int datasourceId) {
		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
		IMetadataDbProcessor metadataDbProcessor = loadProcessorByDbType(dataSourceEntity.getDbType());
		List<MetadataDatabaseVo> result = metadataDbProcessor.listDatabases(dataSourceEntity);
		return result;
	}

//	@Override
//	public List<MetadataDatabaseVo> listDatabases(int userId, int datasourceId) {
//		log.info("[listDatabases]enter, userId:{}, datasourceId:{}", userId, datasourceId);
//		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
//		List<MetadataDatabaseVo> result = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			conn = getConnection(dataSourceEntity);
////			DatabaseMetaData metaData = conn.getMetaData();
//			ps = conn.prepareStatement("show databases");
//			rs = ps.executeQuery();
//			while(rs.next()){
//				String dbName = rs.getString(1);
//				MetadataDatabaseVo item = new MetadataDatabaseVo();
//				item.setDbName(dbName);
//				result.add(item);
//			}
//		} catch (SQLException e) {
//			log.error("[listDatabases]error:{}, userId:{}, datasourceId:{}", e, userId, datasourceId);
//		}finally {
//			releaseResource(conn, ps, rs);
//		}
//		log.info("[listDatabases]result:{}, userId:{}, datasourceId:{}", result, userId, datasourceId);
//		return result;
//	}


	@Override
	public List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName) {
		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
		IMetadataDbProcessor metadataDbProcessor = loadProcessorByDbType(dataSourceEntity.getDbType());
		return metadataDbProcessor.listUserTables(dataSourceEntity, dbName);
	}

//	@Override
//	public List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName) {
//		log.info("[listUserTables]enter, userId:{}, datasourceId:{}, dbName:{}", userId, datasourceId, dbName);
//		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
//		List<MetadataTableVo> result = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		MetadataDatabaseVo databaseVo = MetadataDatabaseVo.builder().dbName(dbName).build();
//		try {
//			conn = getConnection(dataSourceEntity);
//			//TODO 多态，各数据库单独的实现（目前写死了Mysql）
//			StringBuilder sb = new StringBuilder("select TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT " +
//					"from information_schema.TABLES " +
//					"where TABLE_SCHEMA=?"
//			);
//			ps = conn.prepareStatement(sb.toString());
//			ps.setString(1, dbName);
//			rs = ps.executeQuery();
//			while(rs.next()){
//				MetadataTableVo vo = new MetadataTableVo();
//				vo.setDatabase(databaseVo);
//				vo.setTableName(rs.getString("TABLE_NAME"));
//				vo.setRemark(rs.getString("TABLE_COMMENT"));
//				result.add(vo);
//			}
//		} catch (SQLException e) {
//			log.error("[listUserTables]error:{}, userId:{}, datasourceId:{}", e, userId, datasourceId, dbName);
//			e.printStackTrace();
//		}finally {
//			releaseResource(conn, ps, rs);
//		}
//		log.info("[listUserTables]result:{}, userId:{}, datasourceId:{}, dbName:{}", result, userId, datasourceId, dbName);
//		return result;
//	}


	@Override
	public List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName, Set<String> tableNameSet) {
		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
		IMetadataDbProcessor metadataDbProcessor = loadProcessorByDbType(dataSourceEntity.getDbType());
		return metadataDbProcessor.listUserTables(dataSourceEntity, dbName, tableNameSet);
	}


//	@Override
//	public List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName, Set<String> tableNameSet) {
//		log.info("[listUserTables]enter, userId:{}, datasourceId:{}, dbName:{}, tableNameSet:{}", userId, datasourceId, dbName, tableNameSet);
//
//		List<MetadataTableVo> result = null;
//		List<MetadataTableVo> metadataTableVos = listUserTables(userId, datasourceId, dbName);
//
//		if(CollectionUtils.isNotEmpty(metadataTableVos)){
//			//
//			if(CollectionUtils.isNotEmpty(tableNameSet)){
//				//指定了表名的数据
//				result = metadataTableVos.stream().filter(item -> tableNameSet.contains(item.getTableName())).collect(Collectors.toList());
//			}else{
//				//全部表
//				result = metadataTableVos;
//			}
//
//			//获取字段信息
//			List<MetadataColumnVo> metadataColumnVos = listTableColumns(userId, datasourceId, dbName, tableNameSet);
//			for(MetadataTableVo loopTable: result){
//				List<MetadataColumnVo> columns = new ArrayList<>();
//				for(MetadataColumnVo loopColumn: metadataColumnVos){
//					if(loopTable.getTableName().equalsIgnoreCase(loopColumn.getTable().getTableName())){
//						columns.add(loopColumn);
//					}
//				}
//				loopTable.setColumns(columns);
//			}
//		}
//		return result;
//	}



	/**
	 * 获取字段列表
	 * @param userId
	 * @param datasourceId
	 * @param dbName
	 * @param tableNameSet
	 * @return
	 */
    @Override
    public List<MetadataColumnVo> listTableColumns(int userId, int datasourceId, String dbName, Set<String> tableNameSet) {
		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
		IMetadataDbProcessor metadataDbProcessor = loadProcessorByDbType(dataSourceEntity.getDbType());
		return metadataDbProcessor.listTableColumns(dataSourceEntity, dbName, tableNameSet);
	}


//	/**
//	 * 获取字段列表
//	 * @param userId
//	 * @param datasourceId
//	 * @param dbName
//	 * @param tableNameSet
//	 * @return
//	 */
//    @Override
//    public List<MetadataColumnVo> listTableColumns(int userId, int datasourceId, String dbName, Set<String> tableNameSet) {
//		log.info("[listTableColumns]enter, userId:{}, datasourceId:{}, dbName:{}, tableName:{}", userId, datasourceId, dbName, tableNameSet);
//		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
//		List<MetadataColumnVo> result = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			conn = getConnection(dataSourceEntity);
//			//TODO 多态，各数据库单独的实现（目前写死了Mysql）
//
//			StringBuilder inSb=new StringBuilder();
//			if(CollectionUtils.isNotEmpty(tableNameSet)){
//				for(int i=0;i<tableNameSet.size();i++){
//					if(i==0){
//						inSb.append("?");
//					}
//					else{
//						inSb.append(",?");
//					}
//				}
//			}
//
//			StringBuilder sb = new StringBuilder("select TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, IS_NULLABLE, COLUMN_KEY, EXTRA, " +
//					"CHARACTER_MAXIMUM_LENGTH, COLUMN_DEFAULT, COLUMN_COMMENT, CHARACTER_SET_NAME " +
//					"from information_schema.COLUMNS " +
//					"where TABLE_SCHEMA=? " +
//					(CollectionUtils.isNotEmpty(tableNameSet)?"and TABLE_NAME in ("+inSb.toString()+")":"") +
//					"order by TABLE_SCHEMA asc, TABLE_NAME asc, ORDINAL_POSITION asc");
//			ps = conn.prepareStatement(sb.toString());
//			ps.setString(1, dbName);
//
//			int index=2;
//			if(CollectionUtils.isNotEmpty(tableNameSet)){
//				for(String loopTableName : tableNameSet){
//					ps.setString(index++, loopTableName);
//				}
//			}
//
//			rs = ps.executeQuery();
//			while(rs.next()){
//				MetadataColumnVo columnVo = new MetadataColumnVo();
//
//				String tableName = rs.getString("TABLE_NAME");
//				//TODO 缓存，节省内存
//				MetadataTableVo tableVo = MetadataTableVo.builder().tableName(tableName).build();
//
//				columnVo.setTable(tableVo);
//				columnVo.setColumn(rs.getString("COLUMN_NAME"));
//
//				boolean nullable = "YES".equalsIgnoreCase(rs.getString("IS_NULLABLE"))?true:false;
//				columnVo.setNullable(nullable);
//				columnVo.setDataType(rs.getString("DATA_TYPE"));
//
//				//主键判断
//				String columnKey = rs.getString("COLUMN_KEY");
////				columnVo.setColumnKey(columnKey);
//				columnVo.setPrimaryKey("PRI".equalsIgnoreCase(columnKey));
//
//				//自增判断
//				String extra = rs.getString("EXTRA");
////				columnVo.setExtra(extra);
//				columnVo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));
//
//				columnVo.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
//				columnVo.setLength(rs.getLong("CHARACTER_MAXIMUM_LENGTH"));
//				columnVo.setCharset(rs.getString("CHARACTER_SET_NAME"));
//				columnVo.setRemark(rs.getString("COLUMN_COMMENT"));
//				result.add(columnVo);
//			}
//		} catch (SQLException e) {
//			log.error("[listUserTables]error:{}, userId:{}, datasourceId:{}, dbName:{}, tableNameSet:{}", e, userId, datasourceId, dbName, tableNameSet);
//			e.printStackTrace();
//		}finally {
//			releaseResource(conn, ps, rs);
//		}
//		log.info("[listTableColumns]result:{}, userId:{}, dbName:{}, tableNameSet:{}", result, userId, datasourceId, dbName, tableNameSet);
//		return result;
//    }

//    /**
//	 *
//	 * @param dataSourceEntity
//	 * @return
//	 */
//	private Connection getConnection(DataSourceEntity dataSourceEntity) {
//		String jdbcUrl = dataSourceEntity.getJdbcUrl();
//		String jdbcDriver = dataSourceEntity.getJdbcDriver();
//		String username = dataSourceEntity.getUsername();
//		String password = dataSourceEntity.getPassword();
//		Connection conn = null;
//		try {
//			Class.forName(jdbcDriver);
//			conn = DriverManager.getConnection(jdbcUrl, username, password);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return conn;
//	}
//
//	/**
//	 * 释放资源
//	 * @param conn
//	 * @param ps
//	 * @param rs
//	 */
//	private void releaseResource(Connection conn, PreparedStatement ps, ResultSet rs) {
//		if(conn!=null){
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		if(ps!=null){
//			try {
//				ps.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		if(rs!=null){
//			try {
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}


	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
