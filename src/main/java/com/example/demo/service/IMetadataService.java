package com.example.demo.service;

import com.example.demo.processor.IMetadataDbProcessor;
import com.example.demo.model.metadata.MetadataColumnVo;
import com.example.demo.model.metadata.MetadataDatabaseVo;
import com.example.demo.model.metadata.MetadataTableVo;

import java.util.List;
import java.util.Set;

/**
 * 元数据Service
 * @author bruce
 */
public interface IMetadataService {

//	/**
//	 * 根据数据库类型获取对应的处理类
//	 * @param dbTypeVal
//	 * @return
//	 */
//	IMetadataDbProcessor loadDbProcessorByDbType(short dbTypeVal);

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

	/**
	 * 获取指定数据表的元数据信息
	 * @param userId
	 * @param datasourceId
	 * @param dbName
	 * @param tableNameSet
	 * @return
	 */
	List<MetadataTableVo> listUserTables(int userId, int datasourceId, String dbName, Set<String> tableNameSet);



	/**
	 * 列出字段列表
	 * @param userId
	 * @param datasourceId
	 * @param dbName
	 * @param tableNameSet
	 * @return
	 */
	List<MetadataColumnVo> listTableColumns(int userId, int datasourceId, String dbName, Set<String> tableNameSet);

}
