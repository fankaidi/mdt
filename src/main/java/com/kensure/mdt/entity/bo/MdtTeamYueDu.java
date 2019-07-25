package com.kensure.mdt.entity.bo;

import co.kensure.mem.ArithmeticUtils;

public class MdtTeamYueDu {

	/**
	 * 年
	 */
	private Integer year;
	/**
	 * 月
	 */
	private Integer month;
	/**
	 * 指标
	 */
	private Long total;
	/**
	 * 达到
	 */
	private Long num;
	
	
	public MdtTeamYueDu(){}
	
	public MdtTeamYueDu(Integer year,Integer month,Long total,Long num){
		setYear(year);
		setMonth(month);
		setTotal(total);
		setNum(num);	
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	/**
	 * 完成指标
	 * 
	 * @return
	 */
	public Double getWanChen() {
		if (total == 0) {
			return 0D;
		}
		return ArithmeticUtils.div(num, total, 2);
	}

}
