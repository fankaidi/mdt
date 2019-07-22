package com.kensure.mdt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.mdt.dao.MdtApplyMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtApply;
import com.kensure.mdt.entity.MdtApplyDoctor;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.SysMsgTemplate;
import com.kensure.mdt.entity.SysRole;
import com.kensure.mdt.entity.SysUser;
import com.kensure.mdt.entity.SysUserRole;
import com.kensure.mdt.lc.model.LCDaiBan;
import com.kensure.mdt.lc.model.LCHistory;
import com.kensure.mdt.lc.service.LCDaiBanService;
import com.kensure.mdt.lc.service.LCHistoryService;

/**
 * MDT申请表服务实现类
 * 
 * @author fankd created on 2019-6-25
 * @since
 */
@Service
public class MdtApplyService extends JSBaseService{

	@Resource
	private MdtApplyMapper dao;

	@Resource
	private MdtTeamInfoService mdtTeamInfoService;

	@Resource
	private MdtApplyDoctorService mdtApplyDoctorService;

	@Resource
	private SysFeeService sysFeeService;

	@Resource
	private SysMsgTemplateService sysMsgTemplateService;
	@Resource
	private LCDaiBanService lCDaiBanService;
	@Resource
	private LCHistoryService lCHistoryService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private MdtGradeItemService mdtGradeItemService;
	@Resource
	private MdtApplyFeedbackService mdtApplyFeedbackService;
	@Resource
	private MdtApplyOpinionService mdtApplyOpinionService;


	private static final String table = "mdt_apply";

	public MdtApply selectOne(Long id) {
		return dao.selectOne(id);
	}

