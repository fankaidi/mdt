package com.kensure.mdt.rep;

import java.util.Date;

import co.kensure.frame.BaseInfo;

/**
 * 病例
 */
public class WsBingLi extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;


	/** 门诊号码/住院号码 */
	private String HM;

	/** 过去史 */
	private String GQS;
	/** 家族史 */
	private String JZS;
	/** 个人史 */
	private String GRS;
	/** 体检 */
	private String TJ;
	/** 处理 */
	private String CL;
	/** 初步诊断 */
	private String CBZD;
	/**时间*/		
	private Date CREATED_DATE; 


	public String getHM() {
		return HM;
	}


	public void setHM(String hM) {
		HM = hM;
	}


	public String getGQS() {
		return GQS;
	}


	public void setGQS(String gQS) {
		GQS = gQS;
	}


	public String getJZS() {
		return JZS;
	}


	public void setJZS(String jZS) {
		JZS = jZS;
	}


	public String getGRS() {
		return GRS;
	}


	public void setGRS(String gRS) {
		GRS = gRS;
	}


	public String getTJ() {
		return TJ;
	}


	public void setTJ(String tJ) {
		TJ = tJ;
	}


	public String getCL() {
		return CL;
	}


	public void setCL(String cL) {
		CL = cL;
	}


	public String getCBZD() {
		return CBZD;
	}


	public void setCBZD(String cBZD) {
		CBZD = cBZD;
	}


	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}


	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
}
