package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 输出类型的枚举
 * @author bruce
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OutputTypeEnum {

	FILES((short)0,"直接生成代码文件，通常用于本地部署场景"),
	FILE_ZIP((short)1,"基于上面生成的代码，并进行Zip打包以便于下载，通常用于Sass化部署场景")
	;

	private short value;
	private String description;

	OutputTypeEnum(short value , String description) {
		this.value = value;
		this.description = description;
	}

	public short getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}



}
