package co.kensure.ipproxy;

import co.kensure.frame.BaseInfo;

/**

 * 代理ip的信息
 * @see IpProxy
 * @author fankd
 *
 */
public class IpProxy extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/**
	 * ip地址
	 */
	private String ip;

	/**
	 * 端口
	 */
	private int port;
	
	/**
	 * http还是https
	 */
	private String type;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
