package com.kensure.mdt.entity.query;

import co.kensure.frame.BaseInfo;

/**
 * MDT申请表查询对象
 */
public class MdtApplyQuery extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	

	/**-1是作废   申请状态  1是申请人申请，2是科室主任同意，9是审核退回，11是病人缴费 ，12是发短信通知 13是mdt会诊 ，15是申请人填写反馈,19 完成   */		
	private Integer applyStatus;

	private Integer startApplyStatus;
	

	public Integer getApplyStatus() {
		return applyStatus;
	}


	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}


	public Integer getStartApplyStatus() {
		return startApplyStatus;
	}


	public void setStartApplyStatus(Integer startApplyStatus) {
		this.startApplyStatus = startApplyStatus;
	} 	
	
	
	
}
