package com.kensure.mdt.entity.query;

import java.util.Date;

import co.kensure.frame.BaseInfo;

/**
 * MDT申请表查询对象
 */
public class MdtApplyQuery extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/**
	 * -1是作废 申请状态 1是申请人申请，2是科室主任同意，9是审核退回，11是病人缴费 ，12是发短信通知 13是mdt会诊
	 * ，15是申请人填写反馈,19 完成
	 */
	private Integer applyStatus;

	private Integer startApplyStatus;

	private Date startApplyCreatetime;

	private Date endApplyCreatetime;

	private String nameLike;

	private String applyPersonLike;

	private Date startMdtDate;

	private Date endMdtDate;
	/**
	 * 分享
	 */
	private String share;

	/**
	 * 机构权限过滤
	 */
	private Integer orgLevel;

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Integer getStartApplyStatus() {
		return startApplyStatus;
	}

	public void setStartApplyStatus(Integer startApplyStatus) {
		this.startApplyStatus = startApplyStatus;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public Date getStartApplyCreatetime() {
		return startApplyCreatetime;
	}

	public void setStartApplyCreatetime(Date startApplyCreatetime) {
		this.startApplyCreatetime = startApplyCreatetime;
	}

	public Date getEndApplyCreatetime() {
		return endApplyCreatetime;
	}

	public void setEndApplyCreatetime(Date endApplyCreatetime) {
		this.endApplyCreatetime = endApplyCreatetime;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getApplyPersonLike() {
		return applyPersonLike;
	}

	public void setApplyPersonLike(String applyPersonLike) {
		this.applyPersonLike = applyPersonLike;
	}

	public Date getStartMdtDate() {
		return startMdtDate;
	}

	public void setStartMdtDate(Date startMdtDate) {
		this.startMdtDate = startMdtDate;
	}

	public Date getEndMdtDate() {
		return endMdtDate;
	}

	public void setEndMdtDate(Date endMdtDate) {
		this.endMdtDate = endMdtDate;
	}

}
