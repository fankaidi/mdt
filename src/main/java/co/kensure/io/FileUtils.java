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
package co.kensure.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.springframework.web.multipart.MultipartFile;

import co.kensure.exception.BusinessException;
import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.Const;

/**
 * 文件读写类
 * 
 * @author fankd
 * @since SHK FileUtils 1.0
 */
public final class FileUtils {

	/**
	 * 写入文件
	 * 
	 * @param content
	 *            文件内容
	 * @param filePath
	 *            文件存放文件路径,如有没有回自动创建
	 * @param fileName
	 *            文件名称
	 */
	public static void write(String content, String filePath, String fileName) {
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		try {
			// 创建文件夹
			createDir(filePath);

			// 写入文件
			String file = filePath + File.separator + fileName;
			outputStream = new FileOutputStream(file);
			outputStreamWriter = new OutputStreamWriter(outputStream, Const.ENCODING);
			outputStreamWriter.write(content);
			outputStreamWriter.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(outputStream);
			close(outputStreamWriter);
		}
	}

	/**
	 * 将流写入文件
	 * 
	 * @param inputStream
	 * @param filePath
	 *            文件存放文件路径,如有没有回自动创建
	 * @param fileName
	 */
	public static String write(InputStream inputStream, String filePath, String fileName) {
		OutputStream os = null;
		String fullname = null;
		try {
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流保存到本地文件
			createDir(filePath);
			fullname = filePath + File.separator + fileName;
			os = new FileOutputStream(fullname);
			// 开始读取
			while ((len = inputStream.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
		} catch (Exception e) {
			BusinessExceptionUtil.threwException(e);
		} finally {
			// 完毕，关闭所有链接
			close(os);
		}
		return fullname;
	}

	/**
	 * 将二进制流，变成文件
	 * 
	 * @param bytes
	 * @param filePath
	 *            文件存放文件路径,如有没有回自动创建
	 * @param fileName
	 */
	public static String write(byte[] bytes, String filePath, String fileName) {
		OutputStream os = null;
		String fullname = null;
		try {
			// 输出的文件流保存到本地文件
			createDir(filePath);
			fullname = filePath + File.separator + fileName;
			os = new FileOutputStream(fullname);
			os.write(bytes, 0, bytes.length);
		} catch (Exception e) {
			BusinessExceptionUtil.threwException(e);
		} finally {
			// 完毕，关闭所有链接
			close(os);
		}
		return fullname;
	}

	/**
	 * 根据文件路径，创建文件夹
	 * 
	 * @param path
	 */
	public static void createDir(String path) {
		if (StringUtils.isBlank(path)) {
			return;
		}
		FileObject fileObject = null;
		try {
			FileSystemManager fileSystemManager = VFS.getManager();
			fileObject = fileSystemManager.resolveFile(path);
			if (!fileObject.exists()) {
				fileObject.createFolder();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(fileObject);
		}
	}

	/**
	 * 根据文件路径读取文件
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws BusinessException
	 */
	public static String read(String filePath) {
		StringBuilder value = new StringBuilder();
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(filePath), Const.ENCODING);
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				value.append(line);
			}
		} catch (Exception e) {
			throw new BusinessException("获取文件失败", e);
		} finally {
			close(bufferedReader);
			close(inputStreamReader);
		}
		return value.toString();
	}

	/**
	 * 上传过来的文件，把他放入服务器指定的目录，可以重命名
	 * 
	 * @param file
	 *            上传上来的文件
	 * @param filePath
	 *            文件存放文件路径,如有没有回自动创建
	 * @param fileReName
	 *            文件重命名，如果为空，则用文件本来的名字
	 */
	public static String fileToIo(MultipartFile file, String filePath, String fileReName) {
		if (file == null) {
			throw new BusinessException("上传文件不得为空");
		}
		if (StringUtils.isBlank(fileReName)) {
			fileReName = file.getOriginalFilename();
		}
		// 上传到目录
		InputStream is = null;
		String fullname = null;
		try {
			is = file.getInputStream();
			fullname = write(file.getInputStream(), filePath, fileReName);
		} catch (IOException e) {
			BusinessExceptionUtil.threwException(e);
		} finally {
			close(is);
		}
		return fullname;
	}

	/**
	 * 判断这个路径下文件或者文件夹是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static Boolean fileExist(String filePath) {
		File file = new File(filePath);
		return file.exists();

	}

	/**
	 * 删除文件
	 */
	public static Boolean delete(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		return true;
	}

	/**
	 * 根据文件夹，获取他的子文件
	 */
	public static String[] getChildList(String dirpath) {
		File file = new File(dirpath);
		String[] list = null;
		if (file.exists() && file.isDirectory()) {
			list = file.list();
		}
		return list;
	}

	/**
	 * 关闭流，不管是输出和输入流，都需要被关闭
	 * 
	 * @param stream
	 */
	public static void close(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}

	}
	

}
