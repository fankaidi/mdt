package com.kensure.mdt.entity;

import java.util.Date;
import java.util.List;

import co.kensure.frame.BaseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kensure.lc.model.LCHistory;

/**
 * MDT申请表对象类
 */
public class MdtApply extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT申请表*/		
	private Long id; 

	/**患者类型 1住院 2门诊*/		
	private String patientType; 

	/**姓名*/		
	private String name; 

	/**性别*/		
	private String gender; 

	/**出生日期*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date birthday; 

	/**联系电话*/		
	private String phone; 

	/**入院/首诊时间*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date diagnoseDate; 

	/**住院/门诊号*/		
	private String number; 

	/**嘉和电子病历用户截图*/		
	private String picture; 

	/**病情概述（含主诉、病史、诊断、诊治过程等）*/		
	private String overview; 

	/**检验报告*/		
	private String surveyReport; 

	/**检查报告*/		
	private String inspectionReport; 

	/**MDT时间*/
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
	private Date mdtDate; 

	/**MDT地点*/		
	private String mdtLocation; 

	/**病种名称*/		
	private String diseaseName; 

	/**病种名称其它*/		
	private String otherDiseaseName; 

	/**MDT目的*/		
	private String mdtPurpose; 

	/**MDT目的其它*/		
	private String otherMdtPurpose; 

	/**诊治难点*/		
	private String difficulty; 

	/**是否收费 (1:是 0:否)*/		
	private String isCharge; 

	/**申请人*/		
	private String applyPerson; 
	/**申请人id*/		
	private Integer applyPersonId;
	/**流程意见列表*/		
	private List<LCHistory> lCHistoryList;
	
	/**专家列表*/		
	private List<MdtApplyDoctor> doctors;
	
	/**审批时的意见*/		
	private LCHistory yijian;
	
	/**申请科室*/		
	private SysOrg dept;
	/**申请人姓名*/		
	private SysUser applyUser;

	/**申请递交时间*/
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
	private Date applyCreatetime; 

	/**申请人电话*/		
	private String applyPhone; 

	/**-1是作废   申请状态  1是申请人申请，2是科室主任同意，9是审核退回，11是病人缴费 ，12是发短信通知 13是mdt会诊 ，15是申请人填写反馈,19 完成   */		
	private Integer applyStatus; 	
	/**患者对象*/		
	private SysPatient patientUser;
	
	
	/**是否打印缴费通知单，0否，1是*/		
	private Integer isJiaofei; 
	/**是否打印知情同意书，0否，1是*/		
	private Integer isZhiqing; 
	/**是否短信通知，0否，1是*/		
	private Integer isDuanxin; 
	/**是否会诊意见书，0否，1是*/		
	private Integer isKsdafen; 
	/**是否专家打分，0否，1是*/		
	private Integer isZjdafen; 
	/**是否小结，0否，1是*/		
	private Integer isXiaojie; 
	/**是否作废，0否，1是*/		
	private Integer isZuofei; 
	/**团队id */		
	private Long teamId; 	
	/**推荐人id,多个用逗号隔开 */		
	private String tjuserid; 	
	/**推荐人名称,多个用逗号隔开 */		
	private String tjusername; 	
	
	/**是否审批 1是，0否*/		
	private Integer isSp = 0; 
	/**下一步诊疗方案*/		
	private String houxu; 
	
	/***/
	private String share;

	/***/
	private String summary;

	/***/
	private String isDelete;
	
	/**患者id*/
	private Long patientId;
	
	/**身份证*/
	private String idcard;

	/**第二诊疗，0否，1是*/
	private Integer dezl;
	
	/**费用*/
	private Double feiyong;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDiagnoseDate() {
		return diagnoseDate;
	}

	public void setDiagnoseDate(Date diagnoseDate) {
		this.diagnoseDate = diagnoseDate;
	}
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getSurveyReport() {
		return surveyReport;
	}

	public void setSurveyReport(String surveyReport) {
		this.surveyReport = surveyReport;
	}
	public String getInspectionReport() {
		return inspectionReport;
	}

	public void setInspectionReport(String inspectionReport) {
		this.inspectionReport = inspectionReport;
	}
	public Date getMdtDate() {
		return mdtDate;
	}

	public void setMdtDate(Date mdtDate) {
		this.mdtDate = mdtDate;
	}
	public String getMdtLocation() {
		return mdtLocation;
	}

	public void setMdtLocation(String mdtLocation) {
		this.mdtLocation = mdtLocation;
	}
	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getOtherDiseaseName() {
		return otherDiseaseName;
	}

	public void setOtherDiseaseName(String otherDiseaseName) {
		this.otherDiseaseName = otherDiseaseName;
	}
	public String getMdtPurpose() {
		return mdtPurpose;
	}

	public void setMdtPurpose(String mdtPurpose) {
		this.mdtPurpose = mdtPurpose;
	}
	public String getOtherMdtPurpose() {
		return otherMdtPurpose;
	}

	public void setOtherMdtPurpose(String otherMdtPurpose) {
		this.otherMdtPurpose = otherMdtPurpose;
	}
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(String isCharge) {
		this.isCharge = isCharge;
	}
	public String getApplyPerson() {
		return applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}
	public Date getApplyCreatetime() {
		return applyCreatetime;
	}

	public void setApplyCreatetime(Date applyCreatetime) {
		this.applyCreatetime = applyCreatetime;
	}
	public String getApplyPhone() {
		return applyPhone;
	}

	public void setApplyPhone(String applyPhone) {
		this.applyPhone = applyPhone;
	}
	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}


	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}


	public Integer getApplyPersonId() {
		return applyPersonId;
	}

	public void setApplyPersonId(Integer applyPersonId) {
		this.applyPersonId = applyPersonId;
	}

	public Integer getIsSp() {
		return isSp;
	}

	public void setIsSp(Integer isSp) {
		this.isSp = isSp;
	}

	public List<LCHistory> getlCHistoryList() {
		return lCHistoryList;
	}

	public void setlCHistoryList(List<LCHistory> lCHistoryList) {
		this.lCHistoryList = lCHistoryList;
	}

	public LCHistory getYijian() {
		return yijian;
	}

	public void setYijian(LCHistory yijian) {
		this.yijian = yijian;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public List<MdtApplyDoctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<MdtApplyDoctor> doctors) {
		this.doctors = doctors;
	}

	public Integer getIsJiaofei() {
		return isJiaofei;
	}

	public void setIsJiaofei(Integer isJiaofei) {
		this.isJiaofei = isJiaofei;
	}

	public Integer getIsZhiqing() {
		return isZhiqing;
	}

	public void setIsZhiqing(Integer isZhiqing) {
		this.isZhiqing = isZhiqing;
	}

	public Integer getIsDuanxin() {
		return isDuanxin;
	}

	public void setIsDuanxin(Integer isDuanxin) {
		this.isDuanxin = isDuanxin;
	}

	public Integer getIsKsdafen() {
		return isKsdafen;
	}

	public void setIsKsdafen(Integer isKsdafen) {
		this.isKsdafen = isKsdafen;
	}

	public Integer getIsZjdafen() {
		return isZjdafen;
	}

	public void setIsZjdafen(Integer isZjdafen) {
		this.isZjdafen = isZjdafen;
	}

	public Integer getIsXiaojie() {
		return isXiaojie;
	}

	public void setIsXiaojie(Integer isXiaojie) {
		this.isXiaojie = isXiaojie;
	}

	public Integer getIsZuofei() {
		return isZuofei;
	}

	public void setIsZuofei(Integer isZuofei) {
		this.isZuofei = isZuofei;
	}

	public SysOrg getDept() {
		return dept;
	}

	public void setDept(SysOrg dept) {
		this.dept = dept;
	}

	public SysUser getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(SysUser applyUser) {
		this.applyUser = applyUser;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getTjuserid() {
		return tjuserid;
	}

	public void setTjuserid(String tjuserid) {
		this.tjuserid = tjuserid;
	}

	public String getTjusername() {
		return tjusername;
	}

	public void setTjusername(String tjusername) {
		this.tjusername = tjusername;
	}

	public Double getFeiyong() {
		return feiyong;
	}

	public void setFeiyong(Double feiyong) {
		this.feiyong = feiyong;
	}

	public String getHouxu() {
		return houxu;
	}

	public void setHouxu(String houxu) {
		this.houxu = houxu;
	}

	public SysPatient getPatientUser() {
		return patientUser;
	}

	public void setPatientUser(SysPatient patientUser) {
		this.patientUser = patientUser;
	}

	public Integer getDezl() {
		return dezl;
	}

	public void setDezl(Integer dezl) {
		this.dezl = dezl;
	}
	
}
