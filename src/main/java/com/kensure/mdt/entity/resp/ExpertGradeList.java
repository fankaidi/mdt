package com.kensure.mdt.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kensure.mdt.entity.MdtGradeItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MDT参加专家表对象类
 */
public class ExpertGradeList implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT参加专家表*/		
	private Long id; 

	/**MDT申请id*/		
	private Long applyId; 

	/**用户id*/		
	private Long userId; 

	/**专家姓名*/		
	private String name; 

	/**科室*/		
	private String department; 

	/**职称*/		
	private String title; 

	/**手机号*/		
	private String phone; 

	/**手机短号*/		
	private String phoneCornet;

	/***/
	private String reply;

	/***/
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
	private Date replyTime;

	private List<MdtGradeItem> list = new ArrayList<>();

	/**创建时间*/		
	private Date createTime; 

	/**更新时间*/		
	private Date updateTime;

	public List<MdtGradeItem> getList() {
		return list;
	}

	public void setList(List<MdtGradeItem> list) {
		this.list = list;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoneCornet() {
		return phoneCornet;
	}

	public void setPhoneCornet(String phoneCornet) {
		this.phoneCornet = phoneCornet;
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
