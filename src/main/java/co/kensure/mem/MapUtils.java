package co.kensure.mem;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * map工具类
 * 
 * @author fankd created on 2017-5-8
 */
public class MapUtils {

	/**
	 * 直接生成map，并传入key-value对
	 * 
	 * @param keysAndValues
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Map<String, Object> genMap(Object... keysAndValues) {
		Map<String, Object> ret = new HashMap<String, Object>();

		int len = keysAndValues.length;
		if (len == 0)
			return ret;

		checkLenth(keysAndValues);
		for (int i = 0; i < len; i += 2) {
			String key = String.valueOf(keysAndValues[i]);
			Object val = keysAndValues[i + 1];
			if (val != null)
				ret.put(key, val);
		}

		return ret;
	}

	/**
	 * 直接生成map，并传入key-value对
	 * 
	 * @param keysAndValues
	 * @return
	 * @author fankd created on 2017-5-8
	 */
	public static Map<String, String> genStringMap(String... keysAndValues) {
		Map<String, String> ret = new HashMap<String, String>();

		int len = keysAndValues.length;
		if (len == 0)
			return ret;

		checkLenth(keysAndValues);
		for (int i = 0; i < len; i += 2) {
			String key = String.valueOf(keysAndValues[i]);
			String val = keysAndValues[i + 1];
			if (val != null)
				ret.put(key, val);
		}

		return ret;
	}

	private static void checkLenth(String... keysAndValues) {
		if (keysAndValues.length % 2 != 0)
			throw new IllegalArgumentException("传入的参数必须成对!");
	}

	private static void checkLenth(Object... keysAndValues) {
		if (keysAndValues.length % 2 != 0)
			throw new IllegalArgumentException("传入的参数必须成对!");
	}

	/**
	 * 直接生成map，并传入key-value对，若为空也加入
	 * 
	 * @param keysAndValues
	 * @return
	 * @author fankd created on 2014-5-8
	 */
	public static Map<String, Object> genMapWithNull(Object... keysAndValues) {
		Map<String, Object> ret = new HashMap<String, Object>();

		int len = keysAndValues.length;
		if (len == 0)
			return ret;

		if (len % 2 != 0)
			throw new IllegalArgumentException("传入的参数必须成对!");
		for (int i = 0; i < len; i += 2) {
			String key = String.valueOf(keysAndValues[i]);
			Object val = keysAndValues[i + 1];
			ret.put(key, val);
		}

		return ret;
	}

	/**
	 * Map转换为String
	 * 
	 * @param map
	 * @return
	 * @Author:fankd created on 2017-5-15
	 */
	public static String mapToString(Map<String, ?> map) {
		if (map == null || map.size() == 0)
			return null;

		StringBuffer buffer = new StringBuffer();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = map.get(key);
			String strValue = "";
			if (value != null)
				strValue = value.toString();
			buffer.append(",");
			buffer.append(key).append(":").append(strValue);
		}
		return buffer.substring(1);
	}

	/**
	 * 将Map转为 key=val&key=val 形式
	 * 
	 * @param map
	 * @return
	 */
	public static String map2Url(Map<String, String> map) {
		if (map == null) {
			return null;
		}
		StringBuilder url = new StringBuilder();
		for (Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = iter.next();
			url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		url.setLength(url.length() - 1);
		return url.toString();
	}

	/**
	 * 
	 * Bean转换为Map：将bean中所有可访问字段（通过getter/setter方法，含父类的）转换成Map<字段名，字段值>
	 * null字段，不进行转换
	 * 
	 * @author fankd
	 * @param obj
	 * @param ignoreNull
	 *            true 就忽略null
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object bean, boolean ignoreNull) {
		if (bean == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(bean.getClass());

		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			// 过滤class属性
			if (key.equals("class")) {
				continue;
			}
			Object value = null;
			try {
				value = PropertyUtils.getProperty(bean, key);
				if (ignoreNull)
					if (value == null) {
						continue;
					} else if (value instanceof String && StringUtils.isBlank(value.toString())) {
						continue;
					}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			map.put(property.getName(), value);
		}

		return map;
	}

	/**
	 * 判断map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null) {
			return true;
		}
		return map.isEmpty();
	}

	/**
	 * 判断map是不否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 设置字符串，如果为空，则不设置
	 * 
	 * @return
	 */
	public static void putString(Map<String, Object> map, String name, String value) {
		if (StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)) {
			map.put(name, value);
		}
	}

	/**
	 * 设置字符串，传入需要设置的参数对象和获取参数的对象，还有参数名，就可以继续批量设置
	 * 
	 * @param map
	 *            需要进行设置的参数对象
	 * @param json
	 *            获取参数值的对象
	 * @param valueName
	 *            参数名
	 * @return
	 */
	public static void putStrings(Map<String, Object> map, JSONObject json, String... valueName) {
		int len = valueName.length;
		if (len == 0)
			return;
		for (int i = 0; i < len; i++) {
			String key = String.valueOf(valueName[i]);
			String val = json.getString(key);
			putString(map, key, val);
		}
	}

	/**
	 * 设置Integer数字，如果为空，则不设置
	 * 
	 * @return
	 */
	public static void putInt(Map<String, Object> map, String name, String value) {
		if (StringUtils.isNotBlank(value)) {
			map.put(name, NumberUtils.parseInteger(value, 0));
		}
	}

	/**
	 * 设置数字，传入需要设置的参数对象和获取参数的对象，还有参数名，就可以继续批量设置
	 * 
	 * @param map
	 *            需要进行设置的参数对象
	 * @param json
	 *            获取参数值的对象
	 * @param valueName
	 *            参数名
	 * @return
	 */
	public static void putInts(Map<String, Object> map, JSONObject json, String... valueName) {
		int len = valueName.length;
		if (len == 0)
			return;
		for (int i = 0; i < len; i++) {
			String key = String.valueOf(valueName[i]);
			Integer val = json.getInteger(key);
			if (val != null) {
				map.put(key, val);
			}
		}
	}

	/**
	 * 设置开始时间，如果为空，则不设置,如果value只是日期，需要加入 时分秒
	 * 
	 * @return
	 */
	public static void putStartTime(Map<String, Object> map, String name, String value) {
		if (StringUtils.isNotBlank(value)) {
			if (value.length() == 10) {
				value += " 00:00:00";
			}
			map.put(name, DateUtils.parse(value, DateUtils.DATE_FORMAT_PATTERN));
		}
	}

	/**
	 * 设置结束时间，如果为空，则不设置,如果value只是日期，需要加入 时分秒
	 * 
	 * @return
	 */
	public static void putEndTime(Map<String, Object> map, String name, String value) {
		if (StringUtils.isNotBlank(value)) {
			if (value.length() == 10) {
				value += " 23:59:59";
			}
			map.put(name, DateUtils.parse(value, DateUtils.DATE_FORMAT_PATTERN));
		}
	}

	/**
	 * 设置翻页信息，固定参数limit和current
	 * 
	 * @return
	 */
	public static void putPageInfo(Map<String, Object> parameters, PageInfo page) {
		parameters.put("limit", page.getPageSize());
		parameters.put("offset", page.getPageOffset());
	}

}
