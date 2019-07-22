package com.kensure.mdt.entity;

import co.kensure.frame.BaseInfo;

import com.kensure.lc.model.LCHistory;

/**
 * MDT团队建设责任目标对象类
 */
public class MdtTeamObjective extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**MDT团队建设责任目标*/		
	private Long id; 

	/**团队id*/		
	private Long teamId; 

	/**年度*/		
	private String year; 

	/**年度目标完成例次*/		
	private Long yearSum; 

	/**MDT病种相关研究核心期刊论文发表量*/		
	private Long papersNum; 

	/**其他*/		
	private String other;

	/**flag*/
	private String flag;

	/**年度总结*/
	private String summary;

	/**月度目标指标1*/		
	private Long month1; 

	/**月度目标指标2*/		
	private Long month2; 

	/**月度目标指标3*/		
	private Long month3; 

	/**月度目标指标4*/		
	private Long month4; 

	/**月度目标指标5*/		
	private Long month5; 

	/**月度目标指标6*/		
	private Long month6; 

	/**月度目标指标7*/		
	private Long month7; 

	/**月度目标指标8*/		
	private Long month8; 

	/**月度目标指标9*/		
	private Long month9; 

	/**月度目标指标10*/		
	private Long month10; 

	/**月度目标指标11*/		
	private Long month11; 

	/**月度目标指标12*/		
	private Long month12;

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
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	public Long getYearSum() {
		return yearSum;
	}

	public void setYearSum(Long yearSum) {
		this.yearSum = yearSum;
	}
	public Long getPapersNum() {
		return papersNum;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setPapersNum(Long papersNum) {
		this.papersNum = papersNum;
	}
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	public Long getMonth1() {
		return month1;
	}

	public void setMonth1(Long month1) {
		this.month1 = month1;
	}
	public Long getMonth2() {
		return month2;
	}

	public void setMonth2(Long month2) {
		this.month2 = month2;
	}
	public Long getMonth3() {
		return month3;
	}

	public void setMonth3(Long month3) {
		this.month3 = month3;
	}
	public Long getMonth4() {
		return month4;
	}

	public void setMonth4(Long month4) {
		this.month4 = month4;
	}
	public Long getMonth5() {
		return month5;
	}

	public void setMonth5(Long month5) {
		this.month5 = month5;
	}
	public Long getMonth6() {
		return month6;
	}

	public void setMonth6(Long month6) {
		this.month6 = month6;
	}
	public Long getMonth7() {
		return month7;
	}

	public void setMonth7(Long month7) {
		this.month7 = month7;
	}
	public Long getMonth8() {
		return month8;
	}

	public void setMonth8(Long month8) {
		this.month8 = month8;
	}
	public Long getMonth9() {
		return month9;
	}

	public void setMonth9(Long month9) {
		this.month9 = month9;
	}
	public Long getMonth10() {
		return month10;
	}

	public void setMonth10(Long month10) {
		this.month10 = month10;
	}
	public Long getMonth11() {
		return month11;
	}

	public void setMonth11(Long month11) {
		this.month11 = month11;
	}
	public Long getMonth12() {
		return month12;
	}

	public void setMonth12(Long month12) {
		this.month12 = month12;
	}

	public LCHistory getYijian() {
		return yijian;
	}

	public void setYijian(LCHistory yijian) {
		this.yijian = yijian;
	}
	
}
