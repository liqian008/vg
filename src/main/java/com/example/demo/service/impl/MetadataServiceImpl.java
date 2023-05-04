package com.example.demo.service.impl;


import com.example.demo.model.MetadataColumnVo;
import com.example.demo.model.MetadataDatabaseVo;
import com.example.demo.model.MetadataTableVo;
import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.service.IMetadataService;
import com.example.demo.service.entity.IDataSourceEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 元数据service实现
 * @author bruce
 */
@Slf4j
@Service
public class MetadataServiceImpl implements IMetadataService, InitializingBean {

	@Autowired
	private IDataSourceEntityService dataSourceEntityService;



	@Override
	public List<MetadataDatabaseVo> listDatabases(int userId, int datasourceId) {
		log.info("[listDatabases]enter, userId:{}, datasourceId:{}", userId, datasourceId);
		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
		List<MetadataDatabaseVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection(dataSourceEntity);
//			DatabaseMetaData metaData = conn.getMetaData();
			ps = conn.prepareStatement("show databases");
			rs = ps.executeQuery();
			while(rs.next()){
				String dbName = rs.getString(1);
				MetadataDatabaseVo item = new MetadataDatabaseVo();
				item.setDbName(dbName);
				result.add(item);
			}
		} catch (SQLException e) {

		}finally {
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
		log.info("[listDatabases]result:{}, userId:{}, datasourceId:{}", result, userId, datasourceId);
		return result;
	}


	@Override
	public List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName) {
		log.info("[listUserTables]enter, userId:{}, datasourceId:{}, dbName:{}", userId, datasourceId, dbName);
		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
		List<MetadataTableVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		MetadataDatabaseVo databaseVo = new MetadataDatabaseVo();
		try {
			conn = getConnection(dataSourceEntity);
//			StringBuilder sb = new StringBuilder("show tables");
//			if(StringUtils.isNotBlank(dbName)){
//				sb.append(" from "+dbName);
//			}
//			ps = conn.prepareStatement(sb.toString());
//			rs = ps.executeQuery();
//			while(rs.next()){
//				String tableName = rs.getString(1);
//				MetadataTableVo tableVo = new MetadataTableVo();
//				tableVo.setTableName(tableName);
//				tableVo.setDatabase(databaseVo);
//				result.add(tableVo);
//			}

			//TODO 多态，各数据库单独的实现（目前写死了Mysql）
			StringBuilder sb = new StringBuilder("select * from information_schema.TABLES " +
					"where TABLE_SCHEMA=?"
			);
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, dbName);
			rs = ps.executeQuery();
			while(rs.next()){
				MetadataTableVo vo = new MetadataTableVo();
				vo.setTableName(rs.getString("TABLE_NAME"));
				vo.setRemark(rs.getString("TABLE_COMMENT"));
				result.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		log.info("[listUserTables]result:{}, userId:{}, datasourceId:{}", result, userId, datasourceId);
		return result;
	}

	/**
	 * 获取字段列表
	 * @param userId
	 * @param datasourceId
	 * @param dbName
	 * @param tableName
	 * @return
	 */
    @Override
    public List<MetadataColumnVo> listTableColumns(int userId, int datasourceId, String dbName, String tableName) {
		log.info("[listTableColumns]enter, userId:{}, datasourceId:{}, dbName:{}, tableName:{}", userId, datasourceId, dbName, tableName);
		DataSourceEntity dataSourceEntity = dataSourceEntityService.loadById(userId, datasourceId, true);
		List<MetadataColumnVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection(dataSourceEntity);
			//TODO 多态，各数据库单独的实现（目前写死了Mysql）

			StringBuilder sb = new StringBuilder("select COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, IS_NULLABLE, COLUMN_KEY, EXTRA, " +
					"CHARACTER_MAXIMUM_LENGTH, COLUMN_DEFAULT, COLUMN_COMMENT, CHARACTER_SET_NAME " +
					"from information_schema.COLUMNS " +
					"where TABLE_SCHEMA=? and TABLE_NAME=?" +
					"order by ORDINAL_POSITION asc");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, dbName);
			ps.setString(2, tableName);
			rs = ps.executeQuery();
			while(rs.next()){
				MetadataColumnVo columnVo = new MetadataColumnVo();
				columnVo.setColumn(rs.getString("COLUMN_NAME"));

				boolean nullable = "YES".equalsIgnoreCase(rs.getString("IS_NULLABLE"))?true:false;
				columnVo.setNullable(nullable);
				columnVo.setDataType(rs.getString("DATA_TYPE"));

				//主键判断
				String columnKey = rs.getString("COLUMN_KEY");
				columnVo.setColumnKey(columnKey);
				columnVo.setPrimaryKey("PRI".equalsIgnoreCase(columnKey));

				//自增判断
				String extra = rs.getString("EXTRA");
				columnVo.setExtra(extra);
				columnVo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));

				columnVo.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
				columnVo.setLength(rs.getLong("CHARACTER_MAXIMUM_LENGTH"));
				columnVo.setCharset(rs.getString("CHARACTER_SET_NAME"));
				columnVo.setRemark(rs.getString("COLUMN_COMMENT"));
				result.add(columnVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		log.info("[listTableColumns]result:{}, userId:{}, datasourceId:{}", result, userId, datasourceId);
		return result;
    }

    /**
	 *
	 * @param dataSourceEntity
	 * @return
	 */
	private Connection getConnection(DataSourceEntity dataSourceEntity) {
		String jdbcUrl = dataSourceEntity.getJdbcUrl();
		String jdbcDriver = dataSourceEntity.getJdbcDriver();
		String username = dataSourceEntity.getUsername();
		String password = dataSourceEntity.getPassword();
		Connection conn = null;
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
