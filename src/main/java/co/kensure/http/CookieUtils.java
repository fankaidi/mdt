package co.kensure.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 浏览器本地cookie
 * 
 * @author fankd
 *
 */
public class CookieUtils {

	/**
	 * 设置cookie逻辑
	 * 
	 * @param key
	 * @param val
	 * @param resp
	 */
	public static void addCookie(String key, String val, HttpServletResponse resp) {
		// 创建cookie对象
		val = HttpUtils.encoderURLString(val);
		Cookie compCookie = new Cookie(key, val);
		// 服务器把cookie响应给客户端，所有的cookie对象，都会在服务器端创建，通过http响应给客户端(浏览器)
		compCookie.setMaxAge(60 * 60 * 24 * 30);// 如果不设置使用时间，那么将取不到Cookie的值
		compCookie.setPath("/");// 一旦设置了cookie的路径，就只能通过这一个路径才能获取到cookie信息
		resp.addCookie(compCookie);
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param resp
	 * @param name
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse resp, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(null);
					cookie.setMaxAge(0);// 立即销毁cookie
					cookie.setPath("/");
					resp.addCookie(cookie);
					break;
				}
			}
		}
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		String val = null;
		Cookie[] cookies = request.getCookies();
		// 如果浏览器中存在Cookie
		if (cookies != null && cookies.length > 0) {
			// 遍历所有Cookie
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					val = cookie.getValue();
					break;
				}
			}
		}
		return val;

	}
}