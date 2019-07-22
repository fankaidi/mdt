package co.kensure.ipproxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.kensure.frame.BaseInfo;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;
import co.kensure.mem.NumberUtils;

/**
 * 
 * 代理ip的爬取
 * 
 * @see IpProxyUtils
 * @author fankd
 *
 */
public class IpProxyUtils extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/**
	 * ip地址列表
	 */
	private static List<IpProxy> ipList = new ArrayList<>();

	private static int ipindex = 0;

	/**
	 * 爬取代理ip的信息
	 * 
	 * @param type
	 */
	public static void reflesh() {
		ipList.clear();
		try {
			String urlLogin = "https://www.xicidaili.com/nt/1";
			Connection connect = Jsoup.connect(urlLogin);
			Response res = connect.ignoreContentType(true).method(Method.GET).execute();
			Element ip_list = res.parse().getElementById("ip_list");
			Elements ipstr = ip_list.getElementsByTag("tr");
			for (Element tr : ipstr) {
				Elements tds = tr.getElementsByTag("td");
				if (tds.size() != 10) {
					continue;
				}
				// ip地址
				String ip = tds.get(1).text();
				// 端口
				int port = NumberUtils.parseInteger(tds.get(2).text(), null);
				// 代理类型
				String type = tds.get(5).text();
				if (!"HTTP".equalsIgnoreCase(type)) {
					continue;
				}
				IpProxy ipProxy = new IpProxy();
				ipProxy.setIp(ip);
				ipProxy.setPort(port);
				ipProxy.setType(type);
				if (testActive(ipProxy)) {
					ipList.add(ipProxy);
				}

			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println("获取有效ip：" + ipList.size());
		}
	}

	public static void main(String[] args) {
		reflesh();
		for (IpProxy ip : ipList) {
			System.out.println(ip.getIp() + ":" + ip.getPort());
		}
	}

	/**
	 * 测试ip是否可用
	 */
	public static boolean testActive(IpProxy ipProxy) {
		boolean flag = false;
		try {
			// 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
			String ip = ipProxy.getIp();
			int port = ipProxy.getPort();
			String url = "http://www.baidu.com";
			Connection connect = Jsoup.connect(url);
			Map<String, String> headers = MapUtils.genStringMap("Connection", "keep-alive", "X-Forwarded-For", ip);
			connect.ignoreContentType(true).proxy(ip, port).method(Method.GET).headers(headers).timeout(1000).execute();
			flag = true;
		} catch (IOException e) {
		}
		return flag;
	}

	/**
	 * 获取下一个ip
	 * 
	 * @return
	 */
	public static IpProxy getNext() {
		// 没有就刷新
		if (CollectionUtils.isEmpty(ipList)) {
			reflesh();
		}
		IpProxy ip = ipList.get(ipindex);
		ipindex++;
		ipindex = ipindex % ipList.size();
		return ip;
	}

}
