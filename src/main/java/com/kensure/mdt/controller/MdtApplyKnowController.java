package com.kensure.mdt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import co.kensure.http.RequestUtils;

import com.alibaba.fastjson.JSONObject;
import com.kensure.mdt.model.MdtApplyKnow;
import com.kensure.mdt.service.MdtApplyKnowService;

/**
 * 知情同意书
 */
@Controller
@RequestMapping(value = "applyknow")
public class MdtApplyKnowController extends BaseController {

	@Autowired
	private MdtApplyKnowService mdtApplyKnowService;

	/**
	 * 新增
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo save(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtApplyKnow apply = JSONObject.parseObject(json.toJSONString(), MdtApplyKnow.class);
		mdtApplyKnowService.save(apply, getCurrentUser(req));
		return new ResultInfo();
	}

	/**
	 * 查询
	 */
	@ResponseBody
	@RequestMapping(value = "get.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo get(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		Long applyId = json.getLong("applyId");
		MdtApplyKnow know = mdtApplyKnowService.selectByApplyId(applyId);
		return new ResultRowInfo(know);
	}

}
