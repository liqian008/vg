package com.example.demo.service;

import com.example.demo.model.MetadataColumnVo;
import com.example.demo.model.MetadataDatabaseVo;
import com.example.demo.model.MetadataTableVo;

import java.util.List;
import java.util.Set;

/**
 * 元数据Service
 * @author bruce
 */
public interface IMetadataService {

	/**
	 * 列出数据库
	 * @param userId
	 * @param datasourceId
	 * @return
	 */
	List<MetadataDatabaseVo> listDatabases(int userId, int datasourceId);


	/**
	 * 列出数据表
	 * @param userId
	 * @param datasourceId
	 * @param dbName
	 * @return
	 */
	List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName);


//	/**
//	 * 列出数据表
//	 * @param userId
//	 * @param datasourceId
//	 * @param dbName
//	 * @param tableNames 待处理的表名
//	 * @return
//	 */
//	List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName, String... tableNames);



	/**
	 * 列出字段列表
	 * @param userId
	 * @param datasourceId
	 * @param dbName
	 * @param tableName
	 * @return
	 */
	List<MetadataColumnVo> listTableColumns(int userId, int datasourceId, String dbName, String tableName);

}
