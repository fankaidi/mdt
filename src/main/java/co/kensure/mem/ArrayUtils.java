package co.kensure.mem;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组工具类
 * 
 * @author fankd
 * @since SHK Framework 1.0
 */
public final class ArrayUtils {

	/**
	 * 将数组转化成list
	 * 
	 * @param coll
	 * @return
	 */
	public static List<String> toList(String[] coll) {
		List<String> longList = new ArrayList<String>();
		if (coll != null) {
			for (String temp : coll) {
				longList.add(temp);
			}
		}
		return longList;
	}

}
