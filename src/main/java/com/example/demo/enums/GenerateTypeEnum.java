package com.example.demo.enums;

/**
 * 生成类型的枚举
 * @author bruce
 */
public enum GenerateTypeEnum {

	PROJECT((short)0,"项目工程"),
	TABLE_CRUD((short)1,"基于数据库表的CRUD代码")
	;

	private short value;
	private String description;

	GenerateTypeEnum(short value , String description) {
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
