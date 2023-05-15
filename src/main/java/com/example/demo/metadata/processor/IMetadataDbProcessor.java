package com.example.demo.metadata.processor;

import com.example.demo.enums.DbTypeEnum;
import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.model.metadata.MetadataColumnVo;
import com.example.demo.model.metadata.MetadataDatabaseVo;
import com.example.demo.model.metadata.MetadataTableVo;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * 数据库元数据抽象接口
 *
 * @author liqian483
 */
public interface IMetadataDbProcessor {

    /**
     * 获取数据库类型
     * @return
     */
    DbTypeEnum getDbTypeEnum();

    /**
     * 获取数据库链接
     * @param dataSourceEntity
     * @return
     * @throws Exception
     */
    Connection getConnection(DataSourceEntity dataSourceEntity) throws Exception;

    /**
     * 列出数据表
     * @param dataSourceEntity
     * @return
     */
    List<MetadataDatabaseVo> listDatabases(DataSourceEntity dataSourceEntity);


    /**
     * 获取数据表的元数据信息
     * @param dataSourceEntity
     * @param dbName
     * @return
     */
    List<MetadataTableVo> listUserTables(DataSourceEntity dataSourceEntity, String dbName);

    /**
     * 获取指定数据表的元数据信息
     * @param dataSourceEntity
     * @param dbName
     * @param tableNameSet
     * @return
     */
    List<MetadataTableVo> listUserTables(DataSourceEntity dataSourceEntity, String dbName, Set<String> tableNameSet);

    /**
     * 获取字段列表
     * @param dataSourceEntity
     * @param dbName
     * @param tableNameSet
     * @return
     */
    List<MetadataColumnVo> listTableColumns(DataSourceEntity dataSourceEntity, String dbName, Set<String> tableNameSet);
}
