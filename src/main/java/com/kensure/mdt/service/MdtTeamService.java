package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.BaseInfo;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.DateUtils;
import co.kensure.mem.MapUtils;
import co.kensure.mem.NumberUtils;
import co.kensure.mem.PageInfo;

import com.kensure.lc.model.LCHistory;
import com.kensure.lc.model.LCProcess;
import com.kensure.lc.service.LCProcessService;
import com.kensure.mdt.dao.MdtTeamMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtTeam;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.MdtTeamObjective;
import com.kensure.mdt.entity.SysUser;
import com.kensure.mdt.entity.bo.MdtTeamYueDu;
import com.kensure.mdt.entity.query.MdtTeamPinGuQuery;
import com.kensure.mdt.entity.query.MdtTeamQuery;
import com.kensure.mdt.model.MdtTeamText;

/**
 * MDT团队表服务实现类
 */
@Service
public class MdtTeamService extends JSBaseService {

	@Resource
	private MdtTeamMapper dao;
	@Resource
	private MdtTeamObjectiveService mdtTeamObjectiveService;
	@Resource
	private MdtTeamInfoService mdtTeamInfoService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private LCProcessService lCProcessService;
	@Resource
	private MdtApplyService mdtApplyService;
	@Resource
	private MdtTeamTextService mdtTeamTextService;

	private static final String table = "mdt_team";

	public MdtTeam selectOne(Long id) {
		return dao.selectOne(id);
	}

	/**
	 * 获取详细的，包括成员
	 * 
	 * @param id
	 * @return
	 */
	public MdtTeam getDetail(Long id) {
		MdtTeam team = selectOne(id);
		List<MdtTeamInfo> menbers = mdtTeamInfoService.selectList(id);
		MdtTeamText text = mdtTeamTextService.selectOne(id);
		if (text != null) {
			team.setStandard(text.getStandard());
			team.setPlan(text.getPlan());
		}
		team.setMenbers(menbers);
		return team;
	}

	public List<MdtTeam> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtTeam> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(MdtTeam obj) {
		super.beforeInsert(obj);
		obj.setAnnualStatus("0");
		obj.setTwoYearStatus("0");
		if (StringUtils.isBlank(obj.getAuditStatus())) {
			obj.setAuditStatus("0");
		}
		MdtTeamText text = new MdtTeamText();
		text.setId(obj.getId());
		text.setStandard(obj.getStandard());
		text.setPlan(obj.getPlan());
		mdtTeamTextService.insert(text);
		return dao.insert(obj);
	}

