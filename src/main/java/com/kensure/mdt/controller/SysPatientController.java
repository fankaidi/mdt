package com.kensure.mdt.controller;

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
import co.kensure.mem.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysPatient;
import com.kensure.mdt.entity.query.SysPatientQuery;
import com.kensure.mdt.service.SysPatientService;

@Controller
@RequestMapping(value = "patient")
public class SysPatientController extends BaseController {

	@Autowired
	private SysPatientService sysPatientService;

	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo list(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		SysPatientQuery query = JSONObject.parseObject(json.toJSONString(), SysPatientQuery.class);
		AuthUser user = getCurrentUser(req);
		List<SysPatient> list = sysPatientService.selectList(query, page, user);
		long cont = sysPatientService.selectListCount(query, user);
		return new ResultRowsInfo(list, cont);
	}

	@ResponseBody
	@RequestMapping(value = "save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo save(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		AuthUser user = getCurrentUser(req);
		SysPatient patient = JSONObject.parseObject(json.toJSONString(), SysPatient.class);
		sysPatientService.save(patient, user);
		return new ResultInfo();
	}

	@ResponseBody
	@RequestMapping(value = "get", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo get(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.valueOf(req.getParameter("id"));
		SysPatient patient = sysPatientService.selectOne(id);
		return new ResultRowInfo(patient);
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delete(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.valueOf(req.getParameter("id"));
		sysPatientService.delete(id);
		return new ResultInfo();
	}

	/**
	 * 忽略
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hulue.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo hulue(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.valueOf(req.getParameter("id"));
		sysPatientService.hulue(id);
		return new ResultInfo();
	}

}
