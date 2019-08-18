package com.kensure.mdt.entity.query;

import java.io.Serializable;

public class SysUserQuery implements Serializable {

	private static final long serialVersionUID = 3545276994084105527L;

	/** 工号 */
	private String numberLike;
	/** 姓名 */
	private String nameLike;

	public String getNumberLike() {
		return numberLike;
	}

	public void setNumberLike(String numberLike) {
		this.numberLike = numberLike;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

}
