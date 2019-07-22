package com.kensure.mdt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表对象类
 */
public class SysMenu implements Serializable{

	private static final long serialVersionUID = 3545276994084105527L;
	
	/**菜单表*/		
	private Long id;

	/**菜单名称*/		
	private String name; 

	/**图标*/		
	private String icon; 

	/**链接*/		
	private String url; 

	/**父id*/		
	private Long pid;

	private List<SysMenu> menus;//


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public List<SysMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenu> menus) {
		this.menus = menus;
	}
}
