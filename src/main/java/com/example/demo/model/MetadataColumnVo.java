package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 元数据-字段vo对象
 * TODO 不同数据库类型下的泛型
 * @author bruce
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataColumnVo implements Serializable {

	private static final long serialVersionUID = 1L;


	/** 字段表名称 */
	private String column;
	/** 原始字段类型 */
	private String dataType;

	/** 索引key，用于判断主键 */
	private String columnKey;
	/** 是否唯一主键 */
	private Boolean primaryKey;
	/** 是否自增 */
	private Boolean autoIncrement;
	/** 扩展数据 */
	private String extra;

	/** 长度 */
	private Long length;
	/** 是否为空 */
	private Boolean nullable;
	/** 默认值 */
	private String defaultValue;
	/** 注释 */
	private String remark;
	/** 编码 */
	private String charset;

	/** 对应的表对象 */
	private MetadataTableVo table;

	/** TODO 补充其他属性 */


}