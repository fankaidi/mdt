package com.kensure.mdt.entity.bo;

public class MdtGradeItemReq {

	private Long gradeId;

	private String description;

	private Long grade;

	private Long minValue;

	private Long maxValue;

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public Long getMinValue() {
		return minValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	public Long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}
}
