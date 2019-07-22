package com.kensure.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import co.kensure.io.FileUtils;
import co.kensure.mem.DateUtils;

public class FileTest {

	// 打包工具

	@Test
	public void test() {

		String sourcepath1 = "D:\\work1\\shike\\src\\main\\webapp";
		String targetpath1 = "D:\\更新";
		String sourcepath2 = "D:\\work1\\shike\\target\\classes";
		String targetpath2 = "D:\\更新\\WEB-INF\\classes";

		// String sourcepath1 = "E:\\personwork\\shike\\src\\main\\webapp";
		// String targetpath1 = "C:\\Users\\fankaidi\\Desktop\\更新";
		// String sourcepath2 = "E:\\personwork\\shike\\target\\classes";
		// String targetpath2 =
		// "C:\\Users\\fankaidi\\Desktop\\更新\\WEB-INF\\classes";
		savefile(sourcepath1, targetpath1);
		savefile(sourcepath2, targetpath2);
	}

	private void savefile(String sourcepath, String targetpath) {
		Date date = DateUtils.parse("2019-06-08 18:17:00", DateUtils.DATE_FORMAT_PATTERN);
		List<String> list = new ArrayList<String>();
		getList(sourcepath, date, list);
		for (String filepath : list) {
			File sourceFile = new File(filepath);
			filepath = filepath.replace(sourcepath, targetpath);
			File targetFile = new File(filepath);
			try {
				String fold = filepath.substring(0, filepath.lastIndexOf("\\"));
				FileUtils.createDir(fold);
				FileUtils.copyFile(sourceFile, targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(filepath);
		}
	}

	private void getList(String path, Date date, List<String> list) {
		String[] childs = FileUtils.getChildList(path);
		for (String child : childs) {
			String filepath = path + "\\" + child;
			File file = new File(filepath);
			if (file.isDirectory()) {
				getList(filepath, date, list);
			} else {
				if (file.lastModified() >= date.getTime()) {
					list.add(filepath);
				}
			}
		}

	}

}
