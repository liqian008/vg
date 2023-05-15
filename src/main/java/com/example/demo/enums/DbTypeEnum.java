package com.example.demo.enums;

import com.example.demo.consts.Constants;

/**
 * 数据库类型的枚举
 * @author bruce
 */
public enum DbTypeEnum {

	MYSQL((short)0, Constants.DB_TYPE_MSYQL),
	ORACLE((short)1, Constants.DB_TYPE_ORACLE)
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


	public static DbTypeEnum valueOf(short value){
		DbTypeEnum result = null;
		for(DbTypeEnum loop: DbTypeEnum.values()){
			if(loop.getValue()==value){
				result = loop;
				break;
			}
		}
		return result;
	}


}
