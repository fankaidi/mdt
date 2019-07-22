package co.kensure.mem;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * number工具类
 * 
 * @author fankd created on 2017-5-8
 */
public class NumberUtils {

	/**
	 * 进行或判断，至少有一个和value相等，就是true，否者false;
	 * @author fankd created on 2017-5-8
	 */
	public static boolean equealEvenOne(int value, int... values) {
		int len = values.length;
		for (int i = 0; i < len; i++) {
			int val = values[i];
			if (value == val) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 如果为空或者是0，返回true，不然返回false
	 * 
	 * @param obj
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static boolean isZero(Number obj) {
		if (obj == null) {
			return true;
		}
		boolean ret = obj.intValue() == 0;
		return ret;
	}

	/**
	 * Long型做加法，如果obj为空，认为是0
	 * 
	 * @param obj
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Long longAdd(Long obj, int i) {
		Long ret = Long.valueOf(0);
		if (obj != null) {
			ret = obj + i;
		}
		return ret;
	}

	/**
	 * Integer型做加法，如果obj为空，认为是0
	 * 
	 * @param obj
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Integer integerAdd(Integer obj, int i) {
		Integer ret = Integer.valueOf(0);
		if (obj != null) {
			ret = obj + i;
		}
		return ret;
	}

	/**
	 * String转成Integer型，如果obj为空，返回默认值
	 * 
	 * @param obj
	 * @param defaultVal
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Integer parseInteger(String obj, Integer defaultVal) {
		if (StringUtils.isBlank(obj)) {
			return defaultVal;
		}
		BigDecimal big = new BigDecimal(obj);
		Integer ret = big.intValue();
		return ret;
	}

	/**
	 * String转成Double型，如果obj为空，返回默认值
	 * 
	 * @param obj
	 * @param defaultVal
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Double parseDouble(String obj, Double defaultVal) {
		if (StringUtils.isBlank(obj)) {
			return defaultVal;
		}
		BigDecimal big = new BigDecimal(obj);
		Double ret = big.doubleValue();
		return ret;
	}

	/**
	 * Integer型如果为空，返回默认值
	 * 
	 * @param obj
	 * @param defaultVal
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Integer defaultInteger(Integer obj, Integer defaultVal) {
		if (obj == null) {
			return defaultVal;
		}
		return obj;
	}

	/**
	 * String转成Long型，如果obj为空，返回默认值
	 * 
	 * @param obj
	 * @param defaultVal
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Long parseLong(String obj, Long defaultVal) {
		if (StringUtils.isBlank(obj)) {
			return defaultVal;
		}
		BigDecimal big = new BigDecimal(obj);
		Long ret = big.longValue();
		return ret;
	}
	
	/**
	 * String转成Long型，如果obj为空，返回默认值
	 * 
	 * @param obj
	 * @param defaultVal
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static boolean isNumber(String obj) {
		return org.apache.commons.lang3.math.NumberUtils.isNumber(obj);
	}

}
