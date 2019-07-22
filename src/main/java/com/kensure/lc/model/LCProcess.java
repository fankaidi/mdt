package com.kensure.lc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import co.kensure.frame.BaseInfo;

/**
 * 流程实例对象类
 * 
 * @author fankd created on 2019-7-22
 * @since
 */
public class LCProcess extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 流程实例 */
	private Long id;

	/** 流程id */
	private Long defineId;

	/** 名称 */
	private String name;

	/** 业务类型表名 */
	private String typeName;

	/** 业务id */
	private Long busiid;

	/** 当前节点id */
	private Long curItemid;

	/**
	 * 流程实例定义下的节点
	 */
	private List<LCProcessItem> items;

	public LCProcess() {
	}

	public LCProcess(LCDefine define) {
		BeanUtils.copyProperties(define, this);
		this.setDefineId(this.getId());
		this.setId(null);
		//节点
		List<LCProcessItem>  list = new ArrayList<>();
		for(LCDefineItem pi:define.getItems()){
			LCProcessItem item = new LCProcessItem(pi);
			list.add(item);
		}
		this.setItems(list);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDefineId() {
		return defineId;
	}

	public void setDefineId(Long defineId) {
		this.defineId = defineId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getBusiid() {
		return busiid;
	}

	public void setBusiid(Long busiid) {
		this.busiid = busiid;
	}

	public Long getCurItemid() {
		return curItemid;
	}

	public void setCurItemid(Long curItemid) {
		this.curItemid = curItemid;
	}

	public List<LCProcessItem> getItems() {
		return items;
	}

	public void setItems(List<LCProcessItem> items) {
		this.items = items;
	}

}
