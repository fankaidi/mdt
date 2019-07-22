package com.kensure.mdt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * MDT收费情况维护表对象类
 */
public class SysFee implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT收费情况维护表*/		
	private Long id; 

	/***/		
	private Long min;

	/***/		
	private Long max;

	/***/		
	private Long price;

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
	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}
	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
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
