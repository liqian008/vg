package com.example.demo.model.vo;

import com.example.demo.model.entity.DataSourceEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据源对象
 * @author bruce
 */
@ApiModel(value="数据源对象", description="数据源对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 数据源id */
	private Integer id;
	/** 数据源名称 */
	private String title;
	/** 数据源描述 */
	private String description;

	/** 数据库类型对象 */
	private DbTypeVo dbType;

	private String jdbcUrl;

	private String jdbcDriver;

	private String username;

	private String password;

	public static DataSourceVo convert(DataSourceEntity obj){
		DataSourceVo result = null;
		if(obj!=null){
			result = new DataSourceVo();
			result.setId(obj.getId());
			result.setTitle(obj.getTitle());
			result.setDescription(obj.getDescription());
			result.setJdbcDriver(obj.getJdbcDriver());
			result.setJdbcUrl(obj.getJdbcUrl());
			result.setUsername(obj.getUsername());
			if(obj.getDbType()!=null){
				DbTypeVo dbTypeVo = DbTypeVo.loadDbType(obj.getDbType());
				result.setDbType(dbTypeVo);
			}
		}
		return result;
	}


	public static DataSourceEntity convert(DataSourceVo obj){
		DataSourceEntity result = null;
		if(obj!=null){
			result = new DataSourceEntity();
			result.setId(obj.getId());
			result.setTitle(obj.getTitle());
			result.setDescription(obj.getDescription());
			result.setJdbcDriver(obj.getJdbcDriver());
			result.setJdbcUrl(obj.getJdbcUrl());
			result.setUsername(obj.getUsername());
			result.setDbType(obj.getDbType()==null?null:obj.getDbType().getDbType());
		}
		return result;
	}

	public static boolean isValid(DataSourceVo obj){
		return obj!=null && obj.getId()!=null && obj.getId()>0;
	}


}
