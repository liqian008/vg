package com.example.demo.controller;

import com.example.demo.consts.Constants;
import com.example.demo.model.FileDownloadVo;
import com.example.demo.model.GenerateConfigBase;
import com.example.demo.model.ResponseContent;
import com.example.demo.service.IGenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 *
 * @author bruce
 */
@Slf4j
@Api(tags="代码生成")
@RequestMapping("/code")
@RestController
public class CodeController {

	@Autowired
	private IGenerateService generateService;


//	@GetMapping("/templateList")
//	@ApiOperation(value="获取模板列表", notes="获取模板列表")
//	public ResponseContent<List<TemplateVo>> templateList() throws Exception {
//		log.info("[templateList]enter");
//		List<TemplateVo> result = generateService.getTemplateList();
//		return ResponseContent.buildSuccessResult(result);
//	}


	@PostMapping("/generate")
	@ApiOperation(value="根据指定的模板和参数，生成代码")
	public ResponseContent<FileDownloadVo> generate(@RequestBody GenerateConfigBase generateConfig) throws Exception {
		log.info("[generate]enter, generateConfig:{}", generateConfig);
		FileDownloadVo result = generateService.generate(generateConfig);
		return ResponseContent.buildSuccessResult(result);
	}



	/**
	 * 下载文件（zip格式）
	 * @param filename
	 * @param req
	 * @param response
	 * @throws Exception
	 */

	@ApiOperation(value="下载生成的代码包")
	@GetMapping("/download")
	public void download(String filename, HttpServletRequest req, HttpServletResponse response) throws Exception {
		log.info("[downloadFile]enter, filename:{}", filename);
		try {
			File zipFile = generateService.doZipFile(filename);
			//设置响应头，以附件的形式下载
			response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(zipFile.getName(),
					Constants.CHARSET_UTF8));
			//将目标文件复制一份，通过response以流的形式输出
			IOUtils.copy(new FileInputStream(zipFile), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	//	/**
	//	 * 获取指定模板的变量定义
	//	 * @param templateName
	//	 * @return
	//	 * @throws Exception
	//	 */
	//	@RequestMapping("/loadTemplatVars")
	//	public ResponseContent<Set<VarDefinitionVo>> loadTemplatVars(String templateName) throws Exception {
	//		log.info("[loadTemplatVars]enter, templateName:{}", templateName);
	//		Set<VarDefinitionVo> result = generateService.loadTemplateVars(templateName);
	//		return ResponseContent.buildSuccessResult(result);
	//	}


}
