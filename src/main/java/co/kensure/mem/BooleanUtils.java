/*
 * 文件名称: MapUtils.java
 * 版权信息: Copyright 2015-2017 jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2017-5-8
 * 修改内容: 
 */
package co.kensure.mem;

import org.apache.commons.lang3.StringUtils;

/**
 * 布尔值工具类
 * 
 * @author fankd
 * @since SHK Framework 1.0
 */
public final class BooleanUtils {

	/**
	 * 布尔值返回工具，如果为空返回默认值
	 * 
	 * @param boolean 布尔值类型
	 * @return boolean
	 */
	public static boolean getBooleanVal(Boolean val, boolean defval) {
		if (val == null) {
			return defval;
		}
		return val.booleanValue();
	}
	
	/**
	 * 布尔值返回工具，如果为空返回默认值
	 * 
	 * @param boolean 布尔值类型
	 * @return boolean
	 */
	public static boolean getBooleanVal(String val, boolean defval) {
		if (StringUtils.isBlank(val)) {
			return defval;
		}
		return val.equals("true");
	}

}
