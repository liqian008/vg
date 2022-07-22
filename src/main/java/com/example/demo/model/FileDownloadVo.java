package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件下载对象
 * @author bruce
 */
@ApiModel(value="文件下载对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("文件名")
	private String filename;
	@ApiModelProperty("下载地址")
	private String downloadUrl;

}
