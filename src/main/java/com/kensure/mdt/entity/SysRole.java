package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

/**
 * 角色表对象类
 * @author fankd created on 2019-6-10
 * @since
 */
public class SysRole extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**角色表*/		
	private Long id; 

	/**角色名称*/		
	private String name;

	/**数据权限级别 1:集团 2:院区 3:部门 (这个没用)4:科室 5:个人*/
	private Integer level;
	
	/**特殊角色标识*/
	private String code;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
