package com.example.demo.controller;

import com.example.demo.consts.Constants;
import com.example.demo.model.DataSourceVo;
import com.example.demo.model.MetadataTableVo;
import com.example.demo.model.ResponseContent;
import com.example.demo.service.IMetadataService;
import com.example.demo.service.entity.IDataSourceEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/listDatasources")
	@ApiOperation(value="获取我的数据源列表")
	public ResponseContent<List<DataSourceVo>> listUserDatasources(Short dbType) throws Exception {
		log.info("[listUserDatasource]enter, dbType:{}", dbType);
		List<DataSourceVo> result = dataSourceEntityService.listUserDataSources(userId, dbType);
		return ResponseContent.buildSuccessResult(result);
	}


	@PostMapping("/createUserDatasource")
	@ApiOperation(value="获取数据表")
	public ResponseContent<Integer> createUserDatasource(DataSourceVo datasrouce) throws Exception {
		log.info("[createUserDatasource]enter, datasrouce:{}", datasrouce);
		int result = 0;
		dataSourceEntityService.createDatasource(userId, datasrouce);
		return ResponseContent.buildSuccessResult(result);
	}


	@DeleteMapping("/delete")
	@ApiOperation(value="删除数据表")
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
