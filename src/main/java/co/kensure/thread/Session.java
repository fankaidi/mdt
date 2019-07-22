package co.kensure.thread;

import javax.servlet.http.HttpServletRequest;

import co.kensure.http.RequestUtils;

/**
 * 一般的客户端会话类
 *
 * @author fankd
 */

public class Session {

	/**
	 * 会话id
	 */
	private String sessionid;

	/**
	 * 请求的浏览器id
	 */
	private String reqid;

	/**
	 * 用户的客户端类型
	 */
	private String userAgent;

	/**
	 * 用户的ip
	 */
	private String ip;

	/**
	 * 来源url
	 */
	private String url;
	
	public Session(HttpServletRequest request){
		this.userAgent=RequestUtils.getAgent(request);
		this.ip = RequestUtils.getClientIp(request);
		this.sessionid = request.getParameter("sessionid");
		this.reqid = request.getSession().getId();
		this.url = request.getRequestURI();
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getReqid() {
		return reqid;
	}

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
