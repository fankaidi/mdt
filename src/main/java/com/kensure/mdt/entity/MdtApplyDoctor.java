package com.kensure.mdt.entity;

import java.util.List;

import co.kensure.frame.BaseInfo;

/**
 * MDT参加专家表对象类
 */
public class MdtApplyDoctor extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** MDT参加专家表 */
	private Long id;

	/** MDT申请id */
	private Long applyId;

	/** 用户id */
	private Long userId;

	/** 专家姓名 */
	private String name;

	/** 科室 */
	private String department;

	/** 职称 */
	private String title;

	/** 手机号 */
	private String phone;

	/** 手机短号 */
	private String phoneCornet;

	/** 科室对专家评分 */
	private List<MdtGradeItem> ksPinFenList;
	/** 专家对科室评分 */
	private List<MdtGradeItem> zjPinFenList;
	/** 专家意见 */
	private MdtApplyOpinion zjYiJian;

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

	public List<MdtGradeItem> getKsPinFenList() {
		return ksPinFenList;
	}

	public void setKsPinFenList(List<MdtGradeItem> ksPinFenList) {
		this.ksPinFenList = ksPinFenList;
	}

	public MdtApplyOpinion getZjYiJian() {
		return zjYiJian;
	}

	public void setZjYiJian(MdtApplyOpinion zjYiJian) {
		this.zjYiJian = zjYiJian;
	}

	public List<MdtGradeItem> getZjPinFenList() {
		return zjPinFenList;
	}

	public void setZjPinFenList(List<MdtGradeItem> zjPinFenList) {
		this.zjPinFenList = zjPinFenList;
	}

}
