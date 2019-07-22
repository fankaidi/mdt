package com.kensure.mdt.lc.model;

import java.util.HashMap;
import java.util.Map;

import com.kensure.mdt.entity.SysUser;

import co.kensure.frame.BaseInfo;

/**
 * 待办 对象类
 * @author fankd created on 2019-7-20
 * @since
 */
public class LCDaiBan extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**流程统一待办*/		
	private Long id; 

	/**业务类型，比如是mdt申请的代办*/		
	private String busitype; 

	/**节点名称，表示是那个步骤的名称*/		
	private String entryName; 

	/**操作人*/		
	private Integer userid; 

	/**业务id*/		
	private Long bisiid; 

	/**内容*/		
	private String title; 

	/**申请人*/		
	private Integer applyPersonId; 

	/**
	 * 用户
	 */
	private SysUser user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getBusitype() {
		return busitype;
	}
	
	public String getBusitypeStr() {
		Map<String,String> map = new HashMap<>();
		map.put("mdt_apply", "MDT申请");
		map.put("mdt_team", "MDT团队申请");
		map.put("mdt_team_objective", "MDT团队年度评估");
		map.put("mdt_team_assess", "MDT团队建设期满2年评估");
		return map.get(busitype);
	}

	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Long getBisiid() {
		return bisiid;
	}

	public void setBisiid(Long bisiid) {
		this.bisiid = bisiid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getApplyPersonId() {
		return applyPersonId;
	}

	public void setApplyPersonId(Integer applyPersonId) {
		this.applyPersonId = applyPersonId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
	
	
}
