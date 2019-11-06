package com.kensure.mdt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kensure.io.FileUtils;

/**
 * 获取字典
 * 
 * @author fankd
 * @since SHK FileUtils 1.0
 */
public final class DeptDictUtils {
	
	private static Map<String,String> map = new HashMap<>();
	
	public static Map<String,String> read(String filePath) {
		List<String> ids = FileUtils.readList(filePath);
		for (String id : ids) {
			id = id.trim();
			if (id.indexOf(" ") > 0) {
				String[] temp = id.split(" ");
				map.put(temp[1].trim(), temp[0].trim());
			}
		}
		return map;
	}

}
