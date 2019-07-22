package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

/**
 * 文件项表对象类
 */
public class SysFileItem extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**文件项表*/		
	private Long id; 

	/**主表id*/		
	private Long fileId; 

	/**文件名称*/		
	private String fileName; 

	/**文件路径*/		
	private String filePath; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
