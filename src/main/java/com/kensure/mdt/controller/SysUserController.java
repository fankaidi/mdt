package com.kensure.mdt.controller;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import co.kensure.frame.ResultRowsInfo;
import co.kensure.http.RequestUtils;
import co.kensure.io.ReadExcelUtils;
import co.kensure.mem.DateUtils;
import co.kensure.mem.NumberUtils;
import co.kensure.mem.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysMenu;
import com.kensure.mdt.entity.SysOrg;
import com.kensure.mdt.entity.SysUser;
import com.kensure.mdt.entity.query.SysUserQuery;
import com.kensure.mdt.service.SysUserService;
import com.kensure.mdt.service.WsUserAndOrgService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "user")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WsUserAndOrgService wsUserAndOrgService;

	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo list(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		SysUserQuery query = JSONObject.parseObject(json.toJSONString(), SysUserQuery.class);
		AuthUser user = getCurrentUser(req);
		List<SysUser> list = sysUserService.selectList(page, user, query);
		long cont = sysUserService.selectListCount(user, query);
		return new ResultRowsInfo(list, cont);
	}
	
	@ResponseBody
	@RequestMapping(value = "selectUserByRoleId", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo selectUserByRoleId(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		Long id = json.getLong("id");
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		List<SysUser> list = sysUserService.selectUserByRoleId(id);
		return new ResultRowsInfo(list);
	}
	
	

	@ResponseBody
	@RequestMapping(value = "listAll", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo listAll(HttpServletRequest req, HttpServletResponse rep) {
		AuthUser user = getCurrentUser(req);
		JSONObject json = RequestUtils.paramToJson(req);
		String numberOrNameLike = json.getString("numberOrNameLike");
		List<SysUser> list = sysUserService.selectList(user,numberOrNameLike);
		return new ResultRowsInfo(list);
	}

	@ResponseBody
	@RequestMapping(value = "saveRole", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo save(Long userId, String checkedStr) {
		sysUserService.saveRole(userId, checkedStr);
		return new ResultInfo();
	}

	@ResponseBody
	@RequestMapping(value = "save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo save(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		SysUser user = JSONObject.parseObject(json.toJSONString(), SysUser.class);
		sysUserService.save(user);
		return new ResultInfo();
	}

	@ResponseBody
	@RequestMapping(value = "get", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo get(HttpServletRequest req, HttpServletResponse rep) {

		Long id = Long.valueOf(req.getParameter("id"));
		SysUser user = sysUserService.selectOne(id);
		return new ResultRowInfo(user);
	}

	/**
	 * 首页菜单
	 */
	@ResponseBody
	@RequestMapping(value = "indexMenus", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo indexMenus(HttpServletRequest req, HttpServletResponse rep) {
		SysMenu sysMenu = sysUserService.getTree(getCurrentUser(req));
		return new ResultRowInfo(sysMenu);
	}

	/**
	 * 同步用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "init", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo init(HttpServletRequest req, HttpServletResponse rep) {
		wsUserAndOrgService.initUser();
		return new ResultRowInfo();
	}
	
	/**
	 * 读取xls
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "initxls", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo initxls(HttpServletRequest req, HttpServletResponse rep) {
		  File file = new File("D:/user.xls");
	      List excelList = ReadExcelUtils.readExcel(file);
	      for (int i = 1; i < excelList.size(); i++) {
	            List list = (List) excelList.get(i);
	      
	            String number = list.get(0).toString().trim();
	            if(StringUtils.isBlank(number)){
	            	break;
	            }
	            SysUser user = sysUserService.selectByNumber(number);
	            String phonec = list.get(7).toString().trim();
	            if(StringUtils.length(phonec) == 6){
	            	user.setPhoneCornet(phonec);
	            	 sysUserService.update(user);
	            }
	 
	        }
		return new ResultRowInfo();
	}
}
