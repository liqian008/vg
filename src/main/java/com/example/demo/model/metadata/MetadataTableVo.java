package com.example.demo.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 标准元数据-表vo对象
 * @author bruce
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataTableVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private MetadataDatabaseVo database;

	/** 数据表名称 */
	private String tableName;
	/** 注释 */
	private String remark;

	/** 是否包含日期类型 */
	private boolean containsDate;
	/** 是否包含时间类型 */
	private boolean containsDatetime;

	private List<MetadataColumnVo> columns;
	/** TODO 补充其他属性 */


}