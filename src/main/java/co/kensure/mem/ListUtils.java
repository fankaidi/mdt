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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 集合工具类
 * 
 * @author fankd
 * @since SHK Framework 1.0
 */
public final class ListUtils {

	/**
	 * 将String的数组，变成Long型的数组
	 * 
	 * @param coll
	 *            集合类型, 包括List, Set等
	 * @return List<Long>
	 */
	public static List<Long> toLongCol(Collection<String> coll) {
		List<Long> longList = new ArrayList<Long>();
		if (coll != null) {
			for (String temp : coll) {
				longList.add(NumberUtils.parseLong(temp, null));
			}
		}
		return longList;
	}

	/**
	 * 将用逗号隔开的字符串，变成Long型的数组
	 * 
	 * @param ids
	 *            1,3,5,8
	 * 
	 * @return List<Long>
	 */
	public static List<Long> toLongCol(String ids) {
		List<Long> longList = new ArrayList<Long>();
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = ids.split(",");
			longList = toLongCol(Arrays.asList(idArr));
		}
		return longList;
	}
	
	/**
	 * 将用逗号隔开的字符串，变成String型的数组
	 * 
	 * @param ids
	 *            1,3,5,8
	 * 
	 * @return List<Long>
	 */
	public static List<String> toStringCol(String ids) {
		List<String> longList = new ArrayList<String>();
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = ids.split(",");
			longList = Arrays.asList(idArr);
		}
		return longList;
	}

	/**
	 * 将List中的元素以元素中某个字段为Key转为Map
	 * @author qiuxs
	 *
	 * @param list
	 * @param fieldName
	 * @return
	 *
	 * 创建时间：2018年7月26日 下午10:16:01
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> listToMap(List<V> list, String fieldName) {
		Map<K, V> map = new HashMap<>();
		if (list == null || list.size() == 0) {
			return map;
		}
		// 处理List中元素为Map的情况
		if (list.get(0) instanceof Map) {
			for (V v : list) {
				map.put(((Map<?, K>) v).get(fieldName), v);
			}
		} else {
			Field field = ReflectUtils.getAccessibleField(list.get(0).getClass(), fieldName);
			for (V v : list) {
				try {
					map.put((K) field.get(v), v);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
}
