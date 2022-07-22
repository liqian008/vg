package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 组Entity
 * @author bruce
 */
@Data
@TableName("tb_group")
public class GroupEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@TableField
	private String name;

	@TableField
	private String avatar;

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

	public static boolean isValid(UserEntity entity){
		return entity!=null && entity.getId()!=null && entity.getId()>0;

	}

}
