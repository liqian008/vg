package com.example.demo.controller;

import com.example.demo.consts.Constants;
import com.example.demo.model.ResponseContent;
import com.example.demo.model.vo.TemplateVo;
import com.example.demo.service.IGenerateService;
import com.example.demo.service.entity.ITemplateEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 * @author bruce
 */
@Slf4j
@Api(tags="模板管理")
@RequestMapping("/template")
@RestController
public class TemplateController {

	@Autowired
	private ITemplateEntityService templateEntityService;
	@Autowired
	private IGenerateService generateService;


	@GetMapping("/exists")
	@ApiOperation(value="判断模板是否存在", notes="判断模板是否存在")
	public ResponseContent<Boolean> exists(String templateKey) {
		log.info("[templateList]enter");
		boolean result = templateEntityService.exists(templateKey);
		return ResponseContent.buildSuccessResult(result);
	}

	@GetMapping("/list")
	@ApiOperation(value="获取模板列表", notes="获取模板列表")
	public ResponseContent<List<TemplateVo>> templateList(Short sourceType)  {
		log.info("[templateList]enter");
		List<TemplateVo> result = templateEntityService.getTemplateList(sourceType);
		return ResponseContent.buildSuccessResult(result);
	}

	@GetMapping("/delete")
	@ApiOperation(value="删除指定模板", notes="删除指定模板")
	public ResponseContent<Boolean> deleteTemplate(@RequestParam String templateKey) throws Exception {
		log.info("[deleteTemplate]enter");
		boolean result = generateService.deleteTemplate(templateKey);
		return ResponseContent.buildSuccessResult(result);
	}


	@ApiOperation(value="上传自定义模板文件")
	@PostMapping("/uploadTemplateFile")
	public ResponseContent<Boolean> uploadTemplateFile(
			@RequestParam("file") MultipartFile templateZipFile) throws Exception {
		log.info("[uploadTemplateFile]enter, templateZipFile:{}", templateZipFile.getName());
		boolean result = generateService.uploadTemplateFile(Constants.USER_ID_OFFICIAL, templateZipFile);
		return ResponseContent.buildSuccessResult(result);
	}

	@ApiOperation(value="下载模板文件")
	@GetMapping("/downloadFile")
	public void downloadFile(@RequestParam String templateKey,
			HttpServletRequest req, HttpServletResponse response) {
		log.info("[downloadFile]enter, templateKey:{}", templateKey);
		try {
			File zipFile = templateEntityService.loadTemplateZipFile(templateKey);
			//设置响应头，以附件的形式下载
			response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(zipFile.getName(),
					Constants.CHARSET_UTF8));
			//将目标文件复制一份，通过response以流的形式输出
			IOUtils.copy(new FileInputStream(zipFile), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
