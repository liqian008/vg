package com.example.demo.model;

import com.example.demo.enums.GenerateTypeEnum;
import com.example.demo.enums.OutputTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author bruce
 */
@ApiModel("Generate配置基类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateConfigBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="代码生成类型，默认为项目工程（非数据库表CRUD）")
	private String generateType = GenerateTypeEnum.PROJECT.name();

	@ApiModelProperty(value="表crud配置，当generateType为TABLE_CRUD时有效")
	private TableCrudConfig tableCrudConfig;

	@ApiModelProperty(value="代码输出类型，默认为FILES（非ZIP）")
	private String outputType = OutputTypeEnum.FILES.name();

	@ApiModelProperty(value="模板key")
	protected String templateKey;

	@ApiModelProperty(value="用户指定的变量值, Map类型")
	protected Map<String, String> varMap;


	/**
	 * 表CRUD配置
	 */
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	private static class TableCrudConfig implements Serializable {

		private static final long serialVersionUID = 1L;

		@ApiModelProperty(value="数据源id")
		private int datasourceId;

		@ApiModelProperty(value="数据库名称")
		protected String dbName;

		@ApiModelProperty(value="是否选择所有表")
		protected boolean chooseAll;

		@ApiModelProperty(value="数据表名")
		protected List<String> tableNames;

		@ApiModelProperty(value="要进行替换的表的前缀名")
		private String tablenamePrefix;
	}


}
