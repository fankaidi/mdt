package com.kensure.mdt.entity.query;

import java.io.Serializable;

public class MdtTeamQuery implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;

	/**申请人*/		
	private String applyPerson; 

	/**MDT名称*/		
	private String nameLike;

	/**审核状态 (0:未审核 2:科主任审核 3:医务部主任审核 4:分管院长审核)*/
	private String auditStatus;


	

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
}
