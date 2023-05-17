package com.example.demo.service;

import com.example.demo.model.GenerateResultVo;
import com.example.demo.model.GenerateConfigBase;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 生成器service
 * @author bruce
 */
public interface IGenerateService {

	/**
	 * 生成service
	 * @param generateConfig
	 * @return
	 */
	GenerateResultVo generate(GenerateConfigBase generateConfig) throws Exception;

	/**
	 * 上传模板文件
	 *
	 * @param userId
	 * @param templateZipFile
	 * @return
	 */
	boolean uploadTemplateFile(int userId, MultipartFile templateZipFile) throws Exception;

	/**
	 * 压缩文件夹到zip文件
	 * @param sourceDirName
	 * @return
	 */
	File doZipFile(String sourceDirName) throws Exception;

	/**
	 * 删除指定模板
	 * @param templateKey
	 * @return
	 */
	boolean deleteTemplate(String templateKey) throws IOException;




	//	/**
	//	 * 获取模板列表
	//	 * @return
	//	 */
	//	List<TemplateVo> getTemplateList() throws Exception;

	//	/**
	//	 * 获取指定模板的变量定义
	//	 * @param templateName
	//	 * @return
	//	 */
	//	Set<VarDefinitionVo> loadTemplateVars(String templateName) throws Exception;

}
