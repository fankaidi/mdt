package co.kensure.api;

import java.util.Date;

import co.kensure.frame.BaseInfo;

/**
 * 令牌类时间
 * 
 * @author fankd
 */
public class TokenDesc extends BaseInfo {

	private static final long serialVersionUID = 3545276994084105527L;

	public TokenDesc(String val){
		setKey(val);
		setCreatedTime(new Date());
	}
	
	/**
	 * 关键字
	 */
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
