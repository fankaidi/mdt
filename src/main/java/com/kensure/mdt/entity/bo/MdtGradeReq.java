package com.kensure.mdt.entity.bo;

import java.util.List;

public class MdtGradeReq {

	private Long applyId;

	private Long userId;

	private String userName;

	private List<MdtGradeItemReq> list;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<MdtGradeItemReq> getList() {
		return list;
	}

	public void setList(List<MdtGradeItemReq> list) {
		this.list = list;
	}
}
