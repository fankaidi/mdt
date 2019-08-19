package com.kensure.lc.query;

import java.util.Date;

import co.kensure.frame.BaseInfo;

/**
 * 流程待办表查询对象
 */
public class LCDaiBanQuery extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	private String nameLike;

	private Date startCreatedTime;

	private Date endCreatedTime;

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
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
