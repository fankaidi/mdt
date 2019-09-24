package com.kensure.mdt.rep;

import org.apache.commons.lang.StringUtils;

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

	/** 住院号码 */
	private String ZYH;
	/** 入院日期 */
	private String RYRQ;
	/** 身份证号 */
	private String SFZH;
	/** 上级医师 */
	private String ZYYS;
	/** 主任医师 */
	private String ZRYS;
	/** 诊断 */
	private String ZD;
	/** 出院日期 */
	private String CYRQ;

	/** 科室名称 */
	private String KSMC;
	/**地址*/		
	private String XZZ; 

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

	public String getRYRQ() {
		return RYRQ;
	}

	public void setRYRQ(String rYRQ) {
		RYRQ = rYRQ;
	}

	public String getSFZH() {
		return SFZH;
	}

	public void setSFZH(String sFZH) {
		SFZH = sFZH;
	}

	public String getZYYS() {
		return ZYYS;
	}

	public void setZYYS(String zYYS) {
		ZYYS = zYYS;
	}

	public String getZRYS() {
		return ZRYS;
	}

	public void setZRYS(String zRYS) {
		ZRYS = zRYS;
	}

	public String getZD() {
		return ZD;
	}

	public void setZD(String zD) {
		ZD = zD;
	}

	public String getCYRQ() {
		return CYRQ;
	}

	public void setCYRQ(String cYRQ) {
		CYRQ = cYRQ;
	}

	public String getZYH() {
		return ZYH;
	}

	public void setZYH(String zYH) {
		ZYH = zYH;
	}

	public String getXZZ() {
		return XZZ;
	}

	public void setXZZ(String xZZ) {
		XZZ = xZZ;
	}

	public SysPatient toPatient() {
		SysPatient patient = new SysPatient();
		patient.setName(BRXM);
		String gender = "1".equals(BRXB) ? "男" : "女";
		patient.setGender(gender);
		patient.setBirthday(DateUtils.parse(CSNY, DateUtils.DATE_FORMAT_PATTERN));
		patient.setAge(BRNL.longValue());
		patient.setPatientType("1");
		patient.setInHospitalNo(HM);
		patient.setMedicalNo(HM);
		patient.setIdcard(SFZH);
		patient.setSuperiorDoctor(ZYYS);
		patient.setSeniorDoctor(ZRYS);
		patient.setDiagnosis(ZD);
		patient.setYyks(KSMC);
		patient.setTreatmentNo(ZYH);
		patient.setXzz(XZZ);
		if (StringUtils.isNotBlank(RYRQ)) {
			patient.setInHospitalDate(DateUtils.parse(RYRQ, DateUtils.DATE_FORMAT_PATTERN));
		}
		if (StringUtils.isNotBlank(CYRQ)) {
			patient.setOutHospitalDate(DateUtils.parse(RYRQ, DateUtils.DATE_FORMAT_PATTERN));
		}
		return patient;
	}
}
