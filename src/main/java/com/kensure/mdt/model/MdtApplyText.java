/*
 * 文件名称: MdtApplyText.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-9-23
 * 修改内容: 
 */
package com.kensure.mdt.model;

import co.kensure.frame.BaseInfo;;

/**
 * 申请大文本内容对象类
 * @author fankd created on 2019-9-23
 * @since
 */
public class MdtApplyText extends BaseInfo{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**mdt申请id,存放大文本*/		
	private Long id; 

	/**专家意见*/		
	private String zjyj; 


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getZjyj() {
		return zjyj;
	}

	public void setZjyj(String zjyj) {
		this.zjyj = zjyj;
	}
}
