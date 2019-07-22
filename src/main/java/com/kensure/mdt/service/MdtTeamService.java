package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.lc.model.LCHistory;
import com.kensure.lc.model.LCProcess;
import com.kensure.lc.service.LCProcessService;
import com.kensure.mdt.dao.MdtTeamMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtTeam;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.MdtTeamObjective;
import com.kensure.mdt.entity.query.MdtTeamQuery;

/**
 * MDT团队表服务实现类
 */
@Service
public class MdtTeamService extends JSBaseService{

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

	private static final String table = "mdt_team";

	public MdtTeam selectOne(Long id) {
		return dao.selectOne(id);
	}
	
	/**
	 * 获取详细的，包括成员
	 * @param id
	 * @return
	 */
	public MdtTeam getDetail(Long id) {
		MdtTeam team = selectOne(id);
		List<MdtTeamInfo> menbers = mdtTeamInfoService.selectList(id);
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
		obj.setAnnualStatus("0");
		obj.setTwoYearStatus("0");
		if (StringUtils.isBlank(obj.getAuditStatus())) {
			obj.setAuditStatus("0");
		}
		return dao.insert(obj);
	}

	public boolean update(MdtTeam obj) {
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

	/**
	 * 保存、更新 MDT团队及团队建设责任目标
	 * 
	 * @param team
	 * @param mdtTeamObjective
	 */
	@Transactional
	public void save(MdtTeam team, MdtTeamObjective mdtTeamObjective, AuthUser user) {
		MdtTeam obj = selectOne(team.getId());
		// 新增
		if (obj == null) {
			if (team.getAuditStatus() == null) {
				team.setAuditStatus("0"); // 未审核
			}
			team.setIsDelete("0"); // 未删除
			initBase(team, user);
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
			if(process == null){
				process = lCProcessService.start(1L, user, team.getId(), table);
			}	
			lCProcessService.next(process.getId(), null, user);
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
		setOrgLevel(parameters, user);
		parameters.put("isDelete", "0");
		parameters.put("createUserid", user.getId());
		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 条件分页查询
	 * 
	 * @param query
	 * @return
	 */
	public long selectListCount(MdtTeamQuery query, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		setOrgLevel(parameters, user);
		parameters.put("isDelete", "0");
		parameters.put("createUserid", user.getId());
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
		if(StringUtils.isBlank(opt)){
			opt = -1 == yijian.getAuditResult()?"不同意":"同意";
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
	public List<MdtTeam> selectAnnualTeamList(PageInfo page, MdtTeamQuery query,AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		setOrgLevel(parameters, user);
		MapUtils.putPageInfo(parameters, page);
		parameters.put("annualStatusIn", "0,1,2,3");
		parameters.put("isDelete", "0");
		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}
	
	/**
	 * 查询MDT团队年度评估,一般是医务部的人用的
	 * 
	 * @param query
	 * @return
	 */
	public long selectAnnualTeamCount(MdtTeamQuery query,AuthUser user) {
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
	 * 代办审核的
	 * 
	 * @return
	 */
	public List<MdtTeam> doSth(AuthUser user) {

		Map<String, Object> parameters = MapUtils.genMap();

		String auditStatus = "";

		if (user.getRoleIds().contains("5")) {
			auditStatus = "1";
		} else if (user.getRoleIds().contains("3")) {
			auditStatus = "2";
		} else if (user.getRoleIds().contains("2")) {
			auditStatus = "3";
		} else if (user.getRoleIds().contains("7")) {
			auditStatus = "9";
		}

		parameters.put("auditStatus", auditStatus);
		parameters.put("isDelete", "0");

		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 团队年度 代办审核的
	 * 
	 * @return
	 */
	public List<MdtTeam> doSth2(AuthUser user) {

		Map<String, Object> parameters = MapUtils.genMap();

		String annualStatus = "";

		if (user.getRoleIds().contains("3")) {
			annualStatus = "2";
		}

		parameters.put("annualStatus", annualStatus);
		parameters.put("isDelete", "0");

		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 团队两年度 代办审核的
	 * 
	 * @return
	 */
	public List<MdtTeam> doSth3(AuthUser user) {

		Map<String, Object> parameters = MapUtils.genMap();

		String twoYearStatus = "";

		if (user.getRoleIds().contains("3")) {
			twoYearStatus = "2";
		}

		parameters.put("twoYearStatus", twoYearStatus);
		parameters.put("isDelete", "0");

		List<MdtTeam> list = selectByWhere(parameters);
		return list;
	}
}
