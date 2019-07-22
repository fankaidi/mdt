package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

/**
 * 评分表对象类
 */
public class MdtGradeItem extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**评分表*/		
	private Long id; 

	/**1:专家打分 2:组织科室打分*/		
	private String type; 

	/**MDT申请id*/		
	private Long applyId; 

	/**用户id*/		
	private Long userId; 

	/**评分维护表id*/		
	private Long sysGradeId; 

	/**打分分数*/		
	private Long grade; 

	/**最小分值*/		
	private Long minValue; 

	/**最大分值*/		
	private Long maxValue; 

	/**用户名*/		
	private String description; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSysGradeId() {
		return sysGradeId;
	}

	public void setSysGradeId(Long sysGradeId) {
		this.sysGradeId = sysGradeId;
	}
	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public Long getMinValue() {
		return minValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}
	public Long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
