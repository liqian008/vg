package com.example.demo.model;

import com.example.demo.enums.GenerateTypeEnum;
import com.example.demo.enums.OutputTypeEnum;
import com.example.demo.exception.GenericRuntimeException;
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

	@ApiModelProperty(value="代码输出类型，默认为FILES（非ZIP）")
	private String outputType = OutputTypeEnum.FILES.name();

	@ApiModelProperty(value="表crud配置，当generateType为TABLE_CRUD时有效")
	private TableCrudConfig tableCrudConfig;

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
	public static class TableCrudConfig implements Serializable {

		private static final long serialVersionUID = 1L;

		@ApiModelProperty(value="数据源id")
		private int datasourceId;

		@ApiModelProperty(value="数据库名称")
		protected String dbName;

		@ApiModelProperty(value="是否选择所有表，选择所有表则认为是默认配置")
		protected boolean chooseAll;

		@ApiModelProperty(value="数据表名，当chooseAll时为空")
		protected List<TableInfo> tables;
//		protected List<String> tableNames;

		@ApiModelProperty(value="要进行替换的表的前缀名，当chooseAll时使用本属性处理")
		private String tablenamePrefix;

		//TODO 字段的扩展能力？

	}


	/**
	 * 表信息对象
	 */
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TableInfo implements Serializable {

		private static final long serialVersionUID = 1L;

		@ApiModelProperty(value="表名")
		protected String tableName;

		@ApiModelProperty(value="要进行替换的表的前缀名，支持针对性替换")
		private String tablenamePrefix;

		@ApiModelProperty(value="表的自定义扩展数据，提供模板数据的良好扩展性")
		private Map<String, Object> extraData;

		//TODO 字段的扩展能力？

	}

	/**
	 * 是否是数据库表CRUD类型
	 * @param generateConfig
	 * @return
	 */
	public static boolean isGenerateCrud(GenerateConfigBase generateConfig){
		return judgeGenerateType(generateConfig, GenerateTypeEnum.TABLE_CRUD);
	}

	/**
	 * 是否是项目工程代码类型
	 * @param generateConfig
	 * @return
	 */
	public static boolean isGenerateProject(GenerateConfigBase generateConfig){
		return judgeGenerateType(generateConfig, GenerateTypeEnum.PROJECT);
	}


	private static boolean judgeGenerateType(GenerateConfigBase generateConfig, GenerateTypeEnum tableCrud) {
		if(generateConfig==null){
			throw new GenericRuntimeException("配置不能为空");
		}
		return tableCrud.name().equalsIgnoreCase(generateConfig.getGenerateType());
	}

	/**
	 * 是否output zip文件
	 * @param generateConfig
	 * @return
	 */
	public static boolean isOutputZip(GenerateConfigBase generateConfig){
		return judgeOutputType(generateConfig, OutputTypeEnum.FILE_ZIP);
	}

	/**
	 * 是否输出常规文件
	 * @param generateConfig
	 * @return
	 */
	public static boolean isOuputFiles(GenerateConfigBase generateConfig){
		return judgeOutputType(generateConfig, OutputTypeEnum.FILES);
	}

	private static boolean judgeOutputType(GenerateConfigBase generateConfig, OutputTypeEnum ouputType) {
		if(generateConfig==null){
			throw new GenericRuntimeException("配置不能为空");
		}
		return ouputType.name().equalsIgnoreCase(generateConfig.getOutputType());
	}


}
