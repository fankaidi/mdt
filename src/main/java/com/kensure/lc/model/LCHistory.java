package com.kensure.lc.model;

import co.kensure.frame.BaseInfo;

import com.kensure.mdt.entity.SysUser;

/**
 * 审核意见 对象类
 * @author fankd created on 2019-7-20
 * @since
 */
public class LCHistory extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**处理过的审批痕迹*/		
	private Long id; 

	/**业务类型，比如是mdt申请的代办*/		
	private String busitype; 

	/**节点名称，表示是那个步骤的名称*/		
	private String entryName; 

	/**操作人*/		
	private Long userid; 

	/**业务id*/		
	private Long bisiid; 

	/**审核结果，1是通过，-1是不通过*/		
	private Integer auditResult; 

	/**审核意见*/		
	private String auditOpinion; 

	/**
	 * 用户
	 */
	private SysUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getBusitype() {
		return busitype;
	}

	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getBisiid() {
		return bisiid;
	}

	public void setBisiid(Long bisiid) {
		this.bisiid = bisiid;
	}
	public Integer getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
	
}
