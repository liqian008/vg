package com.example.demo.metadata.processor;

import com.example.demo.consts.Constants;
import com.example.demo.enums.DbTypeEnum;
import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.model.metadata.MetadataColumnVo;
import com.example.demo.model.metadata.MetadataDatabaseVo;
import com.example.demo.model.metadata.MetadataTableVo;
import com.example.demo.service.entity.IDataSourceEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Oracle元数据实现类
 *
 * @author liqian483
 */
@Slf4j
@Service(Constants.DB_TYPE_ORACLE + Constants.METADATA_PROCESSOR_SUFFIX)
public class MetadataOracleProcessor implements IMetadataDbProcessor {

    @Override
    public DbTypeEnum getDbTypeEnum() {
        return DbTypeEnum.ORACLE;
    }

    @Override
    public Connection getConnection(DataSourceEntity dataSourceEntity) throws Exception {
        return null;
    }

    @Override
    public List<MetadataDatabaseVo> listDatabases(DataSourceEntity dataSourceEntity) {
        return null;
    }

    @Override
    public List<MetadataTableVo> listUserTables(DataSourceEntity dataSourceEntity, String dbName) {
        return null;
    }

    @Override
    public List<MetadataTableVo> listUserTables(DataSourceEntity dataSourceEntity, String dbName, Set<String> tableNameSet) {
        return null;
    }

    @Override
    public List<MetadataColumnVo> listTableColumns(DataSourceEntity dataSourceEntity, String dbName, Set<String> tableNameSet) {
        return null;
    }


}
