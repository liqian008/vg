package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户Entity
 * @author bruce
 */
@Data
@TableName("tb_user")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@TableField
	private String username;

	@TableField
	private String nickname;

	@TableField
	private String avatarUrl;

	@TableField
	private String email;

	@TableField
	private short status;

	@TableField
	private Date createTime;

	@TableField
	private Date updateTime;

	public static boolean isValid(UserEntity entity){
		return entity!=null && entity.getId()!=null && entity.getId()>0;

	}

}