	public boolean update(MdtTeam obj) {
		super.beforeUpdate(obj);
		if (StringUtils.isNotBlank(obj.getStandard())) {
			mdtTeamTextService.delete(obj.getId());
			MdtTeamText text = new MdtTeamText();
			text.setId(obj.getId());
			text.setStandard(obj.getStandard());
			text.setPlan(obj.getPlan());
			mdtTeamTextService.insert(text);
		}
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

	/**
	 * 保存、更新 MDT团队及团队建设责任目标
	 * 
	 * @param team
	 * @param mdtTeamObjective
	 */
	@Transactional
	public void save(MdtTeam team, MdtTeamObjective mdtTeamObjective, AuthUser user) {
		MdtTeam obj = selectOne(team.getId());
		List<MdtTeamInfo> menbers = mdtTeamInfoService.selectList(team.getId());
		//首席专家数量
		int type1 = 0;
		int type2 = 0;
		int type3 = 0;
		
		Set<String> ksset = new HashSet<>();
		for(MdtTeamInfo m:menbers){
			if("1".equals(m.getSpecialistType())){
				type1++;
			}else if("2".equals(m.getSpecialistType())){
				type2++;
			}else if("3".equals(m.getSpecialistType())){
				type3++;
			}
			ksset.add(m.getDepartment());
		}
		if(type1 != 1){
			BusinessExceptionUtil.threwException("必须且只能选择一个首席专家！");
		}
		if(type2 < 1){
			BusinessExceptionUtil.threwException("必须选择一个团队副组长！");
		}
		if(type3 < 1){
			BusinessExceptionUtil.threwException("必须选择一个团队秘书！");
		}
		if(ksset.size() < 3){
			BusinessExceptionUtil.threwException("包含的成员必须要来自三个科室以上！");
		}
		if(StringUtils.isBlank(team.getCreatedDeptid())){
			BusinessExceptionUtil.threwException("请选择所属科室！");
		}
	
		MdtTeamInfo de = menbers.get(0);
		SysUser sxzj = sysUserService.selectOne(de.getUserId());
		setBase(team, sxzj);

		// 新增
		if (obj == null) {
			if (team.getAuditStatus() == null) {
				team.setAuditStatus("0"); // 未审核
			}
			team.setApplyPersonId(user.getId());
			team.setApplyPerson(user.getName());
			team.setIsDelete("0"); // 未删除
			insert(team);
			mdtTeamObjective.setId(null);
			mdtTeamObjective.setTeamId(team.getId());
			mdtTeamObjective.setFlag("1"); // 第一年
			mdtTeamObjectiveService.save(mdtTeamObjective, user);
		} else {
			// 修改
			team.setCreatedUserid(obj.getCreatedUserid());
			update(team);
			// 更新前，先获取第一个设置的MDT团队目标，然后设置id，在进行更新
			MdtTeamObjective objective = mdtTeamObjectiveService.getFirstByTeamId(team.getId());
			mdtTeamObjective.setId(objective.getId());
			mdtTeamObjective.setTeamId(team.getId());
			mdtTeamObjectiveService.save(mdtTeamObjective, user);
		}

		// 发起申请
		if ("1".equals(team.getAuditStatus())) {
			LCProcess process = lCProcessService.getProcessByBusi(team.getId(), table);
			if (process == null) {
				process = lCProcessService.start(1L, user, team.getId(), table);
			}
			lCProcessService.next(process.getId(), null, user);
		}
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
	 * 条件分页查询
	 * 
	 * @param page
	 * @param query
	 * @return
	 */
	public List<MdtTeam> selectList(PageInfo page, MdtTeamQuery query, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		MapUtils.putPageInfo(parameters, page);
		String filterSql = getFilterSql(user);
		parameters.put("filterSql", filterSql);
		parameters.put("isDelete", "0");
		parameters.put("orderby", "date desc,id desc");
		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 权限比较复杂，只能使用注入sql的方式
	 */
	private String getFilterSql(AuthUser user) {
		String filterSql = " apply_person_id = " + user.getId();
		List<MdtTeamInfo> menbers = mdtTeamInfoService.selectByUserId(user.getId());
		if (CollectionUtils.isNotEmpty(menbers)) {
			filterSql += " or id in(";
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
	 * 条件分页查询
	 * 
	 * @param query
	 * @return
	 */
	public long selectListCount(MdtTeamQuery query, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		String filterSql = getFilterSql(user);
		parameters.put("filterSql", filterSql);
		parameters.put("isDelete", "0");
		return selectCountByWhere(parameters);
	}

	/**
	 * 查询所有已审核过的的MDT团队
	 * 
	 * @return
	 */
	public List<MdtTeam> findAllMdtTeam(AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap("auditStatus", "4");
		setOrgLevel(parameters, user);
		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 逻辑删除
	 * 
	 * @param id
	 */
	public void delMdtTeam(Long id) {
		MdtTeam team = new MdtTeam();
		team.setId(id);
		team.setIsDelete("1"); // 未删除
		update(team);
	}

	/**
	 * 审核
	 * 
	 * @param team
	 */
	@Transactional
	public void audit(MdtTeam team, AuthUser user) {
		// 意见入库
		LCHistory yijian = team.getYijian();
		MdtTeam old = selectOne(team.getId());

		LCProcess process = lCProcessService.getProcessByBusi(team.getId(), table);
		String opt = yijian.getAuditOpinion();
		if (StringUtils.isBlank(opt)) {
			if(-1 == yijian.getAuditResult()){
				BusinessExceptionUtil.threwException("请填写审核意见");
			}		
			opt = -1 == yijian.getAuditResult() ? "不同意" : "同意";
		}

		// 退回走退回逻辑
		if (-1 == yijian.getAuditResult()) {
			old.setAuditStatus("9");
			lCProcessService.back(process.getId(), opt, user);
		} else {
			// 下一步流程人
			if ("1".equals(old.getAuditStatus())) {
				old.setAuditStatus("2");
			} else if ("2".equals(old.getAuditStatus())) {
				old.setAuditStatus("3");
			} else if ("3".equals(old.getAuditStatus())) {
				old.setAuditStatus("4");
			}
			lCProcessService.next(process.getId(), opt, user);
		}
		update(old);
	}

	/**
	 * 查询MDT团队年度评估,一般是医务部的人用的
	 * 
	 * @param page
	 * @param query
	 * @return
	 */
	public List<MdtTeam> selectAnnualTeamList(PageInfo page, MdtTeamQuery query, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		setOrgLevel(parameters, user);
		MapUtils.putPageInfo(parameters, page);
		parameters.put("annualStatusIn", "0,1,2,3");
		parameters.put("isDelete", "0");
		parameters.put("orderby", "id desc");
		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 查询MDT团队年度评估,一般是医务部的人用的
	 * 
	 * @param query
	 * @return
	 */
	public long selectAnnualTeamCount(MdtTeamQuery query, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		setOrgLevel(parameters, user);
		parameters.put("annualStatusIn", "0,1,2,3");
		parameters.put("isDelete", "0");
		return selectCountByWhere(parameters);
	}

	/**
	 * 医务部发起MDT团队年度评估，推送给首席代办
	 */
	@Transactional
	public void launchAnnualAssess(Long teamId) {
		MdtTeam team = new MdtTeam();
		team.setId(teamId);
		team.setAnnualStatus("1");
		update(team);
	}

	/**
	 * 待审核 MDT团队年度评估
	 * 
	 * @param teamId
	 */
	public void toAuditAnnualAssess(Long teamId) {
		MdtTeam team = new MdtTeam();
		team.setId(teamId);
		team.setAnnualStatus("2");
		update(team);
	}

	/**
	 * 审核 MDT团队年度评估,状态更改
	 * 
	 * @param teamId
	 */
	public void auditAnnualAssess(Long teamId) {
		MdtTeam team = new MdtTeam();
		team.setId(teamId);
		team.setAnnualStatus("3");
		update(team);
	}

	/**
	 * 发起 MDT团队建设期满2年评估
	 * 
	 * @param teamId
	 */
	@Transactional
	public void launchTwoYearAssess(Long teamId) {
		MdtTeam team = new MdtTeam();
		team.setId(teamId);
		team.setTwoYearStatus("1");
		update(team);
	}

	/**
	 * 待审核 MDT团队建设期满2年评估
	 * 
	 * @param teamId
	 */
	public void toAuditTwoYearAssess(Long teamId) {
		MdtTeam team = new MdtTeam();
		team.setId(teamId);
		team.setTwoYearStatus("2");

		update(team);
	}

	/**
	 * 审核 MDT团队建设期满2年评估
	 * 
	 * @param teamId
	 * @param result
	 */
	public void auditTwoYearAssess(Long teamId) {
		MdtTeam team = new MdtTeam();
		team.setId(teamId);
		team.setTwoYearStatus("3");
		update(team);
	}

	/**
	 * MDT团队 月度指标完成情况红黄绿卡评估
	 * 
	 * @param page
	 * @param query
	 * @return
	 */
	public List<MdtTeam> selectListPinGu(PageInfo page, MdtTeamPinGuQuery query, AuthUser user) {
		Map<String, Object> parameters = pinguParam(query, user);
		MapUtils.putPageInfo(parameters, page);
		parameters.put("orderby", "date desc,id desc");
		List<MdtTeam> list = selectByWhere(parameters);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (MdtTeam team : list) {
			setPinGu(team, query,user);
		}
		return list;
	}

	private Map<String, Object> pinguParam(MdtTeamPinGuQuery query, AuthUser user){
		Map<String, Object> parameters = MapUtils.genMap("auditStatus", "4");
		parameters.put("isDelete", "0");
		setOrgLevel(parameters, user);
		
		if(StringUtils.isNotBlank(query.getNameLike())){
			parameters.put("nameLike", query.getNameLike());
		}
		//根据首席专家查找团队
		if(StringUtils.isNotBlank(query.getSxzj())){
			List<SysUser> userlist = sysUserService.selectByName(query.getSxzj(), user);
			//表示没有
			if(CollectionUtils.isEmpty(userlist)){
				parameters.put("createdUserid", -1000);	
				return parameters;
			}
			Set<Long> useridList = new HashSet<>();
			userlist.stream().forEach(x->useridList.add(x.getId()));
			//过滤出首席专家的团队
			List<MdtTeamInfo> infoList = mdtTeamInfoService.selectByUserIds(useridList, "1");
			//表示没有
			if(CollectionUtils.isEmpty(infoList)){
				parameters.put("createdUserid", -1000);		
				return parameters;
			}
			Set<Long> idList = new HashSet<>();
			infoList.stream().forEach(x->idList.add(x.getTeamId()));	
			parameters.put("idList", idList);	
		}
		return parameters;	
	}
	
	/**
	 * MDT团队 月度指标完成情况红黄绿卡评估
	 * 
	 * @param query
	 * @return
	 */
	public long selectListPinGuCount(MdtTeamPinGuQuery query, AuthUser user) {
		Map<String, Object> parameters = pinguParam(query, user);
		return selectCountByWhere(parameters);
	}

	/**
	 * 月度评估计算
	 * 
	 * @param team
	 * @param query
	 */
	private List<MdtTeamYueDu> setPinGu(MdtTeam team, MdtTeamPinGuQuery query, AuthUser authUser) {
		List<MdtTeamInfo> menbers = mdtTeamInfoService.selectList(team.getId());
		if (CollectionUtils.isNotEmpty(menbers)) {
			for (MdtTeamInfo mb : menbers) {
				if ("1".equals(mb.getSpecialistType())) {
					team.setSxzj(mb);
					SysUser user = sysUserService.selectOne(mb.getUserId());
					mb.setUser(user);
				}else if ("3".equals(mb.getSpecialistType())) {
					team.setTdms(mb);
					SysUser user = sysUserService.selectOne(mb.getUserId());
					mb.setUser(user);
				}
			}
		}	
		
		List<MdtTeamYueDu> list = new ArrayList<>();
		int startYear = query.getStartYear();
		int startMonth = query.getStartMonth();

		int endYear = query.getEndYear();
		int endMonth = query.getEndmonth();
		while ((startYear * 12 + startMonth) <= (endYear * 12 + endMonth)) {
			MdtTeamYueDu yuedu = pinGu(team, startYear, startMonth,authUser);
			list.add(yuedu);
			if (startMonth == 12) {
				startMonth = 1;
				startYear++;
			} else {
				startMonth++;
			}
		}
		team.setYueDuPinGuList(list);
		return list;
	}

	/**
	 * 两年考评需要的数据
	 * 
	 * @param team
	 * @param query
	 */
	public MdtTeam setTwoYearKaoPin(Long id,AuthUser user) {
		MdtTeam team = selectOne(id);
		team.setObjList(mdtTeamObjectiveService.getTeamObjectiveList(id));
		MdtTeamPinGuQuery query = new MdtTeamPinGuQuery();
		Date start = DateUtils.getPastMonth(team.getDate(), 1);
		Date end = DateUtils.getPastMonth(team.getDate(), 24);
		query.setStartYear(DateUtils.getYear(start));
		query.setStartMonth(DateUtils.getMonth(start));
		query.setEndYear(DateUtils.getYear(end));
		query.setEndmonth(DateUtils.getMonth(end));

		setPinGu(team, query,user);
		return team;
	}

	/**
	 * 月度评估计算
	 * 
	 * @param team
	 * @param query
	 */
	private MdtTeamYueDu pinGu(MdtTeam team, Integer year, Integer month, AuthUser user) {
		// 获取申请年月
		int yyyy = NumberUtils.parseInteger(DateUtils.format(team.getDate(), "yyyy"), null);
		int mm = NumberUtils.parseInteger(DateUtils.format(team.getDate(), "MM"), null);

		// 年度差值
		int yearcha = year - yyyy;
		int monthcha = month - mm;
		// 计算到底差了几个月
		int leijimonth = yearcha * 12 + monthcha;
		MdtTeamYueDu yuedu = new MdtTeamYueDu(year, month, 0L, 0L);
		if (leijimonth <= 0 || leijimonth > 24) {
			return yuedu;
		}
		// 第一年
		MdtTeamObjective teamobj = null;
		int i = 1;
		if (leijimonth <= 12) {
			teamobj = mdtTeamObjectiveService.getFirstByTeamId(team.getId());
			i = leijimonth;
		} else {
			// 第二年
			teamobj = mdtTeamObjectiveService.getSecondByTeamId(team.getId());
			i = leijimonth - 12;
		}

		Long zhibiao = 0L;
		if (teamobj != null) {
			try {
				zhibiao = (Long) PropertyUtils.getProperty(teamobj, "month" + i);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			Date startDiagnoseDate = DateUtils.parse(year + "-" + month + "-01", DateUtils.DAY_FORMAT);
			Date endDiagnoseDate = DateUtils.getPastMonth(startDiagnoseDate, 1);
			//专家打分为依据
			Map<String, Object> parameters = MapUtils.genMap("startDiagnoseDate", startDiagnoseDate, "endDiagnoseDate", endDiagnoseDate, "isZjdafen", 1, "teamId", teamobj.getTeamId());
			long count = mdtApplyService.selectCountByYueDu(parameters,user);
			yuedu.setTotal(zhibiao);
			yuedu.setNum(count);
		}
		return yuedu;
	}

}
