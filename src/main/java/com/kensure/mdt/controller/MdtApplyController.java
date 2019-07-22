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
import com.kensure.mdt.entity.MdtApply;
import com.kensure.mdt.entity.MdtApplyDoctor;
import com.kensure.mdt.entity.MdtApplyFeedback;
import com.kensure.mdt.entity.MdtApplyOpinion;
import com.kensure.mdt.entity.SysGrade;
import com.kensure.mdt.entity.bo.MdtGradeReq;
import com.kensure.mdt.entity.resp.ExpertGradeList;
import com.kensure.mdt.service.MdtApplyDoctorService;
import com.kensure.mdt.service.MdtApplyFeedbackService;
import com.kensure.mdt.service.MdtApplyOpinionService;
import com.kensure.mdt.service.MdtApplyService;
import com.kensure.mdt.service.MdtGradeItemService;
import com.kensure.mdt.service.SysGradeService;

/**
 * MDT申请
 */
@Controller
@RequestMapping(value = "mdtApply")
public class MdtApplyController extends BaseController {

	@Autowired
	private MdtApplyService mdtApplyService;
	@Autowired
	private MdtApplyDoctorService mdtApplyDoctorService;
	@Autowired
	private SysGradeService sysGradeService;

	@Autowired
	private MdtGradeItemService mdtGradeItemService;

	@Autowired
	private MdtApplyFeedbackService mdtApplyFeedbackService;

