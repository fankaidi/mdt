package com.kensure.lc.controller;

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
import com.kensure.lc.model.LCDaiBan;
import com.kensure.lc.query.LCDaiBanQuery;
import com.kensure.lc.service.LCDaiBanService;
import com.kensure.mdt.controller.BaseController;
import com.kensure.mdt.entity.AuthUser;

@Controller
@RequestMapping(value = "daiban")
public class LCDaibanController extends BaseController {

	@Autowired
	private LCDaiBanService lCDaiBanService;

	/**
	 * 获取待办列表
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo list(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		AuthUser user = getCurrentUser(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		LCDaiBanQuery query = JSONObject.parseObject(json.toJSONString(), LCDaiBanQuery.class);
		List<LCDaiBan> list = lCDaiBanService.getUserDaiBan(user, page, query);
		long count = lCDaiBanService.getUserDaiBanCount(user, query);
		return new ResultRowsInfo(list, count);
	}

	/**
	 * 获取待办列表
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "search.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo search(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		String busitype = json.getString("busitype");
		Long busiid = json.getLong("busiid");
		LCDaiBan daiban = lCDaiBanService.getDaibanByBisiid(busiid, busitype);
		return new ResultRowInfo(daiban);
	}
}
