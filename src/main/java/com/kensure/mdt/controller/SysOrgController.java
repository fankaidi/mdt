package com.kensure.mdt.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import co.kensure.frame.ResultRowsInfo;
import co.kensure.http.RequestUtils;
import co.kensure.io.ReadExcelUtils;
import co.kensure.mem.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysOrg;
import com.kensure.mdt.entity.SysTree;
import com.kensure.mdt.service.SysOrgService;
import com.kensure.mdt.service.WsUserAndOrgService;

/**
 * 组织机构管理
 */
@Controller
@RequestMapping(value = "org")
public class SysOrgController extends BaseController {

	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private WsUserAndOrgService wsUserAndOrgService;

	/**
	 * 分页查询
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findByPage", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo findByPage(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		AuthUser user = getCurrentUser(req);
		List<SysOrg> list = sysOrgService.selectList(page, user);
		long cont = sysOrgService.selectListCount(user);

		return new ResultRowsInfo(list, cont);
	}

	/**
	 * 查询所有
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listAll", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo listAll(HttpServletRequest req, HttpServletResponse rep) {
		AuthUser user = getCurrentUser(req);
		List<SysOrg> list = sysOrgService.selectAllList(user);
		return new ResultRowsInfo(list);
	}

	/**
	 * 新增
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo add(HttpServletRequest req, HttpServletResponse rep) {

		JSONObject json = RequestUtils.paramToJson(req);
		SysOrg org = JSONObject.parseObject(json.toJSONString(), SysOrg.class);
		sysOrgService.save(org);
		return new ResultInfo();
	}

	/**
	 * 更新
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo update(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		SysOrg org = JSONObject.parseObject(json.toJSONString(), SysOrg.class);
		sysOrgService.update(org);
		return new ResultInfo();
	}

	/**
	 * 查看
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo get(HttpServletRequest req, HttpServletResponse rep) {

		String id = req.getParameter("id");
		SysOrg org = sysOrgService.selectOne(id);
		return new ResultRowInfo(org);
	}

	/**
	 * 删除
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delete(HttpServletRequest req, HttpServletResponse rep) {
		String id = req.getParameter("id");
		sysOrgService.delete(id);
		return new ResultInfo();
	}

	/**
	 * 查询，返回树形结构
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectTree", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo selectTree(HttpServletRequest req, HttpServletResponse rep) {
		AuthUser user = getCurrentUser(req);
		List<SysTree> list = sysOrgService.selectTree(user);
		return new ResultRowsInfo(list);
	}

	/**
	 * 同步机构
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "init", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo init(HttpServletRequest req, HttpServletResponse rep) {
		wsUserAndOrgService.initOrg();
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
		File file = new File("D:/org1.xls");
		List excelList = ReadExcelUtils.readExcel(file);
		for (int i = 0; i < excelList.size(); i++) {
			List list = (List) excelList.get(i);
			SysOrg org = new SysOrg();
			org.setId(list.get(0).toString().trim());
			org.setName(list.get(1).toString().trim());
			org.setPid(list.get(2).toString().trim());
			org.setArea(list.get(3).toString().trim());
			sysOrgService.insert(org);
		}
		return new ResultRowInfo();
	}
}
