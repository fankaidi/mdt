package com.kensure.mdt.entity.query;

import java.io.Serializable;
import java.util.Date;

public class MdtTeamQuery implements Serializable {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 申请人 */
	private String applyPerson;

	/** MDT名称 */
	private String nameLike;

	/** 审核状态 (0:未审核 2:科主任审核 3:医务部主任审核 4:分管院长审核) */
	private String auditStatus;

	private Date startDate;

	private Date endDate;

	public String getApplyPerson() {
		return applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
