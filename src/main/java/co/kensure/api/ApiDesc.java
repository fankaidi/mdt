package co.kensure.api;

import co.kensure.frame.BaseInfo;

/**
 * 接口使用描述
 * 
 * @author fankd
 */
public class ApiDesc extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	/**
	 * 关键字
	 */
	private String key;
	/**
	 * 名称
	 */
	private String name;

	/**
	 * none:匿名,all:全部,admin:管理员,user:用户,saler:商家,多个用逗号隔开
	 */
	private String auth;
	
	/**
	 * 微信公众号自动登录
	 */
	private String wxgzh;

	/**
	 * get 只是查询接口，put是保存接口
	 */
	private String type;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWxgzh() {
		return wxgzh;
	}

	public void setWxgzh(String wxgzh) {
		this.wxgzh = wxgzh;
	}

}
