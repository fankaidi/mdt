package com.kensure.mdt.controller;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import com.kensure.mdt.entity.SysMenu;
import com.kensure.mdt.entity.Tree;
import com.kensure.mdt.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 校验类数据
 * 
 */
@Controller
@RequestMapping(value = "menu")
public class SysMenuController  extends BaseController {

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "aaa", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo updatescgz(HttpServletRequest req, HttpServletResponse rep) {


        SysMenu sysMenu = sysMenuService.getTree();


        return new ResultRowInfo(sysMenu);
	}

	/**
	 * 获取角色菜单树
	 */
	@ResponseBody
	@RequestMapping(value = "readRoleMenus", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo readRoleMenus(Long id) {

		List<Tree> trees = sysMenuService.readRoleMenus(id);
        return new ResultRowInfo(trees);
	}


}
