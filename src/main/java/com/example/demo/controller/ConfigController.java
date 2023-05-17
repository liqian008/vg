package com.example.demo.controller;

import com.example.demo.model.ResponseContent;
import com.example.demo.service.IConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 * @author bruce
 */
@Slf4j
@Api(tags="配置")
@RequestMapping("/config")
@RestController
public class ConfigController {

	@Autowired
	private IConfigService configService;

	@GetMapping("/")
	@ApiOperation(value="配置信息")
	public ResponseContent<Map<String, Object>> config() throws Exception {
		log.info("[ConfigController][config]enter");
		Map<String, Object> result = configService.getConfig();
		return ResponseContent.buildSuccessResult(result);
	}

}
