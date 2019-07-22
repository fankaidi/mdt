package com.kensure.repository;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.junit.Test;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.http.HttpUtils;
import co.kensure.mem.MapUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 淘宝降权测试
 * 
 * @author fankaidi
 *
 */
public class TaobaoJiangquanTest {

	@Test
	public void test() {
		String name = "优质名流品牌";
		String cookie = login();
		String time = System.currentTimeMillis() + "";
		String sign = getSign(name, cookie, time);
		getUser(name, cookie, sign, time);
	}

	/**
	 * 模拟登录获取cookie和sessionid
	 */
	public String login() {
		String urlLogin = "http://app.tk1788.com/app/superscan/op.jsp?m=login&username=13606816944&password=e9bc0e13a8a16cbb07b175d92a113126&type=1&sign=1553483725893splice2108810db3b79b17dafe433c8e07278";
		Connection connect = Jsoup.connect(urlLogin);
		// 请求url获取响应信息
		Response res = null;

		String cookie = "";
		try {
			res = connect.ignoreContentType(true).method(Method.GET).execute();
			// 获取返回的cookie
			Map<String, String> cookies = res.cookies();
			Set<String> keys = cookies.keySet();
			for (String key : keys) {
				cookie += key + "=" + cookies.get(key) + ";";
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {

		}
		return cookie;
	}

	/**
	 * 获取签名
	 * 
	 * @param url
	 * @return
	 */
	public static String getSign(String aliim, String cookie, String time) {
		String url = "http://app.tk1788.com/app/superscan/op.jsp";
		String sign = "";
		try {
			Map<String, String> params = MapUtils.genStringMap("m", "getSign", "aliim", aliim, "timeStamp", time);
			Map<String, String> headers = MapUtils.genStringMap("Cookie", cookie);
			headers.put("Host", "app.tk1788.com");
			String html = HttpUtils.postParams(url, params, headers);
			System.out.println("111==" + html);
			sign = JSONObject.parseObject(html).getString("sign");
		} catch (Exception e) {
			BusinessExceptionUtil.threwException("获取账号信息出错", e);
		}
		return sign;
	}

	/**
	 * 获取宝贝详情
	 * 
	 * @param url
	 * @return
	 */
	public static String getUser(String aliim, String cookie, String sign, String time) {
		String url = "http://app.tk1788.com/app/superscan/op.jsp";
		String html = "";
		try {
			Map<String, String> params = MapUtils.genStringMap("m", "sAliim", "aliim", aliim, "costType", "costType6", "timeStamp", time);
			params.put("sign", sign);
			params.put("ifQBase", "true");
			params.put("ifQReport", "true");
			params.put("ifQDownB", "true");
			params.put("judgeAnother", "true");
			Map<String, String> headers = MapUtils.genStringMap("Cookie", cookie);
			headers.put("Host", "app.tk1788.com");
			headers.put("Origin", "http://app.tk1788.com");
			headers.put("Referer", "http://app.tk1788.com/app/superscan/searchAliim.jsp");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");

			headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
			headers.put("Accept-Encoding", "gzip, deflate");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Connection", "keep-alive");
			headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			html = HttpUtils.postParams(url, params, headers);
			System.out.println("222==" + html);
		} catch (Exception e) {
			BusinessExceptionUtil.threwException("获取账号信息出错", e);
		}
		return html;
	}

}
