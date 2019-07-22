package com.kensure.lc.model;

import co.kensure.frame.BaseInfo;;

/**
 * 流程节点定义对象类
 * @author fankd created on 2019-7-22
 * @since
 */
public class LCDefineItem extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**流程节点定义*/		
	private Long id; 

	/**流程id*/		
	private Long defineId; 

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

	/**退回类型，-1是无法退回,1是重新开始申请，2是退回上一步*/		
	private Integer backType; 


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
}
