package co.kensure.http;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * HttpServletRequest工具类
 * 
 * @author fankd created on 2017-5-8
 */
public class RequestUtils {

	/**
	 * 将request中的参数变成json
	 * 
	 * @param request
	 * @return
	 */
	public static JSONObject paramToJson(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			if (StringUtils.isNotBlank(value) && value.startsWith("[")) {
				json.put(paraName, JSONObject.parseArray(value));
			} else if (StringUtils.isNotBlank(value) && value.startsWith("{")) {
				json.put(paraName, JSONObject.parseObject(value));
			} else {
				json.put(paraName, value);
			}
		}
		return json;
	}

	/**
	 * 将request中的参数设置到setAttribute，方便jsp获取
	 * 
	 * @param request
	 * @return
	 */
	public static void paramToAttr(HttpServletRequest request) {
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			request.setAttribute(paraName, value);
		}
	}

	/**
	 * getAttribute，返回string，如果为空，返回空格
	 * 
	 * @param request
	 * @return
	 */
	public static String getStringIfNullBlank(HttpServletRequest request, String paraName) {
		String value = (String) request.getAttribute(paraName);
		if (value == null) {
			value = "";
		}
		return value;
	}

	/**
	 * 获取用户设备
	 * 
	 * @param request
	 * @return 设备类型 1是手机，2是pc
	 */
	public static int getUserAgent(HttpServletRequest request) {
		String requestHeader = request.getHeader("user-agent");
		int type = 2;
		if (requestHeader == null)
			return type;
		String[] deviceArray = new String[] { "android", "iPhone", "iPad", "windows phone" };
		requestHeader = requestHeader.toLowerCase();
		for (int i = 0; i < deviceArray.length; i++) {
			if (requestHeader.indexOf(deviceArray[i]) > 0) {
				type = 1;
				break;
			}
		}
		return type;
	}

	/**
	 * 是否微信浏览器
	 */
	public static boolean isWechat(HttpServletRequest request) {
		String ua = request.getHeader("User-Agent").toLowerCase();
		if (ua.indexOf("micromessenger") > -1) {
			return true;// 微信
		}
		return false;// 非微信手机浏览器
	}

	/**
	 * 获取用户设备
	 * 
	 * @param request
	 * @return 设备类型
	 */
	public static String getAgent(HttpServletRequest request) {
		String requestHeader = request.getHeader("user-agent");
		return requestHeader;
	}

	/**
	 * 获取用户客户端ip
	 * 
	 * @param request
	 * @return 客户端ip
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 获取用户直接ip
	 * 
	 * @param request
	 * @return 客户端ip
	 */
	public static String getDip(HttpServletRequest request) {
		return request.getRemoteAddr();
	}
	


	/**
	 * 获取前一个url
	 * 
	 * @param request
	 * @return 前一个url
	 */
	public static String getReferer(HttpServletRequest request) {
		String request_url = request.getHeader("Referer");
		return request_url;
	}

	/**
	 * 是否移动手机用户
	 * 
	 * @param request
	 * @return 是 ，true，不是false
	 */
	public static boolean isMobileAgent(HttpServletRequest request) {
		int agentType = getUserAgent(request);
		boolean type = agentType == 1;
		return type;
	}

}
