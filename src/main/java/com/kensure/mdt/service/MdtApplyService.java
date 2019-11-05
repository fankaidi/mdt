package com.kensure.mdt.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.BaseInfo;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.DateUtils;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.lc.model.LCHistory;
import com.kensure.lc.model.LCProcess;
import com.kensure.lc.service.LCHistoryService;
import com.kensure.lc.service.LCProcessService;
import com.kensure.mdt.dao.MdtApplyMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtApply;
import com.kensure.mdt.entity.MdtApplyDoctor;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.SysMsgTemplate;
import com.kensure.mdt.entity.SysOrg;
import com.kensure.mdt.entity.SysPatient;
import com.kensure.mdt.entity.SysUser;
import com.kensure.mdt.entity.query.MdtApplyQuery;
import com.kensure.mdt.model.MdtApplyText;

/**
 * MDT申请表服务实现类
 * 
 * @author fankd created on 2019-6-25
 * @since
 */
@Service
public class MdtApplyService extends JSBaseService {

	@Resource
	private MdtApplyMapper dao;
	@Resource
	private MdtTeamInfoService mdtTeamInfoService;
	@Resource
	private MdtApplyDoctorService mdtApplyDoctorService;
	@Resource
	private SysFeeService sysFeeService;
	@Resource
	private SysPatientService sysPatientService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysMsgTemplateService sysMsgTemplateService;
	@Resource
	private LCHistoryService lCHistoryService;
	@Resource
	private LCProcessService lCProcessService;
	@Resource
	private MdtGradeItemService mdtGradeItemService;
	@Resource
	private MdtApplyFeedbackService mdtApplyFeedbackService;
	@Resource
	private MdtApplyOpinionService mdtApplyOpinionService;
	@Resource
	private MdtApplyTextService mdtApplyTextService;
	@Resource
	private SmsService smsService;

	private static final String table = "mdt_apply";

	public MdtApply selectOne(Long id) {
		return dao.selectOne(id);
	}

	public MdtApply getDetail(Long id) {
		MdtApply app = selectOne(id);
		List<LCHistory> lCHistoryList = lCHistoryService.selectByBusiid(table, id);
		app.setlCHistoryList(lCHistoryList);
		app.setDoctors(mdtApplyDoctorService.getDetailByApplyId(id));
		app.setPatientUser(sysPatientService.selectOne(app.getPatientId()));
		MdtApplyText text = mdtApplyTextService.selectOne(id);
		String zjyj = text == null ? "" : text.getZjyj();
		app.setZjyj(zjyj);
		return app;
	}

	public List<MdtApply> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtApply> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public long selectCountByYueDu(Map<String, Object> parameters, AuthUser user) {
		setOrgLevel(parameters, user);
		return selectCountByWhere(parameters);
	}

	/**
	 * 完成例次
	 * 
	 * @return
	 */
	public long selectCountLici(Long teamId, AuthUser user) {
		// 专家打分为依据
		Map<String, Object> parameters = MapUtils.genMap("isZjdafen", 1, "teamId", teamId);
		setOrgLevel(parameters, user);
		long count = selectCountByYueDu(parameters, user);
		return count;
	}

