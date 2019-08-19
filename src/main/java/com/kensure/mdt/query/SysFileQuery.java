package com.kensure.mdt.query;

import java.util.Date;

import co.kensure.frame.BaseInfo;

/**
 * 文件表对象类
 */
public class SysFileQuery extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 标题 */
	private String titleLike;

	private Date startCreatedTime;

	private Date endCreatedTime;

	public String getTitleLike() {
		return titleLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public Date getStartCreatedTime() {
		return startCreatedTime;
	}

	public void setStartCreatedTime(Date startCreatedTime) {
		this.startCreatedTime = startCreatedTime;
	}

	public Date getEndCreatedTime() {
		return endCreatedTime;
	}

	public void setEndCreatedTime(Date endCreatedTime) {
		this.endCreatedTime = endCreatedTime;
	}
	
}
