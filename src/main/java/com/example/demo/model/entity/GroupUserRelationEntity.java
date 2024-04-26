package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 组用户关系Entity
 * @author bruce
 */
@Data
@TableName("tb_group_user_relation")
public class GroupUserRelationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@TableField
	private Integer groupId;

	@TableField
	private Integer userId;

//	@TableField
//	private Integer role;

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
