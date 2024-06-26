package com.example.demo.controller;

import com.example.demo.consts.Constants;
import com.example.demo.model.vo.DataSourceVo;
import com.example.demo.model.ResponseContent;
import com.example.demo.service.entity.IDataSourceEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author bruce
 */
@Slf4j
@Api(tags="数据源管理")
@RequestMapping("/datasource")
@RestController
public class DataSourceController {


	private static final int userId = Constants.USER_ID_OFFICIAL;

	@Autowired
	private IDataSourceEntityService dataSourceEntityService;

	@GetMapping("/listUserDatasources")
	@ApiOperation(value="获取我的数据源列表")
	public ResponseContent<List<DataSourceVo>> listUserDatasources(Short dbType) throws Exception {
		log.info("[listUserDatasource]enter, dbType:{}", dbType);
		List<DataSourceVo> result = dataSourceEntityService.listUserDataSources(userId, dbType);
		return ResponseContent.buildSuccessResult(result);
	}


	@GetMapping("/listUserTables")
	@ApiOperation(value="获取数据表")
	public ResponseContent<List<String>> listUserTables(int datasourceId, String dbname) throws Exception {
		log.info("[listUserTables]enter, datasourceId:{}, dbname:{}", datasourceId, dbname);
		List<String> result = dataSourceEntityService.listUserTables(userId, datasourceId);
		return ResponseContent.buildSuccessResult(result);
	}


	@DeleteMapping("/delete")
	@ApiOperation(value="获取数据表")
	public ResponseContent<Boolean> deleteDatasource(int datasourceId) throws Exception {
		log.info("[deleteDatasource]enter, datasourceId:{}", datasourceId);
		boolean result = dataSourceEntityService.deleteDatasource(userId, datasourceId);
		return ResponseContent.buildSuccessResult(result);
	}



//	@GetMapping("/delete")
//	@ApiOperation(value="删除我的数据源", notes="删除指定我的数据源")
//	public ResponseContent<Boolean> deleteDataSource(String templateKey) throws Exception {
//		log.info("[deleteDataSource]enter");
//		boolean result = generateService.deleteDataSource(templateKey);
//		return ResponseContent.buildSuccessResult(result);
//	}

}
