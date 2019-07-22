package com.kensure.repository;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Test;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.http.HttpUtils;
import co.kensure.mem.AESUtils;
import co.kensure.mem.DesUtils;
import co.kensure.mem.MapUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 淘捏捏测试
 * 
 * @author fankaidi
 *
 */
public class TaoNieNieTest {

	private static String sKey = "N7OIOXM2QVZ62Z0D";
	private static String ivParameter = "3ETHGUJHXJCHRJ30";

	@Test
	public void test() {

		try {
			String[] aa = login();
			getcontent("a568993", aa[1], aa[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 模拟登录获取cookie和sessionid
	 */
	public static String[] login() throws IOException {
		String urlLogin = "http://www.taonienie.com/";
		Connection connect = Jsoup.connect(urlLogin);
		// 请求url获取响应信息
		Response res = connect.ignoreContentType(true).method(Method.GET).execute();// 执行请求
		// 获取返回的cookie
		Map<String, String> cookies = res.cookies();
		Set<String> keys = cookies.keySet();
		String cookie = "";
		for (String key : keys) {
			cookie += key + "=" + cookies.get(key) + ";";
		}
		Elements dd = res.parse().select("input").select("[name=__RequestVerificationToken]");
		String ccc = dd.attr("value");
		String[] aa = { cookie, ccc };
		return aa;
	}

	private String getcontent(String notaobao, String __RequestVerificationToken, String cookie) {
		String keyname = DesUtils.stringToHex(DesUtils.des("9HOCO7AP86HS0D3GZDISTDJ2XF7VW1BHB1LZS77MFTG50XHG", notaobao, 1, 0, "0"));
		JSONObject sign = getSign(keyname, __RequestVerificationToken, cookie);

		String code = sign.getString("code");
		String keyName = sign.getString("keyName");

		String tokens = code + "," + keyname;
		String userhtml = getUser(keyName, __RequestVerificationToken, tokens, cookie);
		try {
			int size = userhtml.length();
			userhtml = userhtml.substring(1, size - 1);
			String DeString = AESUtils.getInstance().decrypt(userhtml, "utf-8", sKey, ivParameter);
			System.out.println(DeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userhtml;
	}

	/**
	 * 获取签名
	 * 
	 * @param url
	 * @return
	 */
	private static JSONObject getSign(String keyname, String __RequestVerificationToken, String cookie) {
		String url = "http://www.taonienie.com/GetPlanToken";
		JSONObject sign = null;
		try {
			Map<String, String> params = MapUtils.genStringMap("keyname", keyname, "__RequestVerificationToken", __RequestVerificationToken);
			Map<String, String> headers = MapUtils.genStringMap("Cookie", cookie);

			String html = HttpUtils.postParams(url, params, headers);
			System.out.println("111==" + html);
			sign = JSONObject.parseObject(html);
		} catch (Exception e) {
			BusinessExceptionUtil.threwException("获取账号信息出错", e);
		}
		return sign;
	}

	/**
	 * 获取用户
	 * 
	 * @param url
	 * @return
	 */
	private static String getUser(String nick, String __RequestVerificationToken, String tokens, String cookie) {
		String url = "http://www.taonienie.com/RateUserInfo";
		String html = "";
		try {
			Map<String, String> params = MapUtils.genStringMap("nick", nick, "__RequestVerificationToken", __RequestVerificationToken, "tokens", tokens);
			Map<String, String> headers = MapUtils.genStringMap("Cookie", cookie);
			html = HttpUtils.postParams(url, params, headers);
			System.out.println("222==" + html);
		} catch (Exception e) {
			BusinessExceptionUtil.threwException("获取账号信息出错", e);
		}
		return html;
	}

}