	@Autowired
	private MdtApplyOpinionService mdtApplyOpinionService;

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
		AuthUser user = getCurrentUser(req);
		List<MdtApply> list = mdtApplyService.selectList(page, user);
		long cont = mdtApplyService.selectListCount(user);

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
		MdtApply apply = JSONObject.parseObject(json.toJSONString(), MdtApply.class);
		mdtApplyService.save(apply, getCurrentUser(req));
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
		MdtApply apply = mdtApplyService.selectOne(id);
		return new ResultRowInfo(apply);
	}
	
	/**
	 * 查看详情,包括子对象
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "detail", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo detail(HttpServletRequest req, HttpServletResponse rep) {
		Long id = Long.parseLong(req.getParameter("id"));
		MdtApply apply = mdtApplyService.getDetail(id);
		return new ResultRowInfo(apply);
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
		JSONObject json = RequestUtils.paramToJson(req);
		MdtApply apply = JSONObject.parseObject(json.toJSONString(), MdtApply.class);
		AuthUser user = getCurrentUser(req);
		mdtApplyService.audit(apply, user);
		return new ResultInfo();
	}

	/**
	 * 从MDT团队中将专家选入MDT专家库
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addApplyDoctorFormTeam", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo addApplyDoctorFormTeam(HttpServletRequest req, HttpServletResponse rep) {

		Long teamInfoId = Long.parseLong(req.getParameter("teamInfoId"));
		Long applyId = Long.parseLong(req.getParameter("applyId"));

		mdtApplyService.addApplyDoctorFormTeam(teamInfoId, applyId);
		return new ResultInfo();
	}

	/**
	 * 添加MDT拟请专家
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addApplyDoctor", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo addApplyDoctor(HttpServletRequest req, HttpServletResponse rep) {

		JSONObject json = RequestUtils.paramToJson(req);
		MdtApplyDoctor entiy = JSONObject.parseObject(json.toJSONString(), MdtApplyDoctor.class);

		mdtApplyDoctorService.save(entiy);
		return new ResultInfo();
	}

	/**
	 * 查看MDT拟请专家
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getApplyDoctor", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getApplyDoctor(HttpServletRequest req, HttpServletResponse rep) {

		Long id = Long.parseLong(req.getParameter("id"));
		MdtApplyDoctor entiy = mdtApplyDoctorService.selectOne(id);
		return new ResultRowInfo(entiy);
	}

	/**
	 * 查询MDT拟请专家
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listApplyDoctorByApplyId", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo listApplyDoctorByApplyId(HttpServletRequest req, HttpServletResponse rep) {

		Long applyId = Long.parseLong(req.getParameter("applyId"));

		List<MdtApplyDoctor> list = mdtApplyDoctorService.selectList(applyId);
		return new ResultRowsInfo(list, list.size());
	}

	/**
	 *
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listExpertGradeByApplyId", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo listExpertGradeByApplyId(HttpServletRequest req, HttpServletResponse rep) {

		Long applyId = Long.parseLong(req.getParameter("applyId"));

		List<ExpertGradeList> list = mdtApplyDoctorService.listExpertGrade(applyId);
		return new ResultRowsInfo(list, list.size());
	}

	/**
	 * 删除MDT拟请专家
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delApplyDoctorById", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delApplyDoctorById(HttpServletRequest req, HttpServletResponse rep) {

		Long id = Long.parseLong(req.getParameter("id"));

		mdtApplyDoctorService.delete(id);
		return new ResultInfo();
	}

	/**
	 * 发送MDT通知短信
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendMsg", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo sendMsg(HttpServletRequest req, HttpServletResponse rep) {

		JSONObject json = RequestUtils.paramToJson(req);
		MdtApply apply = JSONObject.parseObject(json.toJSONString(), MdtApply.class);

		mdtApplyService.sendMsg(apply);
		return new ResultInfo();
	}

	/**
	 * 获取评分项目
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGradeItem", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getExpertGradeItem(HttpServletRequest req, HttpServletResponse rep) {

		String type = req.getParameter("type");

		List<SysGrade> list = sysGradeService.getExpertGradeItem(type);
		return new ResultRowsInfo(list);
	}

	/**
	 * 保存专家评分项目
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveExpertGrade", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveExpertGrade(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {
		mdtGradeItemService.saveExpertGrade(mdtGradeReq);
		return new ResultInfo();
	}

	/**
	 * 保存组织科室评分项目
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveDeptGrade", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveDeptGrade(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtApply apply = JSONObject.parseObject(json.toJSONString(), MdtApply.class);
		mdtApplyService.saveKSPinFen(apply);
		return new ResultInfo();
	}

	/**
	 * 录入专家评分
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveZJPinFen", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveZJPinFen(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtApply apply = JSONObject.parseObject(json.toJSONString(), MdtApply.class);
		mdtApplyService.saveZJPinFen(apply);
		return new ResultInfo();
	}
	
	
	/**
	 * 计算费用
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "calculateFee", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo calculateFee(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {

		Long applyId = Long.parseLong(req.getParameter("applyId"));
		Long price = mdtApplyService.calculateFee(applyId);

		return new ResultRowInfo(price);
	}

	/**
	 * 查询反馈
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectFeedbackList", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo selectFeedbackList(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {

		Long applyId = Long.parseLong(req.getParameter("applyId"));
		List<MdtApplyFeedback> list = mdtApplyFeedbackService.selectList(applyId);

		return new ResultRowsInfo(list, list.size());
	}

	/**
	 * 保存反馈
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveFeedback", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveFeedback(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {

		JSONObject json = RequestUtils.paramToJson(req);
		MdtApplyFeedback obj = JSONObject.parseObject(json.toJSONString(), MdtApplyFeedback.class);

		mdtApplyFeedbackService.save(obj);

		return new ResultInfo();
	}

	/**
	 * 查看反馈
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFeedback", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getFeedback(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {

		Long id = Long.parseLong(req.getParameter("id"));
		MdtApplyFeedback mdtApplyFeedback = mdtApplyFeedbackService.selectOne(id);

		return new ResultRowInfo(mdtApplyFeedback);
	}

	/**
	 * 删除反馈
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delFeedback", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delFeedback(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {

		Long id = Long.parseLong(req.getParameter("id"));
		mdtApplyFeedbackService.delete(id);

		return new ResultInfo();
	}

	/**
	 * 获取MdtApply主键
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMdtApplyKey", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getMdtTeamKey(HttpServletRequest req, HttpServletResponse rep) {
		Long key = baseKeyService.getKey("mdt_apply");
		return new ResultRowInfo(key);
	}

	/**
	 * 保存专家意见
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveApplyOpinion", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveApplyOpinion(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		MdtApplyOpinion obj = JSONObject.parseObject(json.toJSONString(), MdtApplyOpinion.class);
		mdtApplyOpinionService.save(obj);
		return new ResultInfo();
	}

	/**
	 * 查看专家意见
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getApplyOpinion", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getApplyOpinion(HttpServletRequest req, HttpServletResponse rep) {

		Long applyId = Long.parseLong(req.getParameter("applyId"));
		Long userId = Long.parseLong(req.getParameter("userId"));

		MdtApplyOpinion obj = mdtApplyOpinionService.getApplyOpinion(applyId, userId);

		return new ResultRowInfo(obj);
	}

	/**
	 * 查询专家意见
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllApplyOpinion", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getAllApplyOpinion(HttpServletRequest req, HttpServletResponse rep) {

		Long applyId = Long.parseLong(req.getParameter("applyId"));

		List<MdtApplyOpinion> obj = mdtApplyOpinionService.getApplyOpinion(applyId);

		return new ResultRowsInfo(obj);
	}

	/**
	 * 保存申请小结
	 * 
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveApplySummary", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveApplySummary(HttpServletRequest req, HttpServletResponse rep) {

		JSONObject json = RequestUtils.paramToJson(req);
		MdtApply obj = JSONObject.parseObject(json.toJSONString(), MdtApply.class);

		mdtApplyService.saveApplySummary(obj);

		return new ResultInfo();
	}

}
