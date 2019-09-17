package com.kensure.mdt.model;

import java.util.Date;

import co.kensure.frame.BaseInfo;

/**
 * mdt知情同意书对象类
 * @author fankd created on 2019-7-24
 * @since
 */
public class MdtApplyKnow extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**知情同意书*/		
	private Long id; 

	/**MDT申请id*/		
	private Long applyId; 

	/**患者姓名*/		
	private String name; 

	/**性 别*/		
	private String gender; 

	/**年 龄*/		
	private String age; 

	/**联系方式*/		
	private String phone; 

	/**患者身份证号*/		
	private String idcard; 

	/**门诊号*/		
	private String medicalNo; 

	/**住院号*/		
	private String inHospitalNo; 

	/**家庭住址*/		
	private String addr; 

	/**患者联系人*/		
	private String linman; 

	/**联系人电话*/		
	private String linphone; 

	/**病因*/		
	private String reason; 

	/**患者签名*/		
	private String nameqm; 

	/**患者签名日期*/		
	private Date qmdate1; 

	/**委托人签名*/		
	private String wtqm; 

	/**委托人签名日期*/		
	private Date qmdate2; 

	/**委托人与患者的关系*/		
	private String wtgx; 

	/**委托人联系电话*/		
	private String wtphone; 

	/**委托人身份证号*/		
	private String wtidcard; 

	/**委托人住址*/		
	private String wtaddr; 

	/**医生签名*/		
	private String doctorqm; 

	/**医生签名日期*/		
	private Date qmdate3; 
	
	/**患者签名图片*/		
	private String nameqmurl; 	
	
	/**患者签名时间，精确到秒*/		
	private Date qmtime1; 
	/**
	 * 费用
	 */
	private Double feiyong;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
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
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getLinman() {
		return linman;
	}

	public void setLinman(String linman) {
		this.linman = linman;
	}
	public String getLinphone() {
		return linphone;
	}

	public void setLinphone(String linphone) {
		this.linphone = linphone;
	}
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNameqm() {
		return nameqm;
	}

	public void setNameqm(String nameqm) {
		this.nameqm = nameqm;
	}
	public Date getQmdate1() {
		return qmdate1;
	}

	public void setQmdate1(Date qmdate1) {
		this.qmdate1 = qmdate1;
	}
	public String getWtqm() {
		return wtqm;
	}

	public void setWtqm(String wtqm) {
		this.wtqm = wtqm;
	}
	public Date getQmdate2() {
		return qmdate2;
	}

	public void setQmdate2(Date qmdate2) {
		this.qmdate2 = qmdate2;
	}
	public String getWtgx() {
		return wtgx;
	}

	public void setWtgx(String wtgx) {
		this.wtgx = wtgx;
	}
	public String getWtphone() {
		return wtphone;
	}

	public void setWtphone(String wtphone) {
		this.wtphone = wtphone;
	}
	public String getWtidcard() {
		return wtidcard;
	}

	public void setWtidcard(String wtidcard) {
		this.wtidcard = wtidcard;
	}
	public String getWtaddr() {
		return wtaddr;
	}

	public void setWtaddr(String wtaddr) {
		this.wtaddr = wtaddr;
	}
	public String getDoctorqm() {
		return doctorqm;
	}

	public void setDoctorqm(String doctorqm) {
		this.doctorqm = doctorqm;
	}
	public Date getQmdate3() {
		return qmdate3;
	}

	public void setQmdate3(Date qmdate3) {
		this.qmdate3 = qmdate3;
	}

	public Double getFeiyong() {
		return feiyong;
	}

	public void setFeiyong(Double feiyong) {
		this.feiyong = feiyong;
	}

	public String getNameqmurl() {
		return nameqmurl;
	}

	public void setNameqmurl(String nameqmurl) {
		this.nameqmurl = nameqmurl;
	}

	public Date getQmtime1() {
		return qmtime1;
	}

	public void setQmtime1(Date qmtime1) {
		this.qmtime1 = qmtime1;
	}
	
}
