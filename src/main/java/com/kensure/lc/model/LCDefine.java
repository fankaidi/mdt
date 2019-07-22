package com.kensure.lc.model;

import java.util.List;

import co.kensure.frame.BaseInfo;

/**
 * 流程定义表 对象类
 * @author fankd created on 2019-7-22
 * @since
 */
public class LCDefine extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**流程定义表*/		
	private Long id; 

	/**名称*/		
	private String name; 

	/**
	 * 流程定义下的节点
	 */
	private List<LCDefineItem> items;

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

	public List<LCDefineItem> getItems() {
		return items;
	}

	public void setItems(List<LCDefineItem> items) {
		this.items = items;
	}
	
}
