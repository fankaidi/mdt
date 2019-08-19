package com.kensure.mdt.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import co.kensure.frame.Const;
import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import co.kensure.frame.ResultRowsInfo;
import co.kensure.http.RequestUtils;
import co.kensure.io.FileUtils;
import co.kensure.mem.DateUtils;
import co.kensure.mem.PageInfo;
import co.kensure.mem.Utils;

import com.alibaba.fastjson.JSONObject;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysFile;
import com.kensure.mdt.query.SysFileQuery;
import com.kensure.mdt.service.SysFileService;

@Controller
@RequestMapping(value = "file")
public class SysFileController extends BaseController {

	@Autowired
	private SysFileService sysFileService;

	/**
	 * 上传图片,给富文本框,返回结果不一样
	 */
	@ResponseBody
	@RequestMapping(value = "addfiles.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo addfiles(MultipartHttpServletRequest request) {
		Collection<List<MultipartFile>> multipartFiles = request.getMultiFileMap().values();
		String path = "/filetemp/" + DateUtils.format(new Date(), DateUtils.DAY_FORMAT1);
		List<JSONObject> data = new ArrayList<>();
		for (List<MultipartFile> filelist : multipartFiles) {
			for (MultipartFile file : filelist) {
				String filename = file.getOriginalFilename();
				String name = Utils.getUUID() + filename.substring(filename.lastIndexOf("."));
				FileUtils.fileToIo(file, Const.ROOT_PATH + path, name);
				String url = request.getContextPath() + path + "/" + name;
				JSONObject obj = new JSONObject();
				obj.put("name", filename);
				obj.put("url", url);
				data.add(obj);
			}
		}
		return new ResultRowsInfo(data);
	}

	@ResponseBody
	@RequestMapping(value = "list.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo list(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		AuthUser user = getCurrentUser(req);
		SysFileQuery query = JSONObject.parseObject(json.toJSONString(), SysFileQuery.class);
		List<SysFile> list = sysFileService.selectList(query, user, page);
		long cont = sysFileService.selectListCount(query, user);
		return new ResultRowsInfo(list, cont);
	}

	@ResponseBody
	@RequestMapping(value = "get.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo get(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		Long id = json.getLong("id");
		SysFile file = sysFileService.detail(id);
		return new ResultRowInfo(file);
	}

	@ResponseBody
	@RequestMapping(value = "delete.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delete(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		Long id = json.getLong("id");
		sysFileService.delete(id);
		return new ResultRowInfo();
	}

	/**
	 * 保存数据
	 */
	@ResponseBody
	@RequestMapping(value = "save.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo save(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		SysFile file = JSONObject.parseObject(json.toJSONString(), SysFile.class);
		AuthUser user = getCurrentUser(req);
		sysFileService.save(file, user);
		return new ResultRowInfo();
	}
}
