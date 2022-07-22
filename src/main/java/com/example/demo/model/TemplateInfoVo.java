package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 模板基础信息对象
 * @author bruce
 */
@ApiModel(value="模板基础信息")
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class TemplateInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

//	/** 模版名称（即其文件夹名称） */
//	private String templateKey;
//	/** 模版名称 */
//	private String title;
//	/** 模版描述 */
//	private String description;
//	/** 模版作者信息 */
//	private TemplateAuthorVo author;
	/** 模版Git信息（TODO 即支持手动上传模板的zip，也支持从git仓库中下载） */
//	private TemplateGitVo gitInfo;

//	/** 模版Git地址 */
//	private String gitUrl;


//	@ApiModel(value="模板作者对象")
//	@Data
//	@NoArgsConstructor
//	@AllArgsConstructor
//	@Builder
//	public static class TemplateAuthorVo implements Serializable {
//
//		private static final long serialVersionUID = 1L;
//
//		/** 模版作者名称 */
//		private String authorName;
//		/** 模版作者ID */
//		private String authorUid;
//		/** 模版作者Email */
//		private String authorEmail;
//	}

	@ApiModel(value="git信息对象")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class TemplateGitVo implements Serializable {

		private static final long serialVersionUID = 1L;

		/** git地址 */
		private String url;
		/** git分支 */
		private String branch;

	}

}
