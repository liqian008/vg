package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author bruce
 */
@ApiModel("Generate配置基类")
@Data
public class GenerateConfigBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="模板key")
	protected String templateKey;

	@ApiModelProperty(value="用户指定的变量值, Map类型")
	protected Map<String, String> varMap;


}
