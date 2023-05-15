package com.example.demo.model.metadata.mysql;

import com.example.demo.model.metadata.MetadataColumnVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Mysql字段的元数据-字段vo对象
 * @author bruce
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataMysqlColumnVo implements Serializable {

	private static final long serialVersionUID = 1L;

//	/** 数据类型映射 */
//	private static final Map<String, Class> DATA_TYPE_MAP = new HashMap<>();
//	static{
//		//常用基本类型
//	    DATA_TYPE_MAP.put("bigint", java.lang.Long.class);
//	    DATA_TYPE_MAP.put("int", java.lang.Integer.class);
//	    DATA_TYPE_MAP.put("smallint", java.lang.Short.class);
//	    DATA_TYPE_MAP.put("tinyint", java.lang.Byte.class);
//
//        DATA_TYPE_MAP.put("float", java.lang.Float.class);
//        DATA_TYPE_MAP.put("double", java.lang.Double.class);
//
//	    DATA_TYPE_MAP.put("varchar", java.lang.String.class);
//	    DATA_TYPE_MAP.put("text", java.lang.String.class);
//	    DATA_TYPE_MAP.put("mediumtext", java.lang.String.class);
//	    DATA_TYPE_MAP.put("longtext", java.lang.String.class);
//
//	    DATA_TYPE_MAP.put("timestamp", java.util.Date.class);
//	    DATA_TYPE_MAP.put("date", java.util.Date.class);
//	    DATA_TYPE_MAP.put("datetime", java.util.Date.class);
//
//	    //其他类型暂时不做支持(均使用String类型)
//    }

//    public static void main(String[] args) {
//
//        System.out.println(String.class.toString());
//        System.out.println(Byte.class.getSimpleName());
//    }

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


	/** TODO 补充其他属性 */


//	public static Class parseDataType(String dataTypeText){
//		return parseDataType(dataTypeText, String.class);
//	}
//
//	/**
//	 * 解析mysql的数据类型到基本类型
//	 * @param defaultClass
//	 * @return
//	 */
//	public static Class parseDataType(String dataTypeText, Class defaultClass){
//		Class result = null;
//		if(dataTypeText!=null){
//			result = DATA_TYPE_MAP.get(StringUtils.trim(dataTypeText));
//		}
//		if(result==null){
//			result = defaultClass;
//		}
//		return result;
//	}


    /**
     * 将Mysql元数据对象转换为标准元数据对象
     * @param vo
     * @return
     */
	public static MetadataColumnVo convert(MetadataMysqlColumnVo vo){
		MetadataColumnVo result = null;
		if(vo!=null){
			result = new MetadataColumnVo();


			BeanUtils.copyProperties(vo, result);
		}
		return result;
	}

}