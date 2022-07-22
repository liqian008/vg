package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 模板作者对象
 * @author bruce
 */
@ApiModel(value="作者对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 作者uid */
	private Integer userId;
	/** 作者用户名 */
	private String username;
	/** 作者昵称 */
	private String nickname;
	/** 作者email */
	private String email;
	/** 头像地址 */
	private String avatarUrl;
}