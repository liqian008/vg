package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用于生成代码的表对象
 * @author bruce
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTableVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 原始数据表名称 */
	private String name;
	/** 原始数据表注释 */
	private String remark;

	/** 类名（需要根据情况做驼峰等处理） */
	private String className;
	/** 字段列表 */
	private List<GenerateColumnVo> columns;

	/** TODO 补充其他属性 */

	/**
	 * 字段
	 */
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GenerateColumnVo{
		/** 原始字段名 */
		private String name;
		/** 原始字段注释 */
		private String remark;

		/** 基本Java类型 */
		private String fieldType;
		/** 字段名称（需要根据情况做驼峰等处理） */
		private String fieldName;

		/** 是否是日期字段 */
		private boolean isDate;
		/** 是否是blob字段 */
		private boolean isBlob;

	}

}