package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

public class MdtTeamInfo extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT团队基本信息表（多人明细）*/		
	private Long id; 

	/**团队id*/		
	private Long teamId; 

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

	/**专家类型*/		
	private String specialistType; 

	/**专家对象*/		
	private SysUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
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
	public String getSpecialistType() {
		return specialistType;
	}

	public void setSpecialistType(String specialistType) {
		this.specialistType = specialistType;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
	
}
