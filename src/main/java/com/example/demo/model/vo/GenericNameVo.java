package com.example.demo.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用名称对象
 * @author bruce
 */
@ApiModel(value="通用名称对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericNameVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  名称 */
	private String name;

}
