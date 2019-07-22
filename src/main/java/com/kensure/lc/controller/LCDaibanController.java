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
import co.kensure.frame.ResultRowsInfo;

import com.kensure.lc.model.LCDaiBan;
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
		AuthUser user = getCurrentUser(req);
		List<LCDaiBan> list = lCDaiBanService.getUserDaiBan(user.getId());
		return new ResultRowsInfo(list);
	}
}