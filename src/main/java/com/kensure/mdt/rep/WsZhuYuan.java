package com.kensure.mdt.rep;

import co.kensure.frame.BaseInfo;
import co.kensure.mem.DateUtils;

import com.kensure.mdt.entity.SysPatient;

/**
 * 住院患者信息表对象类,通过http接口获取
 */
public class WsZhuYuan extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 病人姓名 */
	private String BRXM;

	/** 病人性别,1：男，2：女 */
	private String BRXB;

	/** 出生年月 "1956-12-26 00:00:00" */
	private String CSNY;

	/** 病人年龄 */
	private Integer BRNL;

	/** 门诊号码/住院号码 */
	private String HM;

	/** 科室名称 */
	private String KSMC;

	public String getBRXM() {
		return BRXM;
	}

	public void setBRXM(String bRXM) {
		BRXM = bRXM;
	}

	public String getBRXB() {
		return BRXB;
	}

	public void setBRXB(String bRXB) {
		BRXB = bRXB;
	}

	public String getCSNY() {
		return CSNY;
	}

	public void setCSNY(String cSNY) {
		CSNY = cSNY;
	}

	public Integer getBRNL() {
		return BRNL;
	}

	public void setBRNL(Integer bRNL) {
		BRNL = bRNL;
	}

	public String getHM() {
		return HM;
	}

	public void setHM(String hM) {
		HM = hM;
	}

	public String getKSMC() {
		return KSMC;
	}

	public void setKSMC(String kSMC) {
		KSMC = kSMC;
	}

	
	public SysPatient toPatient(){
		SysPatient patient = new SysPatient();
		patient.setName(BRXM);
		String gender = "1".equals(BRXB)?"男":"女";
		patient.setGender(gender);
		patient.setBirthday(DateUtils.parse(CSNY, DateUtils.DATE_FORMAT_PATTERN));
		patient.setAge(BRNL.longValue());
		patient.setPatientType("1");
		patient.setInHospitalNo(HM);
		return patient;
	}
}
