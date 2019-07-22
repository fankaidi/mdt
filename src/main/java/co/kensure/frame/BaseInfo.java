package co.kensure.frame;

import java.util.Date;

import co.kensure.mem.DateUtils;


/**
 *
 * 基础对象，所有的info都要继承这个对象,默认实现序列化
 *
 * @author fankd
 */
public class BaseInfo implements java.io.Serializable {

	protected static final long serialVersionUID = 3545276994084105527L;
	
	/**创建时间*/		
	protected Date createdTime; 

	/**更新时间*/		
	protected Date updatedTime; 
	
	/**创建人*/		
	protected Long createdUserid; 
	
	/**创建部门*/		
	protected String createdDeptid; 
	/**创建机构*/		
	protected String createdOrgid; 
	
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	public String getCreatedTimeStr() {
		return DateUtils.format(createdTime);
	}
	public String getCreatedTimeDay() {
		return DateUtils.format(createdTime,DateUtils.DAY_FORMAT);
	}
	
	public String getUpdatedTimeStr() {
		return DateUtils.format(updatedTime);
	}
	public String getUpdatedTimeDay() {
		return DateUtils.format(updatedTime,DateUtils.DAY_FORMAT);
	}

	public Long getCreatedUserid() {
		return createdUserid;
	}

	public void setCreatedUserid(Long createdUserid) {
		this.createdUserid = createdUserid;
	}

	public String getCreatedDeptid() {
		return createdDeptid;
	}

	public void setCreatedDeptid(String createdDeptid) {
		this.createdDeptid = createdDeptid;
	}

	public String getCreatedOrgid() {
		return createdOrgid;
	}

	public void setCreatedOrgid(String createdOrgid) {
		this.createdOrgid = createdOrgid;
	}
	
	
	
}
