package com.kensure.mdt.entity.query;

import java.io.Serializable;

public class MdtTeamPinGuQuery implements Serializable {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 开始年 */
	private Integer startYear;
	/** 结束年 */
	private Integer endYear;
	/** 开始月 */
	private Integer startMonth;
	/** 结束月 */
	private Integer endmonth;

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getEndmonth() {
		return endmonth;
	}

	public void setEndmonth(Integer endmonth) {
		this.endmonth = endmonth;
	}

}
