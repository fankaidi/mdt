package com.kensure.mdt.controller;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowsInfo;
import com.kensure.mdt.entity.resp.ToSthResp;
import com.kensure.mdt.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * MDT申请
 */
@Controller
@RequestMapping(value = "index")
public class IndexController extends BaseController {

	@Autowired
	private IndexService indexService;




}
