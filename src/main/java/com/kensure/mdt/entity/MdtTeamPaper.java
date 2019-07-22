package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * MDT团队论文表对象类
 */
public class MdtTeamPaper extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT团队论文表*/		
	private Long id; 

	/**团队id*/		
	private Long teamId; 

	/**论文题目*/		
	private String title; 

	/**期刊号*/		
	private String serialNumber; 

	/**发表时间*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date postedDate; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
}
