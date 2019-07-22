package com.kensure.mdt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典维护表对象类
 */
public class SysCode implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**字典维护表*/		
	private Long id; 

	/**类型 1:MDT目的*/		
	private String type; 

	/**code*/		
	private String code; 

	/**value*/		
	private String value; 

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
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
