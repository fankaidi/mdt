package com.kensure.mdt.model;

import co.kensure.frame.BaseInfo;;

/**
 * mdt团队文本数据对象类
 * @author fankd created on 2019-8-13
 * @since
 */
public class MdtTeamText extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**团队id*/		
	private Long id; 

	/**MDT病种纳入标准和诊疗规范（指南）*/		
	private String standard; 

	/**责任目标任务安排计划*/		
	private String plan; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}
	
	
}
