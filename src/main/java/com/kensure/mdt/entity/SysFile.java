package com.kensure.mdt.entity;

import java.util.List;

import co.kensure.frame.BaseInfo;

/**
 * 文件表对象类
 */
public class SysFile extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 文件表 */
	private Long id;
	/** 内容 */
	private String content;
	/** 标题 */
	private String title;

	private List<SysFileItem> fileList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SysFileItem> getFileList() {
		return fileList;
	}

	public void setFileList(List<SysFileItem> fileList) {
		this.fileList = fileList;
	}

}
