package com.example.demo.consts;

import com.example.demo.enums.DbTypeEnum;

/**
 * @author bruce
 */
public interface Constants {


//	/** 模板存放目录 */
//	String DIRNAME_TEMPLATE_ROOT = "./Templates";
//	/** 代码输出目录 */
//	String DIRNAME_OUTPUT = "./Output";
//	/** 上传文件&解压的临时目录 */
//	String DIRNAME_TEMPLATE_TMP = "./tmp";

	String CHARSET_UTF8 = "UTF-8";
	/**  官方uid */
	int USER_ID_OFFICIAL = 1;

	/** 模板配置文件的名称 */
	String FILENAME_TEMPLATE_CONFIG = "templateConfig.json";
	/** zip文件的后缀名 */
	String SUFFIX_ZIP = ".zip";
	/** 变量，模板名称的key */
	String KEY_TEMPLATE_NAME = "template_name";
	/** 变量，模板描述的key */
	String KEY_TEMPLATE_DESCRIPTION = "template_description";

	String FORMAT_YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";


	/** 生成代码的常量配置， start */


	/** 生成代码方式的key */
	String KEY_GENERATE_OUTPUT_TYPE = "generate_output_type";

	/** 表前缀的key */
	String KEY_TABLE_PREFIX = "table_prefix";

	/** 表对象的key（freemarker中的key） */
	String TABLE = "table";

	/** Mysql数据库类型 */
	String DB_TYPE_MSYQL = "MYSQL";
	/** Oracle数据库类型 */
	String DB_TYPE_ORACLE = "ORACLE";

	/** 首字母大写 */
	String NAMED_CAMEL_CASE = "camel";
	/** 类名 */
	String NAMED_CLASS_CASE = "class";

	/** 元数据process后缀名 */
	String METADATA_PROCESSOR_SUFFIX = "Processor";

//	/** 表名 */
//	String TABLE_NAME = "table_name";
//	/** 表注释 */
//	String TABLE_REMARK = "table_remark";
//	/** 表对应的类名 */
//	String TABLE_CLASS_NAME = "table_class_name";
//
//
//
//	/** 表对象中字段列表的key */
//	String COLUMNS = "columns";
//
//	/** 字段名 */
//	String COLUMN_NAME = "column_name";
//	/** 字段注释 */
//	String COLUMN_REMARK = "column_remark";
//	/** 字段名 */
//	String COLUMN_FIELD_TYPE = "column_field_type";
//	/** 字段所对应的类属性名 */
//	String COLUMN_FIELD_NAME = "column_field_name";

	/** 生成代码的常量配置， end */
}