	public MdtApply getDetail(Long id) {
		MdtApply app = selectOne(id);
		List<LCHistory> lCHistoryList = lCHistoryService.selectByBusiid(table, id);
		app.setlCHistoryList(lCHistoryList);
		app.setDoctors(mdtApplyDoctorService.getDetailByApplyId(id));
		;
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

	public boolean insert(MdtApply obj) {
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

		MdtApply obj = selectOne(apply.getId());
		if (obj == null) {
			if (StringUtils.isBlank(apply.getApplyStatus())) {
				apply.setApplyStatus("0"); // "申请人申请" 状态
			}
			apply.setShare("0"); // "分享" 状态
			apply.setIsDelete("0");
			initBase(apply, user);
			insert(apply);
		} else {
			update(apply);
		}
		// 发起申请
		if ("1".equals(apply.getApplyStatus())) {
			// 住院
			if ("1".equals(apply.getPatientType())) {
				// 获取科室主任
				List<SysUser> userlist = sysUserService.selectKSZR(apply.getApplyPersonId().longValue());
				if (CollectionUtils.isEmpty(userlist)) {
					BusinessExceptionUtil.threwException("找不到对应的科室主任");
				}
				List<LCDaiBan> daibanlist = new ArrayList<>();
				for (SysUser kszruser : userlist) {
					LCDaiBan daiban = new LCDaiBan();
					daiban.setApplyPersonId(apply.getApplyPersonId());
					daiban.setBisiid(apply.getId());
					daiban.setEntryName("科主任审核");
					daiban.setTitle(apply.getName());
					daiban.setBusitype(table);
					daiban.setUserid(kszruser.getId().intValue());
					daibanlist.add(daiban);
				}
				lCDaiBanService.liucheng(daibanlist, apply.getId(), table);
			} else {
				// 门诊没有流程，直接到缴费
				apply.setApplyStatus("11");
				update(apply);
			}
		}
	}

	public List<MdtApply> selectList(PageInfo page, AuthUser user) {
		List<Long> idList = daibanList(user);
		Map<String, Object> parameters = MapUtils.genMap("isManager", 1, "applyPersonId1", user.getId(), "idList", idList);
		setOrgLevel(parameters, user);
		MapUtils.putPageInfo(parameters, page);
		List<MdtApply> list = selectByWhere(parameters);

		if (CollectionUtils.isNotEmpty(idList)) {
			for (MdtApply app : list) {
				if (idList.contains(app.getId())) {
					app.setIsSp(1);
				}
			}
		}
		return list;
	}

	public long selectListCount(AuthUser user) {
		List<Long> idList = daibanList(user);
		Map<String, Object> parameters = MapUtils.genMap("isManager", 1, "applyPersonId1", user.getId(), "idList", idList);
		setOrgLevel(parameters, user);
		return selectCountByWhere(parameters);
	}

	private List<Long> daibanList(AuthUser user) {
		List<LCDaiBan> list = lCDaiBanService.getUserDaiBanByBusitype(user.getId(), table);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Long> idList = new ArrayList<>();
		for (LCDaiBan daiban : list) {
			idList.add(daiban.getBisiid());
		}
		return idList;
	}

	/**
	 * 从MDT团队中将专家选入MDT专家库
	 * 
	 * @param teamInfoId
	 * @param applyId
	 */
	public void addApplyDoctorFormTeam(Long teamInfoId, Long applyId) {

		MdtTeamInfo mdtTeamInfo = mdtTeamInfoService.selectOne(teamInfoId);

		mdtApplyDoctorService.addApplyDoctor(applyId, mdtTeamInfo);
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
		// 科室领导审批
		yijian.setBisiid(apply.getId());
		yijian.setBusitype(table);
		yijian.setUserid(user.getId());
		if ("1".equals(old.getApplyStatus())) {
			yijian.setEntryName("科主任审核");
		} else if ("2".equals(old.getApplyStatus())) {
			yijian.setEntryName("医务部主任审核");
		}
		lCHistoryService.insert(yijian);

		// 退回走退回逻辑
		if (-1 == yijian.getAuditResult()) {
			old.setApplyStatus("9");
			back(old);
		} else {
			// 下一步流程人
			if ("1".equals(old.getApplyStatus())) {
				old.setApplyStatus("2");
				// 获取医务部主任
				SysRole role = sysRoleService.selectByCode("ywbzr", user.getCreatedOrgid());	
				List<SysUserRole> userlist = sysUserRoleService.selectByRoleId(role.getId());
				if (CollectionUtils.isEmpty(userlist)) {
					BusinessExceptionUtil.threwException("找不到对应的医务部主任");
				}
				List<LCDaiBan> daibanlist = new ArrayList<>();
				for (SysUserRole kszruser : userlist) {
					LCDaiBan daiban = new LCDaiBan();
					daiban.setApplyPersonId(old.getApplyPersonId());
					daiban.setBisiid(old.getId());
					daiban.setEntryName("医务部主任审核");
					daiban.setTitle(old.getName());
					daiban.setBusitype(table);
					daiban.setUserid(kszruser.getUserId().intValue());
					daibanlist.add(daiban);
				}
				lCDaiBanService.liucheng(daibanlist, old.getId(), table);
			} else if ("2".equals(old.getApplyStatus())) {
				old.setApplyStatus("11");
				lCDaiBanService.liucheng(null, old.getId(), table);
			}
		}
		update(old);
	}

	/**
	 * 退回逻辑
	 */
	public void back(MdtApply old) {
		List<LCDaiBan> daibanlist = new ArrayList<>();
		LCDaiBan daiban = new LCDaiBan();
		daiban.setApplyPersonId(old.getApplyPersonId());
		daiban.setBisiid(old.getId());
		daiban.setEntryName("申请人申请");
		daiban.setTitle(old.getName());
		daiban.setBusitype(table);
		daiban.setUserid(old.getApplyPersonId());
		daibanlist.add(daiban);
		lCDaiBanService.liucheng(daibanlist, old.getId(), table);
	}

	/**
	 * 送MDT短信
	 * 
	 * @param apply
	 */
	public void sendMsg(MdtApply apply) {

		List<MdtApplyDoctor> mdtApplyDoctors = mdtApplyDoctorService.selectByApplyId(apply.getId());

		SysMsgTemplate template = sysMsgTemplateService.getMsgTemplate("1");
		String content = template.getContent();

		for (MdtApplyDoctor mdtApplyDoctor : mdtApplyDoctors) {

			String phone = mdtApplyDoctor.getPhone();

			// System.out.println(apply.getMdtDate());
			// System.out.println(apply.getMdtLocation());

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String mdtDate = sf.format(apply.getMdtDate());

			content = content.replace("｛专家名字｝", mdtApplyDoctor.getName());
			content = content.replace("｛MDT名称｝", apply.getDiseaseName());
			content = content.replace("｛MDT时间｝", mdtDate);
			content = content.replace("｛MDT申请地点｝", apply.getMdtLocation());

			System.out.println(phone);
			System.out.println(content);
		}
	}

	/**
	 * 计算费用
	 * 
	 * @param applyId
	 */
	public Long calculateFee(Long applyId) {

		List<MdtApplyDoctor> mdtApplyDoctors = mdtApplyDoctorService.selectByApplyId(applyId);

		Set<String> departments = new TreeSet<>();

		for (MdtApplyDoctor mdtApplyDoctor : mdtApplyDoctors) {
			String department = mdtApplyDoctor.getDepartment();

			departments.add(department);
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

	public void saveApplySummary(MdtApply obj) {
		update(obj);
	}

	/**
	 * 代办审核的
	 * 
	 * @return
	 */
	public List<MdtApply> doSth(AuthUser user) {

		Map<String, Object> parameters = MapUtils.genMap();

		String applyStatus = "";

		if (user.getRoleIds().contains("5")) {
			applyStatus = "1";
		} else if (user.getRoleIds().contains("3")) {
			applyStatus = "2";
		} else if (user.getRoleIds().contains("7")) {
			applyStatus = "9";
		}

		parameters.put("applyStatus", applyStatus);
		parameters.put("isDelete", "0");

		List<MdtApply> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 保存科室打分和意见
	 * 
	 * @return
	 */
	public void saveKSPinFen(MdtApply apply) {
		mdtGradeItemService.saveDeptGrade(apply);
		mdtApplyOpinionService.saveZJYJ(apply);

	}

	/**
	 * 录入专家的打分
	 * 
	 * @return
	 */
	public void saveZJPinFen(MdtApply apply) {
		mdtGradeItemService.saveLRZJPF(apply);
		apply.setApplyStatus("13");
		update(apply);
	}
}
