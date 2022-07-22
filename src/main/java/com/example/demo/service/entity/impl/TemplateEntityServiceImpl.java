package com.example.demo.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.consts.Constants;
import com.example.demo.enums.TemplateSourceTypeEnum;
import com.example.demo.exception.GenericRuntimeException;
import com.example.demo.mapper.TemplateEntityMapper;
import com.example.demo.model.*;
import com.example.demo.service.entity.ITemplateEntityService;
import com.example.demo.service.entity.IUserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板EntityService
 * @author bruce
 */
@Slf4j
@Service
public class TemplateEntityServiceImpl extends ServiceImpl<TemplateEntityMapper, TemplateEntity>
		implements ITemplateEntityService, InitializingBean {

	//TODO 重构
	/** 模板存放目录 */
	@Value("${variable-generator.path.templates}")
	private String DIRNAME_TEMPLATE_ROOT;

	/** 模板存放目录 */
	private File templatesRootDir = null;
	/** 代码输出目录 */
	private File outputRootDir = null;
	/** 临时目录 */
	private File tmpDir = null;


	@Autowired
	private IUserEntityService userEntityService;

	/**
	 * 判断模板是否存在
	 * @param templateKey
	 * @return
	 */
	@Override public boolean exists(String templateKey) {
		TemplateEntity templateVo = loadByTemplateKey(templateKey);
		return TemplateEntity.isValid(templateVo);
	}

	/**
	 *
	 * @param templateKey
	 * @return
	 */
	@Override public TemplateEntity loadByTemplateKey(String templateKey) {
		return loadByTemplateKey(templateKey, false);
	}

	/**
	 *
	 * @param templateKey
	 * @return
	 */
	@Override public TemplateEntity loadByTemplateKey(String templateKey, boolean throwException)  {
		log.info("[loadByTemplateKey]enter, templateKey:{}, throwException:{}", templateKey, throwException );
		LambdaQueryWrapper<TemplateEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(TemplateEntity::getTemplateKey, templateKey);
		TemplateEntity result = this.getOne(wrapper);
		if(!TemplateEntity.isValid(result) && throwException){
			throw new GenericRuntimeException(ResponseContent.CODE_ERROR, templateKey+"对应的模板不存在");
		}
		log.info("[loadByTemplateKey]result, templateKey:{}, throwException:{}", result, templateKey, throwException);
		return result;
	}

	/**
	 *
	 * @param sourceType
	 * @return
	 */
	@Override public List<TemplateVo> getTemplateList(Short sourceType) {
		log.info("[getTemplateList]enter, sourceType:{}", sourceType);
		LambdaQueryWrapper<TemplateEntity> wrapper = null;
		if(sourceType!=null && sourceType>= TemplateSourceTypeEnum.OFFICIAL.getValue()){
			wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(TemplateEntity::getSourceType, sourceType);
		}
		List<TemplateEntity> list = this.list(wrapper);

		List<TemplateVo> result = new ArrayList<>();
		for(TemplateEntity loopItem: list){
			TemplateVo templateVo = TemplateVo.convert(loopItem);

			//补全作者信息
			UserEntity userEntity = userEntityService.getById(loopItem.getCreateUid());
			if(UserEntity.isValid(userEntity)){
				AuthorVo author = AuthorVo.builder()
						.userId(userEntity.getId()).username(userEntity.getUsername()).nickname(userEntity.getNickname())
						.email(userEntity.getEmail()).avatarUrl(userEntity.getAvatarUrl())
						.build();
				templateVo.setAuthor(author);
			}
			result.add(templateVo);
		}
		log.info("[getTemplateList]result:{}, sourceType:{}", result, sourceType);
		return result;
	}

	/**
	 * 获取zipFile
	 * @param templateKey
	 * @return
	 */
	@Override public File loadTemplateZipFile(String templateKey) {
		log.info("[loadTemplateZipFile]enter, templateKey:{}", templateKey);
		TemplateEntity templateEntity = loadByTemplateKey(templateKey);
		if(!TemplateEntity.isValid(templateEntity)){
			throw new GenericRuntimeException(ResponseContent.CODE_ERROR, "模板文件不存在，请检查");
		}
		String filename = StringUtils.right(templateEntity.getPath(), templateKey.length());
		File result = new File(templatesRootDir, filename+ Constants.SUFFIX_ZIP);
		if(!result.exists()){
			log.error("[loadTemplateZipFile]error，模板文件不存在" + result.getAbsolutePath());
			throw new GenericRuntimeException(ResponseContent.CODE_ERROR, "模板文件不存在，请检查");
		}
		log.info("[loadTemplateZipFile]result:{}, templateKey:{}", result, templateKey);
		return result;
	}

//	private void populateAuthorInfo(int userId){
//
//	}



	@Override public void afterPropertiesSet() throws Exception {
		/** 模板存放目录 */
		templatesRootDir = new File(DIRNAME_TEMPLATE_ROOT);
	}

}
