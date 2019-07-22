package com.kensure.mdt.entity;

import java.io.Serializable;

/**
 * 角色权限表对象类
 */
public class SysRoleMenu implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/***/		
	private Long roleId;

	/***/		
	private Long menuId;


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}
