package com.example.demo.controller;

import com.example.demo.consts.Constants;
import com.example.demo.model.*;
import com.example.demo.model.metadata.MetadataColumnVo;
import com.example.demo.model.metadata.MetadataDatabaseVo;
import com.example.demo.model.metadata.MetadataTableVo;
import com.example.demo.service.IMetadataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 元数据管理
 * @author bruce
 */
@Slf4j
@Api(tags="元数据管理")
@RequestMapping("/metadata")
@RestController
public class MetadataController {

	private static final int userId = Constants.USER_ID_OFFICIAL;

	@Autowired
	private IMetadataService metadataService;

	@GetMapping("/listDatabases")
	@ApiOperation(value="获取数据库")
	public ResponseContent<List<MetadataDatabaseVo>> listUserDatabases(int datasourceId) throws Exception {
		log.info("[listUserTables]enter, datasourceId:{}", datasourceId);
		List<MetadataDatabaseVo> metadataTableVos = metadataService.listDatabases(userId, datasourceId);
		return ResponseContent.buildSuccessResult(metadataTableVos);
	}

	@GetMapping("/listTables")
	@ApiOperation(value="获取数据表")
	public ResponseContent<List<MetadataTableVo>> listTables(int datasourceId, String dbName) throws Exception {
		log.info("[listUserTables]enter, datasourceId:{}, dbname:{}", datasourceId, dbName);
		List<MetadataTableVo> metadataTableVos = metadataService.listUserTables(userId, datasourceId, dbName);
		return ResponseContent.buildSuccessResult(metadataTableVos);
	}

	@GetMapping("/listTables2")
	@ApiOperation(value="获取数据表")
	public ResponseContent<List<MetadataTableVo>> listTables(int datasourceId, String dbName, @RequestParam(value = "tableName", required = false) String... tableNames) throws Exception {
		log.info("[listTables]enter, datasourceId:{}, dbname:{}, tableNames:{}", datasourceId, dbName, tableNames);
		Set<String> tableNameSet = tableNames==null?null: Arrays.stream(tableNames).collect(Collectors.toSet());
		List<MetadataTableVo> metadataTableVos = metadataService.listUserTables(userId, datasourceId, dbName, tableNameSet);
		return ResponseContent.buildSuccessResult(metadataTableVos);
	}



	@GetMapping("/listColumns")
	@ApiOperation(value="获取字段列表")
	public ResponseContent<List<MetadataColumnVo>> listColumns(int datasourceId, String dbName, @RequestParam(value = "tableName") String... tableNames) throws Exception {
		log.info("[listColumns]enter, datasourceId:{}, dbname:{}, tableNames:{}", datasourceId, dbName, tableNames);
		Set<String> tableNameSet = tableNames==null?null: Arrays.stream(tableNames).collect(Collectors.toSet());
		List<MetadataColumnVo> metadataTableVos = metadataService.listTableColumns(userId, datasourceId, dbName, tableNameSet);
		return ResponseContent.buildSuccessResult(metadataTableVos);
	}


}
