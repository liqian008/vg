package com.example.demo.enums;

/**
 * 数据库类型的枚举
 * @author bruce
 */
public enum DbTypeEnum {

	MYSQL((short)0,"MYSQL"),
	ORACLE((short)1,"ORACLE")
	;

	private short value;
	private String name;

	DbTypeEnum(short value ,String name) {
		this.value = value;
		this.name = name;
	}

	public short getValue() {
		return value;
	}

	public String getName() {
		return name;
	}



}
