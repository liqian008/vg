package com.example.demo.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.vo.TemplateVo;
import com.example.demo.model.entity.TemplateEntity;

import java.io.File;
import java.util.List;

/**
 * 模板EntityService
 * @author bruce
 */
public interface ITemplateEntityService extends IService<TemplateEntity> {

	/**
	 * 判断模板key是否存在
	 * @param templateKey
	 * @return
	 */
	boolean exists(String templateKey);

	/**
	 *
	 * @param templateKey
	 * @return
	 */
	TemplateEntity loadByTemplateKey(String templateKey);

	TemplateEntity loadByTemplateKey(String templateKey, boolean throwException) throws Exception;

	/**
	 *
	 * @param sourceType
	 * @return
	 */
	List<TemplateVo> getTemplateList(Short sourceType);

	/**
	 * 获取zipFile
	 * @param templateKey
	 * @return
	 */
	File loadTemplateZipFile(String templateKey);
}
