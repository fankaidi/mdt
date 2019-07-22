package co.kensure.frame;

import java.util.Date;
import java.util.Map;

import com.kensure.mdt.entity.AuthUser;

/**
 * Created by fankd on 2017/6/5.
 */
public abstract class JSBaseService {

	/**
	 * 表名称
	 * 
	 * @param obj
	 * @param tableName
	 */
	protected void beforeInsert(BaseInfo obj) {
		Date date = new Date();
		if (obj.getCreatedTime() == null) {
			obj.setCreatedTime(date);
		}
		if (obj.getUpdatedTime() == null) {
			obj.setUpdatedTime(date);
		}
	}

	protected void beforeUpdate(BaseInfo obj) {
		Date date = new Date();
		obj.setUpdatedTime(date);
	}

	/**
	 * 设置基础数据
	 */
	public static void initBase(BaseInfo obj, AuthUser user) {
		Date date = new Date();
		if (obj.getCreatedTime() == null) {
			obj.setCreatedTime(date);
		}
		if (obj.getUpdatedTime() == null) {
			obj.setUpdatedTime(date);
		}
		if (obj.getCreatedUserid() == null) {
			obj.setCreatedUserid(user.getId());
		}
		if (obj.getCreatedDeptid() == null) {
			obj.setCreatedDeptid(user.getDepartment());
		}
		if (obj.getCreatedOrgid() == null) {
			obj.setCreatedOrgid(user.getCreatedOrgid());
		}
	}

	/**
	 * 根据角色的级别 自动设置权限 设置数据权限
	 */
	public static void setAutoLevel(Map<String, Object> parameters, AuthUser user) {
		if (user.getRoleLevel() == 1) {
		} else if (user.getRoleLevel() == 2) {
			// 园区级别
			parameters.put("createdOrgid", user.getCreatedOrgid());
		} else if (user.getRoleLevel() == 4) {
			// 科室级别
			parameters.put("createdDeptidList", user.getDeptIdList());
		} else{
			// 个人级别
			parameters.put("createdUserid", user.getId());
		}
	}

	/**
	 * 设置机构级别的数据权限
	 */
	public static void setOrgLevel(Map<String, Object> parameters, AuthUser user) {
		parameters.put("createdOrgid", user.getCreatedOrgid());
	}
}