	public boolean insert(MdtApply obj) {
		obj.setIsDuanxin(0);
		obj.setIsJiaofei(0);
		obj.setIsZuofei(0);
		obj.setIsZjdafen(0);
		obj.setIsKsdafen(0);
		obj.setIsXiaojie(0);
		obj.setIsZhiqing(0);
		obj.setShare("0"); // "分享" 状态
		obj.setIsDelete("0");
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean update(MdtApply obj) {
		super.beforeUpdate(obj);
		return dao.update(obj);
	}

	public boolean updateByMap(Map<String, Object> params) {
		return dao.updateByMap(params);
	}

	public boolean delete(Long id) {
		return dao.delete(id);
	}

	public boolean deleteMulti(Collection<Long> ids) {
		return dao.deleteMulti(ids);
	}

	public boolean deleteByWhere(Map<String, Object> parameters) {
		return dao.deleteByWhere(parameters);
	}

	public void save(MdtApply apply, AuthUser user) {
		Long teamId = mdtApplyDoctorService.getTeamIdByApplyId(apply.getId());
		List<MdtTeamInfo> menbers = mdtTeamInfoService.selectSxzjList(teamId);
		// 首席专家
		MdtTeamInfo de = menbers.get(0);
		SysUser sxzj = sysUserService.selectOne(de.getUserId());
		apply.setTeamId(teamId);
		if (StringUtils.isBlank(apply.getCreatedDeptid())) {
			BusinessExceptionUtil.threwException("请选择所属科室！");
		}
		if (apply.getDezl() == null) {
			BusinessExceptionUtil.threwException("请选择第二诊疗！");
		}
		setBase(apply, sxzj);

		MdtApply obj = selectOne(apply.getId());
		if (obj == null) {
			if (apply.getApplyStatus() == null) {
				apply.setApplyStatus(0); // "申请人申请" 状态
			}
			insert(apply);
		} else {
			update(apply);
		}

		// 发起申请
		if (1 == apply.getApplyStatus()) {
			// 住院
			if ("1".equals(apply.getPatientType())) {
				LCProcess process = lCProcessService.getProcessByBusi(apply.getId(), table);
				if (process == null) {
					process = lCProcessService.start(2L, user, apply.getId(), table);
				}
				lCProcessService.next(process.getId(), null, user);
			} else {
				// 门诊没有流程，直接到缴费
				apply.setApplyStatus(11);
				update(apply);
			}
		}
		// 计算费用
		apply.setFeiyong(calculateFee(apply.getId()).doubleValue());
		update(apply);
	}

	/**
	 * 设置基础数据
	 */
	public static void setBase(BaseInfo obj, SysUser sxzj) {
		if (obj.getCreatedUserid() == null) {
			obj.setCreatedUserid(sxzj.getId());
		}
		if (obj.getCreatedOrgid() == null) {
			obj.setCreatedOrgid(sxzj.getCreatedOrgid());
		}
	}

	/**
	 * 权限比较复杂，只能使用注入sql的方式
	 */
	private String getFilterSql(AuthUser user) {
		String filterSql = " apply_person_id = " + user.getId();
		List<MdtTeamInfo> menbers = mdtTeamInfoService.selectByUserId(user.getId());
		if (CollectionUtils.isNotEmpty(menbers)) {
			filterSql += " or team_id in(";
			Set<Long> teamidlist = new HashSet<>();
			for (MdtTeamInfo mb : menbers) {
				teamidlist.add(mb.getTeamId());
			}
			filterSql += StringUtils.join(teamidlist, ",") + ") ";
		}

		if (user.getRoleLevel() == 1) {
			filterSql += " or 1=1 ";
		} else if (user.getRoleLevel() == 2) {
			// 园区级别
			filterSql += " or created_orgid= " + user.getCreatedOrgid();
		} else if (user.getRoleLevel() == 4) {
			// 科室级别
			filterSql += " or created_deptid in ( " + StringUtils.join(user.getDeptIdList(), ",") + ") ";
		} else {
			// 个人级别
			filterSql += " or created_userid= " + user.getId();
		}
		return filterSql;
	}

	/**
	 * 申请管理页面
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public List<MdtApply> selectList(MdtApplyQuery query, PageInfo page, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		parameters.put("orderby", "apply_createtime desc");
		if (query.getOrgLevel() == null || query.getOrgLevel() == 0) {
			String filterSql = getFilterSql(user);
			parameters.put("filterSql", filterSql);
		} else {
			setOrgLevel(parameters, user);
		}

		MapUtils.putPageInfo(parameters, page);
		List<MdtApply> list = selectByWhere(parameters);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (MdtApply apply : list) {
			SysOrg dept = sysOrgService.selectOne(apply.getCreatedDeptid());
			apply.setDept(dept);
			SysUser applyUser = sysUserService.selectOne(apply.getCreatedUserid());
			apply.setApplyUser(applyUser);
			apply.setDoctors(mdtApplyDoctorService.getDetailByApplyId(apply.getId()));
		}
		return list;
	}

	public long selectListCount(MdtApplyQuery query, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		if (query.getOrgLevel() == null || query.getOrgLevel() == 0) {
			String filterSql = getFilterSql(user);
			parameters.put("filterSql", filterSql);
		} else {
			setOrgLevel(parameters, user);
		}
		return selectCountByWhere(parameters);
	}

	/**
	 * 从MDT团队中将专家选入MDT专家库
	 * 
	 * @param teamInfoId
	 * @param applyId
	 */
	public MdtApplyDoctor addApplyDoctorFormTeam(Long teamInfoId, Long applyId) {
		MdtTeamInfo mdtTeamInfo = mdtTeamInfoService.selectOne(teamInfoId);
		return mdtApplyDoctorService.addApplyDoctor(applyId, mdtTeamInfo);
	}

	/**
	 * 审核
	 * 
	 * @param apply
	 */
	public void audit(MdtApply apply, AuthUser user) {
		// 意见入库
		LCHistory yijian = apply.getYijian();
		MdtApply old = selectOne(apply.getId());

		LCProcess process = lCProcessService.getProcessByBusi(apply.getId(), table);
		String opt = yijian.getAuditOpinion();
		if (StringUtils.isBlank(opt)) {
			if (-1 == yijian.getAuditResult()) {
				BusinessExceptionUtil.threwException("请填写审核意见");
			}
			opt = -1 == yijian.getAuditResult() ? "不同意" : "同意";
		}
		// 退回走退回逻辑
		if (-1 == yijian.getAuditResult()) {
			old.setApplyStatus(9);
			lCProcessService.back(process.getId(), opt, user);
		} else {
			// 下一步流程人
			if (1 == old.getApplyStatus()) {
				old.setApplyStatus(2);
			} else if (2 == old.getApplyStatus()) {
				old.setApplyStatus(11);
			}
			lCProcessService.next(process.getId(), opt, user);
		}
		update(old);
	}

	/**
	 * 送MDT短信
	 * 
	 * @param apply
	 */
	public void sendMsg(MdtApply apply, String type) {
		MdtApply old = selectOne(apply.getId());
		old.setMdtDate(apply.getMdtDate());
		old.setMdtLocation(apply.getMdtLocation());
		old.setIsDuanxin(1);
		if (old.getIsZhiqing() == 1 && old.getApplyStatus() < 13) {
			old.setApplyStatus(13);
		}
		update(old);
		sendMsgContent(old, type);
	}

	private void sendMsgContent(MdtApply apply, String tempid) {
		List<MdtApplyDoctor> mdtApplyDoctors = mdtApplyDoctorService.selectByApplyId(apply.getId());
		SysMsgTemplate template = sysMsgTemplateService.getMsgTemplate(tempid);
		for (MdtApplyDoctor mdtApplyDoctor : mdtApplyDoctors) {
			String content = template.getContent();
			String phone = mdtApplyDoctor.getPhone();
			String mdtDate = DateUtils.format(apply.getMdtDate());
			// content = content.replace("｛专家名字｝", mdtApplyDoctor.getName());
			content = content.replace("｛MDT名称｝", apply.getName());
			content = content.replace("｛MDT时间｝", mdtDate);
			content = content.replace("｛MDT申请地点｝", apply.getMdtLocation());
			System.out.println(phone);
			System.out.println(content);
			if (StringUtils.isNotBlank(phone) && phone.length() == 11) {
				smsService.sendSms(phone, content);
			}
		}
	}

	/**
	 * 计算费用
	 * 
	 * @param applyId
	 */
	public Long calculateFee(Long applyId) {
		MdtApply apply = selectOne(applyId);
		if ("0".equals(apply.getIsCharge())) {
			return 0L;
		}

		List<MdtApplyDoctor> mdtApplyDoctors = mdtApplyDoctorService.selectByApplyId(applyId);
		Set<String> departments = new TreeSet<>();
		for (MdtApplyDoctor mdtApplyDoctor : mdtApplyDoctors) {
			if (mdtApplyDoctor.getDepartment() != null) {
				departments.add(mdtApplyDoctor.getDepartment());
			}
		}
		int departmentNum = departments.size();
		Long price = sysFeeService.calculateFee(departmentNum);
		return price;

	}

	/**
	 * 开启分享病例
	 * 
	 * @param applyId
	 */
	public void share(Long applyId) {
		MdtApply apply = new MdtApply();
		apply.setId(applyId);
		apply.setShare("1");
		update(apply);
	}

	/**
	 * 保存小结和科室打分
	 * 
	 * @param obj
	 */
	public void saveApplySummary(MdtApply obj) {
		mdtGradeItemService.saveDeptGrade(obj);
		obj.setIsXiaojie(1);
		update(obj);
	}

	/**
	 * 保存专家治疗方案和后续治疗方案 <br>
	 * 状态进入会诊意见书
	 * 
	 * @return
	 */
	@Transactional
	public void saveZJYiJian(MdtApply apply,AuthUser user) {
		apply.setIsKsdafen(1);
		apply.setJlrid(user.getId().intValue());
		apply.setJlrname(user.getName());	
		update(apply);
		MdtApplyText text = mdtApplyTextService.selectOne(apply.getId());
		if (text == null) {
			text = new MdtApplyText();
			text.setId(apply.getId());
			text.setZjyj(apply.getZjyj());
			mdtApplyTextService.insert(text);
		} else {
			text.setZjyj(apply.getZjyj());
			mdtApplyTextService.update(text);
		}
	}

	/**
	 * 录入专家的打分
	 * 
	 * @return
	 */
	public void saveZJPinFen(MdtApply apply) {
		mdtGradeItemService.saveLRZJPF(apply);
		MdtApply old = selectOne(apply.getId());
		if (old.getApplyStatus() < 15) {
			old.setApplyStatus(15);
		}
		old.setIsZjdafen(1);
		update(old);
	}

	/**
	 * 保存知情通知书
	 * 
	 * @return
	 */
	public void saveDaYinZhiQing(Long applyId, String medicalNo) {
		MdtApply apply = selectOne(applyId);
		apply.setIsZhiqing(1);
		if (apply.getApplyStatus() < 13) {
			apply.setApplyStatus(13);
		}
		if ("2".equals(apply.getPatientType()) && StringUtils.isBlank(medicalNo)) {
			BusinessExceptionUtil.threwException("请填写门诊号");
		}
		// 门诊号发生变更
		if ("2".equals(apply.getPatientType()) && StringUtils.isNotBlank(medicalNo) && !medicalNo.equals(apply.getNumber())) {
			apply.setNumber(medicalNo);
			// 同步资料
			if (apply.getPatientId() != null) {
				SysPatient pa = sysPatientService.selectOne(apply.getPatientId());
				pa.setMedicalNo(medicalNo);
				sysPatientService.update(pa);
				
				sysPatientService.saveBingli(apply.getPatientId());
				SysPatient user = sysPatientService.selectOne(apply.getPatientId());
				if (apply.getOverview() == null || apply.getOverview().length() < 50) {
					String overview = "病史：\n" + (user.getMedicalHistory() != null ? user.getMedicalHistory() : "") + "\n\n\n体检：" + (user.getMedicalExam() != null ? user.getMedicalExam() : "") + "\n\n\n处理："
							+ (user.getDispose() != null ? user.getDispose() : "") + "\n\n\n初步诊断：" + (user.getPrimaryDiagnosis() != null ? user.getPrimaryDiagnosis() : "");
					apply.setOverview(overview);
				}
			}
		}
		update(apply);
	}

	/**
	 * 保存打印缴费通知单
	 * 
	 * @return
	 */
	public void saveJiaofei(Long applyId) {
		MdtApply apply = selectOne(applyId);
		apply.setIsJiaofei(1);
		update(apply);
	}

	/**
	 * 病人不愿意进行会诊，把申请作废
	 * 
	 * @return
	 */
	public void saveZuofei(Long applyId) {
		MdtApply apply = selectOne(applyId);
		apply.setIsZuofei(1);
		apply.setApplyStatus(-1);
		update(apply);
	}

	/**
	 * 反馈之后完成了
	 * 
	 * @return
	 */
	public void saveFankui(Long applyId) {
		MdtApply apply = selectOne(applyId);
		apply.setApplyStatus(19);
		update(apply);
	}

}
