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
import com.kensure.basekey.BaseKeyService;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtTeam;
import com.kensure.mdt.entity.MdtTeamAssess;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.MdtTeamIssue;
import com.kensure.mdt.entity.MdtTeamObjective;
import com.kensure.mdt.entity.MdtTeamPaper;
import com.kensure.mdt.entity.query.MdtTeamPinGuQuery;
import com.kensure.mdt.entity.query.MdtTeamQuery;
import com.kensure.mdt.service.MdtTeamAssessService;
import com.kensure.mdt.service.MdtTeamInfoService;
import com.kensure.mdt.service.MdtTeamIssueService;
import com.kensure.mdt.service.MdtTeamObjectiveService;
import com.kensure.mdt.service.MdtTeamPaperService;
import com.kensure.mdt.service.MdtTeamService;

/**
 * MDT团队管理
 */
@Controller
@RequestMapping(value = "mdtTeam")
public class MdtTeamController extends BaseController {

	@Autowired
	private MdtTeamService mdtTeamService;

	@Autowired
	private MdtTeamInfoService mdtTeamInfoService;

	@Autowired
	private MdtTeamObjectiveService mdtTeamObjectiveService;

	@Autowired
	private MdtTeamAssessService mdtTeamAssessService;

	@Autowired
	private MdtTeamPaperService mdtTeamPaperService;

	@Autowired
	private MdtTeamIssueService mdtTeamIssueService;

	@Autowired
	private BaseKeyService baseKeyService;

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
		MdtTeamQuery query = JSONObject.parseObject(json.toJSONString(), MdtTeamQuery.class);
		AuthUser user = getCurrentUser(req);
		List<MdtTeam> list = mdtTeamService.selectList(page, query, user);
		long cont = mdtTeamService.selectListCount(query, user);

