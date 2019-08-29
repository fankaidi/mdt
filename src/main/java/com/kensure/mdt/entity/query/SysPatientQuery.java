package com.kensure.mdt.entity.query;

import java.io.Serializable;

public class SysPatientQuery implements Serializable {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 患者类型(1:住院2:门诊 ) */
	private String patientType;
	/** 姓名患者名称 */
	private String nameLike;
	/** 身份证号 */
	private String idcard;
	/** 身份证号 */
	private String idcardLike;
	/** 病历号(门诊) */
	private String medicalNo;
	/** 住院号(住院) */
	private String inHospitalNo;
	/** 门诊预约) */
	private String treatmentNo;

	/** 是否同步数据，1是同步,0是不同步 */
	private Integer syncData;

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
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

	public String getInHospitalNo() {
		return inHospitalNo;
	}

	public void setInHospitalNo(String inHospitalNo) {
		this.inHospitalNo = inHospitalNo;
	}

	public String getIdcardLike() {
		return idcardLike;
	}

	public void setIdcardLike(String idcardLike) {
		this.idcardLike = idcardLike;
	}

	public Integer getSyncData() {
		return syncData;
	}

	public void setSyncData(Integer syncData) {
		this.syncData = syncData;
	}

	public String getTreatmentNo() {
		return treatmentNo;
	}

	public void setTreatmentNo(String treatmentNo) {
		this.treatmentNo = treatmentNo;
	}

}
