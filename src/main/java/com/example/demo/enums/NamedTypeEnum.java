//package com.example.demo.enums;
//
//import com.example.demo.consts.Constants;
//
///**
// * 命名的枚举（支持扩展）
// * @author bruce
// */
//public enum NamedTypeEnum {
//
//	/** "驼峰" */
//	CAMEL_CASE((short)0, Constants.NAMED_CAMEL_CASE),
//	/** "类名（首字母大写 + 后面驼峰）" */
//	CLASS_NAME((short)1, Constants.NAMED_CLASS_CASE)
//	;
//
//	private short value;
//	private String name;
//
//	NamedTypeEnum(short value , String name) {
//		this.value = value;
//		this.name = name;
//	}
//
//	public short getValue() {
//		return value;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//
//	public static NamedTypeEnum valueOf(short value){
//		NamedTypeEnum result = null;
//		for(NamedTypeEnum loop: NamedTypeEnum.values()){
//			if(loop.getValue()==value){
//				result = loop;
//				break;
//			}
//		}
//		return result;
//	}
//
//
//}