		return new ResultRowsInfo(list, cont);
	}

	/**
	 * 新增
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo save(HttpServletRequest req, HttpServletResponse rep) {

		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeam team = JSONObject.parseObject(json.toJSONString(), MdtTeam.class);
		MdtTeamObjective teamObjective = JSONObject.parseObject(json.toJSONString(), MdtTeamObjective.class);
		mdtTeamService.save(team, teamObjective, getCurrentUser(req));
		return new ResultInfo();
	}

	/**
	 * 审核
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "audit", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo audit(HttpServletRequest req, HttpServletResponse rep) {
		AuthUser user = getCurrentUser(req);
		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeam team = JSONObject.parseObject(json.toJSONString(), MdtTeam.class);
		mdtTeamService.audit(team, user);
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
		Long id = Long.parseLong(req.getParameter("id"));
		MdtTeam team = mdtTeamService.getDetail(id);
		return new ResultRowInfo(team);
	}

	/**
	 * 两年考评初始数据
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "twoYearInfo.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo twoYearInfo(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.parseLong(req.getParameter("id"));
		AuthUser user = getCurrentUser(req);
		MdtTeam team = mdtTeamService.setTwoYearKaoPin(id,user);
		return new ResultRowInfo(team);
	}
	
	
	/**
	 * 删除MDT团队
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delete(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.parseLong(req.getParameter("id"));
		mdtTeamService.delMdtTeam(id);
		return new ResultInfo();
	}

	/**
	 * 获取第一个设置的MDT团队目标
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFirstByTeamId", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getFirstByTeamId(HttpServletRequest req, HttpServletResponse rep) {
		Long teamId = Long.parseLong(req.getParameter("teamId"));
		MdtTeamObjective obj = mdtTeamObjectiveService.getFirstByTeamId(teamId);
		return new ResultRowInfo(obj);
	}
	
	/**
	 * 获取第二个设置的MDT团队目标
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSecondByTeamId", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getSecondByTeamId(HttpServletRequest req, HttpServletResponse rep) {
		Long teamId = Long.parseLong(req.getParameter("teamId"));
		MdtTeamObjective obj = mdtTeamObjectiveService.getSecondByTeamId(teamId);
		return new ResultRowInfo(obj);
	}

	/**
	 * 查询所有 MDT团队基本信息（多人明细）
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findTeamInfoByTeamId", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo findTeamInfoByTeamId(HttpServletRequest req, HttpServletResponse rep) {
		Long teamId = Long.parseLong(req.getParameter("teamId"));
		List<MdtTeamInfo> list = mdtTeamInfoService.selectList(teamId);
		long cont = mdtTeamInfoService.selectListCount(teamId);
		return new ResultRowsInfo(list, cont);
	}

	/**
	 * 保存 MDT团队基本信息（多人明细）
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTeamInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveTeamInfo(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeamInfo team = JSONObject.parseObject(json.toJSONString(), MdtTeamInfo.class);
		mdtTeamInfoService.save(team);
		return new ResultInfo();
	}

	/**
	 * 查看 MDT团队基本信息（多人明细）
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTeamInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getTeamInfo(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.parseLong(req.getParameter("id"));
		MdtTeamInfo team = mdtTeamInfoService.selectOne(id);
		return new ResultRowInfo(team);
	}

	/**
	 * 删除 MDT团队基本信息（多人明细）
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delTeamInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delTeamInfo(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.parseLong(req.getParameter("id"));
		mdtTeamInfoService.delete(id);
		return new ResultInfo();
	}

	/**
	 * 查询所有 MDT团队
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findAllMdtTeam", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo findAllMdtTeam(HttpServletRequest req, HttpServletResponse rep) {
		AuthUser user = getCurrentUser(req);
		List<MdtTeam> list = mdtTeamService.findAllMdtTeam(user);
		return new ResultRowsInfo(list);
	}

	/**
	 * 保存 年度评估 团队目标建设
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTeamObjective", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveTeamObjective(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeamObjective teamObjective = JSONObject.parseObject(json.toJSONString(), MdtTeamObjective.class);
		mdtTeamObjectiveService.tianxie(teamObjective, getCurrentUser(req));
		return new ResultInfo();
	}

	/**
	 * 通过teamId查看团队目标建设
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTeamObjective", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getTeamObjective(HttpServletRequest req, HttpServletResponse rep) {
		Long teamId = Long.parseLong(req.getParameter("teamId"));
		List<MdtTeamObjective> list = mdtTeamObjectiveService.getTeamObjectiveList(teamId);
		return new ResultRowsInfo(list);
	}

	/**
	 * 查询MDT团队年度评估,一般是医务部的人用的
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAnnualTeam", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo selectAnnualTeam(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		MdtTeamQuery query = JSONObject.parseObject(json.toJSONString(), MdtTeamQuery.class);
		AuthUser user = getCurrentUser(req);
		List<MdtTeam> list = mdtTeamService.selectAnnualTeamList(page, query, user);
		long cont = mdtTeamService.selectAnnualTeamCount(query, user);

		return new ResultRowsInfo(list, cont);
	}

	/**
	 * 医务部发起MDT团队年度评估
	 */
	@ResponseBody
	@RequestMapping(value = "launchAnnualAssess", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo launchAnnualAssess(HttpServletRequest req, HttpServletResponse rep) {
		Long teamId = Long.parseLong(req.getParameter("teamId"));
		AuthUser user = getCurrentUser(req);
		mdtTeamObjectiveService.faqi(teamId, user);
		return new ResultInfo();
	}

	/**
	 * 审核 MDT团队年度评估
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "auditAnnualAssess", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo auditAnnualAssess(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeamObjective teamObjective = JSONObject.parseObject(json.toJSONString(), MdtTeamObjective.class);
		AuthUser user = getCurrentUser(req);
		mdtTeamObjectiveService.auditAnnualAssess(teamObjective, user);
		return new ResultInfo();
	}

	/**
	 * 保存 团队建设期满2年评估
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTeamAssess", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveTeamAssess(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeamAssess obj = JSONObject.parseObject(json.toJSONString(), MdtTeamAssess.class);
		mdtTeamAssessService.tianxie(obj, getCurrentUser(req));
		return new ResultRowInfo(obj);
	}

	/**
	 * 通过teamId查看团队建设期满2年评估
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTeamAssess", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getTeamAssess(HttpServletRequest req, HttpServletResponse rep) {
		Long teamId = Long.parseLong(req.getParameter("teamId"));
		MdtTeamAssess obj = mdtTeamAssessService.getTeamAssess(teamId);
		return new ResultRowInfo(obj);
	}

	/**
	 * 发起 MDT团队建设期满2年评估
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "launchTwoYearAssess", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo launchTwoYearAssess(HttpServletRequest req, HttpServletResponse rep) {
		Long teamId = Long.parseLong(req.getParameter("teamId"));
		AuthUser user = getCurrentUser(req);
		mdtTeamAssessService.faqi(teamId, user);
		return new ResultInfo();
	}

	/**
	 * 审核 团队建设期满2年评估
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "auditTwoYearAssess", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo auditTwoYearAssess(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeamAssess obj = JSONObject.parseObject(json.toJSONString(), MdtTeamAssess.class);
		AuthUser user = getCurrentUser(req);
		mdtTeamAssessService.auditTwoYearAssess(obj, user);
		return new ResultInfo();
	}

	/**
	 * 查询 建期两年MDT病种研究方向发表的论文
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectTeamPaper", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo selectTeamPaper(HttpServletRequest req, HttpServletResponse rep) {

		Long teamId = Long.parseLong(req.getParameter("teamId"));

		List<MdtTeamPaper> list = mdtTeamPaperService.selectList(teamId);

		return new ResultRowsInfo(list, list.size());
	}

	/**
	 * 查看 建期两年MDT病种研究方向发表的论文
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTeamPaper", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getTeamPaper(HttpServletRequest req, HttpServletResponse rep) {

		Long id = Long.parseLong(req.getParameter("id"));

		MdtTeamPaper obj = mdtTeamPaperService.selectOne(id);

		return new ResultRowInfo(obj);
	}

	/**
	 * 保存 建期两年MDT病种研究方向发表的论文
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTeamPaper", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveTeamPaper(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeamPaper obj = JSONObject.parseObject(json.toJSONString(), MdtTeamPaper.class);
		mdtTeamPaperService.save(obj);
		return new ResultInfo();
	}

	/**
	 * 删除 建期两年MDT病种研究方向发表的论文
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delTeamPaper", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delTeamPaper(HttpServletRequest req, HttpServletResponse rep) {

		Long id = Long.parseLong(req.getParameter("id"));

		mdtTeamPaperService.delete(id);

		return new ResultInfo();
	}

	/**
	 * 查询 建期两年MDT病种研究方向开展的课题探究
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectTeamIssue", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo selectTeamIssue(HttpServletRequest req, HttpServletResponse rep) {

		Long teamId = Long.parseLong(req.getParameter("teamId"));

		List<MdtTeamIssue> list = mdtTeamIssueService.selectList(teamId);

		return new ResultRowsInfo(list, list.size());
	}

	/**
	 * 查看 建期两年MDT病种研究方向开展的课题探究
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTeamIssue", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getTeamIssue(HttpServletRequest req, HttpServletResponse rep) {

		Long id = Long.parseLong(req.getParameter("id"));

		MdtTeamIssue obj = mdtTeamIssueService.selectOne(id);

		return new ResultRowInfo(obj);
	}

	/**
	 * 保存 建期两年MDT病种研究方向开展的课题探究
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTeamIssue", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveTeamIssue(HttpServletRequest req, HttpServletResponse rep) {

		JSONObject json = RequestUtils.paramToJson(req);
		MdtTeamIssue obj = JSONObject.parseObject(json.toJSONString(), MdtTeamIssue.class);

		mdtTeamIssueService.save(obj);
		return new ResultInfo();
	}

	/**
	 * 删除 建期两年MDT病种研究方向开展的课题探究
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delTeamIssue", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delTeamIssue(HttpServletRequest req, HttpServletResponse rep) {

		Long id = Long.parseLong(req.getParameter("id"));

		mdtTeamIssueService.delete(id);

		return new ResultInfo();
	}

	/**
	 * 获取MdtTeam主键
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMdtTeamKey", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getMdtTeamKey(HttpServletRequest req, HttpServletResponse rep) {
		Long key = baseKeyService.getKey("mdt_team");
		return new ResultRowInfo(key);
	}

	/**
	 * 团队月度指标
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findYueDu.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo findYueDu(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		MdtTeamPinGuQuery query = JSONObject.parseObject(json.toJSONString(), MdtTeamPinGuQuery.class);
		AuthUser user = getCurrentUser(req);
		List<MdtTeam> list = mdtTeamService.selectListPinGu(page, query, user);
		long cont = mdtTeamService.selectListPinGuCount(query, user);
		return new ResultRowsInfo(list, cont);
	}
	
	/**
	 * 团队月度指标
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "userTeam.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo userTeam(HttpServletRequest req, HttpServletResponse rep) {
		AuthUser user = getCurrentUser(req);
		List<MdtTeamInfo> list = mdtTeamInfoService.selectByUserId(user.getId());
		return new ResultRowsInfo(list);
	}
	
	

}
