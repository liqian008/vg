package com.example.demo.model.entity;

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
	private Short dbType;

	private String title;

	private String description;

	private String jdbcUrl;

	private String jdbcDriver;

	private String username;

	private String password;

	private Short status;

	private Integer createUid;

	private Integer updateUid;

	private Date createTime;


	private Date updateTime;

	public static boolean isValid(DataSourceEntity obj){
		return obj!=null && obj.getId()!=null && obj.getId()>0;

	}

}
