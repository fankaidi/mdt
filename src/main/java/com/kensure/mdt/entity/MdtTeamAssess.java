package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

import com.kensure.lc.model.LCHistory;

/**
 * MDT团队建设期满2年评估表对象类
 */
public class MdtTeamAssess extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT团队建设期满2年评估表*/		
	private Long id; 

	/**团队id*/		
	private Long teamId; 

	/**病例总数*/		
	private Long caseSum; 

	/**年度1*/		
	private Long year1; 

	/**年度1病例数*/		
	private Long caseNum1; 

	/**年度2*/		
	private Long year2; 

	/**年度2病例数*/		
	private Long caseNum2; 

	/**建期两年教学情况*/		
	private String teachingSituation; 

	/**培养研究生数量*/		
	private Long trainNum; 

	/**住院医生数量*/		
	private Long hospitalNum; 

	/**主治医生数量*/		
	private Long majorNum; 

	/**副主任医生数量*/		
	private Long assistantMajorNum; 

	/**主任医生数量*/		
	private Long directorNum;
	
	/**审批时的意见*/		
	private LCHistory yijian;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public Long getCaseSum() {
		return caseSum;
	}

	public void setCaseSum(Long caseSum) {
		this.caseSum = caseSum;
	}
	public Long getYear1() {
		return year1;
	}

	public void setYear1(Long year1) {
		this.year1 = year1;
	}
	public Long getCaseNum1() {
		return caseNum1;
	}

	public void setCaseNum1(Long caseNum1) {
		this.caseNum1 = caseNum1;
	}
	public Long getYear2() {
		return year2;
	}

	public void setYear2(Long year2) {
		this.year2 = year2;
	}
	public Long getCaseNum2() {
		return caseNum2;
	}

	public void setCaseNum2(Long caseNum2) {
		this.caseNum2 = caseNum2;
	}
	public String getTeachingSituation() {
		return teachingSituation;
	}

	public void setTeachingSituation(String teachingSituation) {
		this.teachingSituation = teachingSituation;
	}
	public Long getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(Long trainNum) {
		this.trainNum = trainNum;
	}
	public Long getHospitalNum() {
		return hospitalNum;
	}

	public void setHospitalNum(Long hospitalNum) {
		this.hospitalNum = hospitalNum;
	}
	public Long getMajorNum() {
		return majorNum;
	}

	public void setMajorNum(Long majorNum) {
		this.majorNum = majorNum;
	}
	public Long getAssistantMajorNum() {
		return assistantMajorNum;
	}

	public void setAssistantMajorNum(Long assistantMajorNum) {
		this.assistantMajorNum = assistantMajorNum;
	}
	public Long getDirectorNum() {
		return directorNum;
	}

	public void setDirectorNum(Long directorNum) {
		this.directorNum = directorNum;
	}

	public LCHistory getYijian() {
		return yijian;
	}

	public void setYijian(LCHistory yijian) {
		this.yijian = yijian;
	}
	
	
}
