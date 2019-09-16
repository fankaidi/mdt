package com.kensure.mdt.entity;

import java.util.ArrayList;
import java.util.List;

import co.kensure.frame.BaseInfo;

/**
 * 树对象类
 */
public class SysTree extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** id */
	private String id;

	/** 名称 */
	private String text;

	/** 子对象 */
	private List<SysTree> children = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<SysTree> getChildren() {
		return children;
	}

	public void setChildren(List<SysTree> children) {
		this.children = children;
	}

}
