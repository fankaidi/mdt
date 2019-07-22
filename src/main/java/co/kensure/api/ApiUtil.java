package co.kensure.api;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口定义配置解析类
 * 
 * @author fankd
 *
 */

public class ApiUtil {
	public static Logger logger = Logger.getLogger(ApiUtil.class);

	// 根据关键字查
	private static Map<String, ApiDesc> apiMap = new HashMap<String, ApiDesc>();

	public static void init() {
		try {
			apiPageHelp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ApiDesc get(String key) {
		ApiDesc api = apiMap.get(key);
		return api;
	}
	
	public static String getUrl(String key) {
		return "";
	}
	

	/**
	 * 初始化接口定义,扫描所有interface下的xml文件
	 * 
	 * @throws Exception
	 */
	private static void apiPageHelp() {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] arrResource = resourceResolver.getResources("classpath*:api/*.xml");
			for (Resource res : arrResource) {
				api(res);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 将单个文档初始化成对象，并且放入缓存
	 * 
	 * @throws Exception
	 */
	private static void api(Resource res) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(res.getURL());
			Element root = doc.getRootElement();
			initHelp(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void initHelp(Element root) {
		List<Element> listElement = root.elements("api");// 所有一级子节点的list
		for (Element e : listElement) {// 遍历所有一级子节点
			ApiDesc help = new ApiDesc();
			String key = e.attributeValue("key");
			help.setKey(key);
			help.setName(e.attributeValue("name"));
			help.setAuth(e.attributeValue("auth"));
			help.setType(e.attributeValue("type"));
			help.setWxgzh(e.attributeValue("wxgzh"));
			apiMap.put(key, help);
		}
	}

}
