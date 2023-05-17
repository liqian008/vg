package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 生成类型的枚举
 * @author bruce
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenerateTypeEnum {

	PROJECT((short)0,"PROJECT", "项目工程"),
	TABLE_CRUD((short)1,"TABLE_CRUD", "基于数据库表的CRUD代码")
	;

	private short value;
	private String key;
	private String description;

	GenerateTypeEnum(short value, String key, String description) {
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


	public static GenerateTypeEnum valueOf(short value){
		GenerateTypeEnum result = null;
		for(GenerateTypeEnum loop: GenerateTypeEnum.values()){
			if(loop.getValue()==value){
				result = loop;
				break;
			}
		}
		return result;
	}

}
