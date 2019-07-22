package com.kensure.mdt.controller;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import co.kensure.frame.ResultRowsInfo;

import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysRole;
import com.kensure.mdt.entity.Tree;
import com.kensure.mdt.service.SysRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@Controller
@RequestMapping(value = "role")
public class SysRoleController  extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;

	@ResponseBody
	@RequestMapping(value = "findAll", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo findAll(HttpServletRequest req, HttpServletResponse rep) {
		AuthUser user = getCurrentUser(req);
        List<SysRole> roleList = sysRoleService.findAll(user);
        return new ResultRowsInfo(roleList);
	}

	@ResponseBody
	@RequestMapping(value = "save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo save(Long roleId, String checkedStr) {
        sysRoleService.save(roleId, checkedStr);
        return new ResultInfo();
	}

	@ResponseBody
	@RequestMapping(value = "readEmpRoles", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo readEmpRoles(Long userId) {
		List<Tree> trees = sysRoleService.readEmpRoles(userId);
		return new ResultRowInfo(trees);
	}

}
