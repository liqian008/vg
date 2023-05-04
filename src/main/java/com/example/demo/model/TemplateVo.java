package com.example.demo.model;

import com.example.demo.model.entity.TemplateEntity;
import com.example.demo.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 模板配置对象
 * @author bruce
 */
@ApiModel(value="模板配置对象", description="模板配置对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 模版id */
	private Integer id;
	/** 模版名称（即其文件夹名称） */
	private String templateKey;
	/** 模版版本号 */
	private Integer version;
	/** 模版名称 */
	private String title;
	/** 模版描述 */
	private String description;
	/** 模板地址 */
	private String fileUrl;

	/** 模版作者信息 */
	private AuthorVo author;

//	@ApiModelProperty("模板对象")
//	private TemplateInfoVo templateInfo;

	@ApiModelProperty("变量定义的列表")
	private List<TemplateVarDefinitionVo> varDefinitions;

	public static TemplateVo convert(TemplateEntity obj){
		TemplateVo result = null;
		if(obj!=null && StringUtils.isNotBlank(obj.getConfig())){
			result = JsonUtil.GSON.fromJson(obj.getConfig(), TemplateVo.class);
			if(result!=null){
				result.setId(obj.getId());
				result.setTemplateKey(obj.getTemplateKey());
				result.setVersion(obj.getVersion());
				result.setFileUrl("/template/downloadFile?templateKey="+obj.getTemplateKey());
			}
		}
		return result;
	}

	public static boolean isValid(TemplateVo obj){
		return obj!=null && obj.getId()!=null && obj.getId()>0;

	}

}
