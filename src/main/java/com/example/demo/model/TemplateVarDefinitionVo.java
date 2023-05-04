package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author bruce
 */
@ApiModel(value="模板变量定义类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateVarDefinitionVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("是否是系统变量")
	private boolean isSystemVar = false;

	@ApiModelProperty("变量key")
	private String key;

	@ApiModelProperty("变量名称")
	private String title;

	@ApiModelProperty("变量描述")
	private String description;

	@ApiModelProperty("默认值")
	private String defaultValue;

	@ApiModelProperty("占位符")
	private String placeholder;

	@ApiModelProperty("是否必填")
	private boolean required=true;

	@Override public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TemplateVarDefinitionVo that = (TemplateVarDefinitionVo) o;
		return key.equals(that.key);
	}

	@Override public int hashCode() {
		return Objects.hash(key);
	}

}
