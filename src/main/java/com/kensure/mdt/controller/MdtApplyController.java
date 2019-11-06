package com.kensure.mdt.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.kensure.frame.Const;
import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import co.kensure.frame.ResultRowsInfo;
import co.kensure.http.HttpUtils;
import co.kensure.http.RequestUtils;
import co.kensure.io.FileUtils;
import co.kensure.io.WriteExcelUtils;
import co.kensure.mem.DateUtils;
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
import com.kensure.mdt.entity.query.MdtApplyQuery;
import com.kensure.mdt.entity.resp.ExpertGradeList;
import com.kensure.mdt.rep.WsBingLi;
import com.kensure.mdt.service.MdtApplyDoctorService;
import com.kensure.mdt.service.MdtApplyFeedbackService;
import com.kensure.mdt.service.MdtApplyOpinionService;
import com.kensure.mdt.service.MdtApplyService;
import com.kensure.mdt.service.MdtGradeItemService;
import com.kensure.mdt.service.SysGradeService;
import com.kensure.mdt.service.WsPatientService;

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
	private WsPatientService wsPatientService;
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
		MdtApplyQuery query = JSONObject.parseObject(json.toJSONString(), MdtApplyQuery.class);
		AuthUser user = getCurrentUser(req);
		List<MdtApply> list = mdtApplyService.selectList(query, page, user);
		long cont = mdtApplyService.selectListCount(query, user);

		return new ResultRowsInfo(list, cont);
	}

	/**
	 * 导出报表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exportApply.do")
	@ResponseBody
	public void exportApply(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		JSONObject json = RequestUtils.paramToJson(req);
		PageInfo page = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
		page.setPageSize(1000000);
		MdtApplyQuery query = JSONObject.parseObject(json.toJSONString(), MdtApplyQuery.class);
		AuthUser user = getCurrentUser(req);
		List<MdtApply> list = mdtApplyService.selectList(query, page, user);

		List<String[]> dataList = new ArrayList<>();
		// excel标题
		String[] title = { "MDT申请时间", "MDT专家名单", "MDT申请人", "推荐人", "MDT地点", "MDT费用", "状态" };
		dataList.add(title);

		list.forEach(data -> {
			String[] content = new String[7];
			dataList.add(content);

			content[0] = DateUtils.format(data.getApplyCreatetime());
			List<String> names = new ArrayList<>();
			for (MdtApplyDoctor doc : data.getDoctors()) {
				names.add(doc.getName());
			}
			content[1] = StringUtils.join(names, ",");
			content[2] = data.getApplyPerson();
			content[3] = data.getTjusername();
			content[4] = data.getMdtLocation();
			content[5] = data.getFeiyong() + "";
			content[6] = data.getIsZjdafen() == 0 ? "进行" : "完成";
		});

		// 响应到客户端
		Workbook wb = null;
		OutputStream os = null;
		try {
			wb = WriteExcelUtils.writeExcel(dataList);
			// excel文件名
			String fileName = "MDT会诊统计.xls";
			HttpUtils.setResponseHeader(rep, fileName);
			os = rep.getOutputStream();
			wb.write(os);
			os.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
			FileUtils.close(os);
		}
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
		MdtApplyDoctor applydoctor = mdtApplyService.addApplyDoctorFormTeam(teamInfoId, applyId);
		return new ResultRowInfo(applydoctor);
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
		String type = json.getString("type");
		mdtApplyService.sendMsg(apply, type);
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
	 * 保存专家意见和下一步治疗方案
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
		AuthUser user = getCurrentUser(req);
		mdtApplyService.saveZJYiJian(apply, user);
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
	 * 删除apply
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.do", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delete(HttpServletRequest req, HttpServletResponse rep, MdtGradeReq mdtGradeReq) {
		Long id = Long.parseLong(req.getParameter("id"));
		mdtApplyService.delete(id);
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
	 * 保存申请小结,同时保存专家打分
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

	/**
	 * 保存打印缴费通知单
	 */
	@ResponseBody
	@RequestMapping(value = "saveApplyJiaofei", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveApplyJiaofei(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		Long applyId = json.getLong("applyId");
		mdtApplyService.saveJiaofei(applyId);
		return new ResultInfo();
	}

	/**
	 * mdt申请作废
	 */
	@ResponseBody
	@RequestMapping(value = "zuofei", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo zuofei(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = RequestUtils.paramToJson(req);
		Long id = json.getLong("id");
		mdtApplyService.saveZuofei(id);
		return new ResultInfo();
	}

	/**
	 * 病例列表
	 */
	@ResponseBody
	@RequestMapping(value = "bllist", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo bllist(HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json1 = RequestUtils.paramToJson(req);
		String code = json1.getString("code");
		List<WsBingLi> list = wsPatientService.getBLList(code);
		Map<String, String> map = DeptDictUtils.read(Const.ROOT_PATH + "/deptcode.txt");
		for (WsBingLi bl : list) {
			String deptcode = bl.getDEPT_CODE();
			String name = map.get(deptcode);
			if (StringUtils.isNotBlank(name)) {
				bl.setDEPT_CODE(name);
			}
		}
		return new ResultRowsInfo(list);
	}

}
