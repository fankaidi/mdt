package com.kensure.lc.model;

import org.springframework.beans.BeanUtils;

import co.kensure.frame.BaseInfo;

/**
 * 流程实例节点对象类
 * @author fankd created on 2019-7-22
 * @since
 */
public class LCProcessItem extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**流程实例节点*/		
	private Long id; 

	/**流程id*/		
	private Long defineId; 

	/**实例id*/		
	private Long processId; 

	/**节点名称*/		
	private String entryName; 

	/**上一个节点id,没有就是0*/		
	private Long backId; 

	/**下一个节点id,没有就是0*/		
	private Long nextId; 

	/**节点类型，0是开始,1是一般节点，-1是结束节点*/		
	private Integer typeId; 

	/**绑定角色的code*/		
	private String roleId; 

	/**退回类型，1是重新开始申请，2是退回上一步*/		
	private Integer backType; 

	/**执行人id*/		
	private Long userid; 

	/**节点id*/		
	private Long itemId; 

	public LCProcessItem(){}
	
	public LCProcessItem(LCDefineItem item){
		BeanUtils.copyProperties(item, this);
		this.setItemId(item.getId());
		this.setId(null);
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
	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public Long getBackId() {
		return backId;
	}

	public void setBackId(Long backId) {
		this.backId = backId;
	}
	public Long getNextId() {
		return nextId;
	}

	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Integer getBackType() {
		return backType;
	}

	public void setBackType(Integer backType) {
		this.backType = backType;
	}
	public Long getCreatedUserid() {
		return createdUserid;
	}

	public void setCreatedUserid(Long createdUserid) {
		this.createdUserid = createdUserid;
	}
	public String getCreatedOrgid() {
		return createdOrgid;
	}

	public void setCreatedOrgid(String createdOrgid) {
		this.createdOrgid = createdOrgid;
	}
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
}
