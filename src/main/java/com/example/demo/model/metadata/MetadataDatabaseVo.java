package com.example.demo.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 标准数据库Vo对象
 * @author bruce
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDatabaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 数据库类型 */
	private String dbName;

	private List<MetadataTableVo> tables;

	/** TODO 补充其他属性 */

}