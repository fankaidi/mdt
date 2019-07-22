package com.kensure.mdt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 评分项维护表对象类
 */
public class SysGrade implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**评分项维护表*/		
	private Long id; 

	/**类型 1:MDT专家对组织科室评价*/		
	private String type; 

	/**打分说明*/		
	private String description; 

	/**打分值*/		
	private String minValue; 

	/**打分值*/		
	private String maxValue; 

	/**创建时间*/		
	private Date createTime; 

	/**更新时间*/		
	private Date updateTime; 


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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
