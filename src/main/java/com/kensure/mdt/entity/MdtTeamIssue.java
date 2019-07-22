package com.kensure.mdt.entity;

import java.util.Date;

import co.kensure.frame.BaseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * MDT团队课题表对象类
 */
public class MdtTeamIssue extends BaseInfo{
	protected static final long serialVersionUID = 3545276994084105527L;
	/**MDT团队课题表*/		
	private Long id; 

	/**团队id*/		
	private Long teamId; 

	/**项目名称*/		
	private String name; 

	/**项目研究时间*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date researchDate; 

	/**项目经费*/		
	private String projectFund; 

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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Date getResearchDate() {
		return researchDate;
	}

	public void setResearchDate(Date researchDate) {
		this.researchDate = researchDate;
	}
	public String getProjectFund() {
		return projectFund;
	}

	public void setProjectFund(String projectFund) {
		this.projectFund = projectFund;
	}
}
