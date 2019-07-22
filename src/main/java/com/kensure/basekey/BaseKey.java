/*
 * 文件名称: BaseKey.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2018-3-13
 * 修改内容: 
 */
package com.kensure.basekey;

import java.util.Date;

/**
 * 主键表对象类
 * @author fankd created on 2018-3-13
 * @since
 */
public class BaseKey implements java.io.Serializable {

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**表名*/		
	private String id;

	/**当前id号*/		
	private Long nowid;

	/**创建时间*/		
	private Date createDate;

	/**修改时间*/		
	private Date updateDate;
	
	/**
	 * 区间内最小id，也就是开始id
	 */
	private Long minid;
	/**
	 * 区间内最大id，也就是结束id
	 */
	private Long maxid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Long getNowid() {
		return nowid;
	}

	public void setNowid(Long nowid) {
		this.nowid = nowid;
	}
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getMinid() {
		return minid;
	}

	public void setMinid(Long minid) {
		this.minid = minid;
	}

	public Long getMaxid() {
		return maxid;
	}

	public void setMaxid(Long maxid) {
		this.maxid = maxid;
	}
	
}
