package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据源Entity
 * @author bruce
 */
@Data
@TableName("tb_datasource")
public class DataSourceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * @see com.example.demo.enums.DbTypeEnum
	 */
	@TableField
	private Short dbType;

	@TableField
	private String title;

	@TableField
	private String description;

	@TableField
	private String jdbcUrl;

	@TableField
	private String jdbcDriver;

	@TableField
	private String username;

	@TableField
	private String password;

	@TableField
	private short status;

	@TableField
	private Integer createUid;

	@TableField
	private Integer updateUid;


	@TableField
	private Date createTime;

	@TableField
	private Date updateTime;

	public static boolean isValid(DataSourceEntity obj){
		return obj!=null && obj.getId()!=null && obj.getId()>0;

	}

}
