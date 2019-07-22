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
package co.kensure.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import co.kensure.io.FileUtils;

/**
 * 下载工具类
 * 
 * @author fankd
 * @since SHK DownLoadUtil 1.0
 */
public final class DownLoadUtils {

	/**
	 * 将文件内存下载成文件
	 * 
	 * @param fileContent
	 *            文件内容
	 * @param filename
	 *            下载后名称
	 * @param response
	 */
	public static void downLoadFile(String fileContent, String filename, HttpServletResponse response) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setContentType("text/plain");
			response.setHeader("Content-disposition", "attachment; filename=" + filename);
			bis = new BufferedInputStream(new ByteArrayInputStream(fileContent.getBytes("utf-8")));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buf = new byte[1024];
			int bytesRead = 0;
			while (-1 != (bytesRead = (bis.read(buf)))) {
				bos.write(buf, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			FileUtils.close(bis);
			FileUtils.close(bos);
		}
	}

	/**
	 * 将文件内存下载成文件
	 * 
	 * @param fileContent
	 *            文件内容
	 * @param filename
	 *            下载后名称
	 * @param response
	 */
	public static void downLoadZip(String filePath, String filename, HttpServletResponse response) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		InputStream is = null;
		try {
			if (StringUtils.isBlank(filename)) {
				filename = filePath.substring(filePath.lastIndexOf(File.separator)+1);
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-disposition", "attachment; filename=" + filename);
			FileInputStream fis = new FileInputStream(filePath);
			is = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buf = new byte[10240];
			int bytesRead = 0;
			while (-1 != (bytesRead = (is.read(buf)))) {
				bos.write(buf, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			FileUtils.close(is);
			FileUtils.close(bis);
			FileUtils.close(bos);
		}

	}
	
	

}
