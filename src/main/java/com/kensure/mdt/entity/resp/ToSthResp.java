package com.kensure.mdt.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class ToSthResp implements Serializable{


	private Long id;

	private String type;

	private String applyPerson;

	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
	private Date applyDate;

	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public String getApplyPerson() {
		return applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
