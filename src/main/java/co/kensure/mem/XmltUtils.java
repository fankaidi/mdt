package co.kensure.mem;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

/**
 * XML解析工具类，统一用dom4j解析
 * 
 * @author fankd
 * @since SHK Framework 1.0
 */
public final class XmltUtils {

	/**
	 * 解析成dom
	 * @param xml
	 * @return
	 */
	public static Document parseDom4j(String xml) {
		Document document = null;
		try {
			// 获取Document节点
			document = DocumentHelper.parseText(xml);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return document;
	}

}
