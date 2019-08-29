package com.kensure.mdt.rep;

import java.util.Date;

import co.kensure.frame.BaseInfo;
import co.kensure.mem.DateUtils;

import com.kensure.mdt.entity.SysPatient;

/**
 * 门诊患者信息表对象类,通过http接口获取
 */
public class WsMenZhen extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 预约序号 */
	private String YYXH;

	/** 病人姓名 */
	private String BRXM;

	/** 病人性别，1是男，2是女 */
	private String BRXB;

	/** 身份证号 */
	private String SFZH;

	/** 联系电话 */
	private String LXDH;

	/** 预约日期 */
	private String YYRQ;
	/** 科室名称 */
	private String KSMC;

	public String getYYXH() {
		return YYXH;
	}

	public void setYYXH(String yYXH) {
		YYXH = yYXH;
	}

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

	public String getSFZH() {
		return SFZH;
	}

	public void setSFZH(String sFZH) {
		SFZH = sFZH;
	}

	public String getLXDH() {
		return LXDH;
	}

	public void setLXDH(String lXDH) {
		LXDH = lXDH;
	}

	public String getYYRQ() {
		return YYRQ;
	}

	public void setYYRQ(String yYRQ) {
		YYRQ = yYRQ;
	}

	public String getKSMC() {
		return KSMC;
	}

	public void setKSMC(String kSMC) {
		KSMC = kSMC;
	}

	public SysPatient toPatient() {
		SysPatient patient = new SysPatient();
		patient.setTreatmentNo(YYXH);
		patient.setName(BRXM);
		String gender = "1".equals(BRXB) ? "男" : "女";
		patient.setGender(gender);
		patient.setIdcard(SFZH);
		patient.setPhone(LXDH);
		patient.setBirthday(DateUtils.parse(YYRQ, DateUtils.DATE_FORMAT_PATTERN));
		Date now = new Date();
		long age = (now.getTime()-patient.getBirthday().getTime())/1000/60/60/24/365;
		patient.setAge(age);
		patient.setPatientType("2");
		return patient;
	}
}
