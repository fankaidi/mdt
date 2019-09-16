package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

/**
 * 机构表对象类
 */
public class SysOrg extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 机构表 */
	private String id;

	/** 机构名称 */
	private String name;

	/** 父id */
	private String pid;

	/** 片区 */
	private String area;

	/** 排序 */
	private Long sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	public SysTree toTree(){
		SysTree tree = new SysTree();
		tree.setId(id);
		tree.setText(name);
		return tree;
	}


}
