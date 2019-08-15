/*
 * 文件名称: MdtTeamText.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-8-13
 * 修改内容: 
 */
package com.kensure.mdt.model;

import co.kensure.frame.BaseInfo;;

/**
 * mdt团队文本数据对象类
 * @author fankd created on 2019-8-13
 * @since
 */
public class MdtTeamText extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**团队id*/		
	private Long id; 

	/**MDT病种纳入标准和诊疗规范（指南）*/		
	private String standard; 


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
}
