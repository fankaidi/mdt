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
import co.kensure.frame.ResultRowsInfo;
import co.kensure.mem.NumberUtils;

import com.kensure.mdt.lc.model.LCHistory;
import com.kensure.mdt.lc.service.LCHistoryService;

@Controller
@RequestMapping(value = "lc")
public class LCHistoryController  extends BaseController  {

	@Autowired
	private LCHistoryService lCHistoryService;

    /**
     * 获取列表
     * @param req
     * @param rep
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "list.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo login(HttpServletRequest req, HttpServletResponse rep) {

		String busitype = req.getParameter("busitype");
		Long bisiid = NumberUtils.parseLong(req.getParameter("bisiid"), 0L);
		List<LCHistory> list = lCHistoryService.selectByBusiid(busitype, bisiid);
        return new ResultRowsInfo(list);
	}
}
