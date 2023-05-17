package com.example.demo.enums;

import com.example.demo.consts.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 数据库类型的枚举
 * @author bruce
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT) //将枚举类型序列化为对象
public enum DbTypeEnum {

	MYSQL((short)0, Constants.DB_TYPE_MSYQL, Constants.DB_TYPE_MSYQL),
	ORACLE((short)1, Constants.DB_TYPE_ORACLE, Constants.DB_TYPE_ORACLE),
//	UNKNOWN((short)-1, Constants.DB_TYPE_UNKNOWN)
	;

	private short value;
	private String key;
	private String description;

	DbTypeEnum(short value, String key, String description) {
		this.value = value;
		this.key = key;
		this.description = description;
	}

	public short getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
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
