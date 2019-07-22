package com.kensure.mdt.entity;

import java.util.Date;

import co.kensure.frame.BaseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户表对象类
 */
public class SysUser extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 用户表 */
	private Long id;

	/** 工号 */
	private String number;

	/** 姓名 */
	private String name;

	/** password */
	private String password;

	/** 年龄 */
	private Long age;

	/** 生日 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;

	/** 所在科室 */
	private String department;

	/** 职称 */
	private String title;

	/** 学历 */
	private String education;

	/** 手机长号 */
	private String phone;

	/** 手机短号 */
	private String phoneCornet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
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

}
