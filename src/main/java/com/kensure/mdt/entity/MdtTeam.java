package com.kensure.mdt.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kensure.frame.BaseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kensure.lc.model.LCHistory;
import com.kensure.mdt.entity.bo.MdtTeamYueDu;

/**
 * MDT团队表对象类
 */
public class MdtTeam extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT团队表*/		
	private Long id; 

	/**申请人*/		
	private String proposer; 

	/**MDT名称*/		
	private String name; 

	/**申请日期*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date date; 

	/**MDT病种纳入标准和诊疗规范（指南）*/
	private String standard;

	/**
	 * 年度审核状态0是未发起  1:发起 2:专家填写 3:审核完成
	 */
	private String annualStatus;

	/**
	 * 满两年度审核状态 0:未发起 1:发起 2:专家填写 3:审核通过 4:审核不通过
	 */
	private String twoYearStatus;

	/**(0:未提交 1:已提交未审核 2:科主任已审核 3:医务部主任已审核 4:分管院长已审核)*/		
	private String auditStatus;

	/**是否删除 1是 0否*/
	private String isDelete;

	
	/**审批时的意见*/		
	private LCHistory yijian;
	/**
	 * 团队成员
	 */
	List<MdtTeamInfo> menbers;

	/**
	 * 月度评估数据
	 */
	List<MdtTeamYueDu> yueDuPinGuList;
	
	/**
	 * 年度指标数据
	 */
	List<MdtTeamObjective> objList;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getAnnualStatus() {
		return annualStatus;
	}

	public void setAnnualStatus(String annualStatus) {
		this.annualStatus = annualStatus;
	}

	public String getTwoYearStatus() {
		return twoYearStatus;
	}

	public void setTwoYearStatus(String twoYearStatus) {
		this.twoYearStatus = twoYearStatus;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public LCHistory getYijian() {
		return yijian;
	}

	public void setYijian(LCHistory yijian) {
		this.yijian = yijian;
	}

	public List<MdtTeamInfo> getMenbers() {
		return menbers;
	}

	public void setMenbers(List<MdtTeamInfo> menbers) {
		this.menbers = menbers;
	}

	public List<MdtTeamYueDu> getYueDuPinGuList() {
		return yueDuPinGuList;
	}

	public void setYueDuPinGuList(List<MdtTeamYueDu> yueDuPinGuList) {
		this.yueDuPinGuList = yueDuPinGuList;
	}

	public Map<String,MdtTeamYueDu> getYueDuPinGuMap() {
		Map<String,MdtTeamYueDu> yueDuPinGuMap = new HashMap<>();	
		if(this.yueDuPinGuList != null){
			for(MdtTeamYueDu temp:this.yueDuPinGuList){
				yueDuPinGuMap.put(temp.getYear()+"-"+temp.getMonth(), temp);
			}
		}
		return yueDuPinGuMap;
	}

	public List<MdtTeamObjective> getObjList() {
		return objList;
	}

	public void setObjList(List<MdtTeamObjective> objList) {
		this.objList = objList;
	}
	
}
