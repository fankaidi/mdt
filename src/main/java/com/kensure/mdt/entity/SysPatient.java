package com.kensure.mdt.entity;

import java.util.Date;

import co.kensure.frame.BaseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 患者信息表对象类
 */
public class SysPatient extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**患者信息表*/		
	private Long id; 

	/**患者类型(1:住院2:门诊 )*/		
	private String patientType; 

	/**姓名患者名称*/		
	private String name; 

	/**性别*/		
	private String gender; 

	/**出生日期*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date birthday; 

	/**年龄*/		
	private Long age; 

	/**身份证号*/		
	private String idcard; 
	/**电话号码*/		
	private String phone; 

	/**病历号(门诊)*/		
	private String medicalNo; 

	/**就诊号(门诊),预约号*/		
	private String treatmentNo; 
	/**首诊日期(门诊)*/		
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date medicalDate; 
	
	/**预约日期*/		
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date yyDate; 
	/**预约科室*/		
	private String yyks; 


	/**病历名称(门诊)*/		
	private String medicalName; 

	/**病历状态(门诊)*/		
	private String medicalStatus; 

	/**主治医生(门诊)*/		
	private String majorDoctor; 

	/**现在病史(门诊)*/		
	private String medicalHistory; 

	/**体检(门诊)*/		
	private String medicalExam; 

	/**专科体检(门诊)*/		
	private String specializedMedical; 

	/**处理(门诊)*/		
	private String dispose; 

	/**初步诊断(门诊)*/		
	private String primaryDiagnosis; 

	/**住院号(住院)*/		
	private String inHospitalNo; 

	/**住院次(住院)*/		
	private String inHospitalNumber; 

	/**费别(住院)*/		
	private String feeType; 

	/**入院日期(住院)*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date inHospitalDate; 

	/**经治医生(住院)*/		
	private String cureDoctor; 

	/**上级医师(住院)*/		
	private String superiorDoctor; 

	/**主任医师(住院)*/		
	private String seniorDoctor; 

	/**诊断(住院)*/		
	private String diagnosis; 

	/**住院日(住院)*/		
	private Integer inHospitalDays;

	/**出院日期(住院)*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date outHospitalDate; 

	/**MDT前治疗方案*/		
	private String mdtBeforeProject; 

	/**MDT后治疗方案*/		
	private String mdtAfterProject; 

	/**修改医生*/		
	private String updateDoctor; 
	
	/**默认是0,1是被删除*/		
	private Integer isDel; 
	
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
	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getMedicalNo() {
		return medicalNo;
	}

	public void setMedicalNo(String medicalNo) {
		this.medicalNo = medicalNo;
	}
	public String getTreatmentNo() {
		return treatmentNo;
	}

	public void setTreatmentNo(String treatmentNo) {
		this.treatmentNo = treatmentNo;
	}
	public String getMedicalName() {
		return medicalName;
	}

	public void setMedicalName(String medicalName) {
		this.medicalName = medicalName;
	}
	public String getMedicalStatus() {
		return medicalStatus;
	}

	public void setMedicalStatus(String medicalStatus) {
		this.medicalStatus = medicalStatus;
	}
	public String getMajorDoctor() {
		return majorDoctor;
	}

	public void setMajorDoctor(String majorDoctor) {
		this.majorDoctor = majorDoctor;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public String getMedicalExam() {
		return medicalExam;
	}

	public void setMedicalExam(String medicalExam) {
		this.medicalExam = medicalExam;
	}
	public String getSpecializedMedical() {
		return specializedMedical;
	}

	public void setSpecializedMedical(String specializedMedical) {
		this.specializedMedical = specializedMedical;
	}
	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}
	public String getPrimaryDiagnosis() {
		return primaryDiagnosis;
	}

	public void setPrimaryDiagnosis(String primaryDiagnosis) {
		this.primaryDiagnosis = primaryDiagnosis;
	}
	public String getInHospitalNo() {
		return inHospitalNo;
	}

	public void setInHospitalNo(String inHospitalNo) {
		this.inHospitalNo = inHospitalNo;
	}
	public String getInHospitalNumber() {
		return inHospitalNumber;
	}

	public void setInHospitalNumber(String inHospitalNumber) {
		this.inHospitalNumber = inHospitalNumber;
	}
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Date getInHospitalDate() {
		return inHospitalDate;
	}

	public void setInHospitalDate(Date inHospitalDate) {
		this.inHospitalDate = inHospitalDate;
	}
	public String getCureDoctor() {
		return cureDoctor;
	}

	public void setCureDoctor(String cureDoctor) {
		this.cureDoctor = cureDoctor;
	}
	public String getSuperiorDoctor() {
		return superiorDoctor;
	}

	public void setSuperiorDoctor(String superiorDoctor) {
		this.superiorDoctor = superiorDoctor;
	}
	public String getSeniorDoctor() {
		return seniorDoctor;
	}

	public void setSeniorDoctor(String seniorDoctor) {
		this.seniorDoctor = seniorDoctor;
	}
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public Integer getInHospitalDays() {
		return inHospitalDays;
	}

	public void setInHospitalDays(Integer inHospitalDays) {
		this.inHospitalDays = inHospitalDays;
	}
	public Date getOutHospitalDate() {
		return outHospitalDate;
	}

	public void setOutHospitalDate(Date outHospitalDate) {
		this.outHospitalDate = outHospitalDate;
	}
	public String getMdtBeforeProject() {
		return mdtBeforeProject;
	}

	public void setMdtBeforeProject(String mdtBeforeProject) {
		this.mdtBeforeProject = mdtBeforeProject;
	}
	public String getMdtAfterProject() {
		return mdtAfterProject;
	}

	public void setMdtAfterProject(String mdtAfterProject) {
		this.mdtAfterProject = mdtAfterProject;
	}
	
	public String getUpdateDoctor() {
		return updateDoctor;
	}

	public void setUpdateDoctor(String updateDoctor) {
		this.updateDoctor = updateDoctor;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getMedicalDate() {
		return medicalDate;
	}

	public void setMedicalDate(Date medicalDate) {
		this.medicalDate = medicalDate;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getYyDate() {
		return yyDate;
	}

	public void setYyDate(Date yyDate) {
		this.yyDate = yyDate;
	}

	public String getYyks() {
		return yyks;
	}

	public void setYyks(String yyks) {
		this.yyks = yyks;
	}
	
}
