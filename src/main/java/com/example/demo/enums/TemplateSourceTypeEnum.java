package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 模版来源的枚举
 * @author bruce
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TemplateSourceTypeEnum {

	OFFICIAL((short)0,"官方", "官方模板"),
	USER_CUSTOMIZE((short)1,"自定义", "用户自定义")
	;

	private short value;
	private String name;
	private String description;

	TemplateSourceTypeEnum(short value ,String name, String description) {
		this.value = value;
		this.name = name;
		this.description = description;
	}

	public short getValue() {
		return value;
	}

	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}

}
