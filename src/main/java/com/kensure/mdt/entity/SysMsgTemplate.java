package com.kensure.mdt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信模板表对象类
 */
public class SysMsgTemplate implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**短信模板表*/		
	private Long id; 

	/**1:MDT发送短信*/		
	private Long type; 

	/**内容*/		
	private String content;

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
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
