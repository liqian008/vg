package com.example.demo.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 标准元数据-字段vo对象
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
	/** 数据字段类型 */
	private Class dataTypeClazz;
	/** 数据字段类型 */
	private String dataType;

//	/** 索引key，用于判断主键 */
//	private String columnKey;
	/** 是否唯一主键 */
	private Boolean primaryKey;
	/** 是否自增 */
	private Boolean autoIncrement;

//	/** 扩展数据 */
//	private String extra;

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


	private void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public String getDataType() {
		return dataTypeClazz.getSimpleName();
	}


}