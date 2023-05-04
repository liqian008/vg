package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 元数据-表vo对象
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

	private List<MetadataColumnVo> columns;
	/** TODO 补充其他属性 */


}