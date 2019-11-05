package com.kensure.mdt.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;

import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.service.SysUserService;

@Controller
@RequestMapping(value = "auth")
public class SysAuthController  extends BaseController  {

	@Autowired
	private SysUserService sysUserService;

    /**
     * 登录
     * @param req
     * @param rep
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "login", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo login(HttpServletRequest req, HttpServletResponse rep) {

		String username = req.getParameter("username");
		String password = req.getParameter("password");

        AuthUser user = sysUserService.login(username, password);

        req.getSession().setAttribute("user", user);
        req.getSession().setMaxInactiveInterval(60 * 60 * 24);

        return new ResultInfo();
	}
	
	
    /**
     * 外部链接用工号进行直接登录
     * @return
     */
	@RequestMapping("loginbylink")
	public String loginByLink(HttpServletRequest req, HttpServletResponse rep, Model model) {
		String username = req.getParameter("loginUser");
        AuthUser user = sysUserService.loginByLink(username);
        req.getSession().setAttribute("user", user);
        req.getSession().setMaxInactiveInterval(60 * 60 * 24);
        try {
			rep.sendRedirect("/mdt/assets/index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "assets/index.html";
	}

    /**
     * 退出
     * @param req
     * @param rep
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "logout", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo logout(HttpServletRequest req, HttpServletResponse rep) {

        req.getSession().removeAttribute("user");
        return new ResultInfo();
	}

	@ResponseBody
	@RequestMapping(value = "getUser", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getUser(HttpServletRequest req, HttpServletResponse rep) {

        Object obj = req.getSession().getAttribute("user");
        return new ResultRowInfo(obj);
	}


}
