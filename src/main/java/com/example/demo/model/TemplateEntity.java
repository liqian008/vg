package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板Entity
 * @author bruce
 */
@Data
@TableName("tb_template")
public class TemplateEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * @see com.example.demo.enums.TemplateSourceTypeEnum
	 */
	@TableField
	private Short sourceType;

	@TableField
	private String templateKey;

	@TableField
	private Integer version;

	@TableField
	private String title;

	@TableField
	private String description;

	@TableField
	private String path;

	@TableField
	private String config;

	@TableField
	private Integer createUid;

	@TableField
	private Integer updateUid;

	@TableField
	private short status;

	@TableField
	private Date createTime;

	@TableField
	private Date updateTime;

	public static boolean isValid(TemplateEntity obj){
		return obj!=null && obj.getId()!=null && obj.getId()>0;

	}

}
