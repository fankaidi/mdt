package com.kensure.mdt.entity;

import java.io.Serializable;

/**
 * 用户角色表对象类
 */
public class SysUserRole implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/***/		
	private Long userId;

	/***/		
	private Long roleId;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
