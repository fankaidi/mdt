package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;

import com.kensure.lc.model.LCHistory;
import com.kensure.lc.model.LCProcess;
import com.kensure.lc.service.LCProcessService;
import com.kensure.mdt.dao.MdtTeamObjectiveMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtTeamObjective;

/**
 * MDT团队建设责任目标服务实现类
 */
@Service
public class MdtTeamObjectiveService extends JSBaseService {

	@Resource
	private MdtTeamObjectiveMapper dao;
	@Resource
	private MdtTeamService mdtTeamService;
	@Resource
	private LCProcessService lCProcessService;
	private static final String table = "mdt_team_objective";

	public MdtTeamObjective selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<MdtTeamObjective> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtTeamObjective> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(MdtTeamObjective obj) {
		return dao.insert(obj);
	}

	public boolean update(MdtTeamObjective obj) {
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
	 * 保存，就是保存
	 * 
	 * @param teamObjective
	 * @param user
	 */
	@Transactional
	public void save(MdtTeamObjective teamObjective, AuthUser user) {
		if (teamObjective.getId() == null) {
			initBase(teamObjective, user);
			insert(teamObjective);
		} else {
			update(teamObjective);
		}
	}

	/**
	 * 填写第二年的目标
	 */
	@Transactional
	public void tianxie(MdtTeamObjective teamObjective, AuthUser user) {
		save(teamObjective, user);
		mdtTeamService.toAuditAnnualAssess(teamObjective.getTeamId());
		LCProcess process = lCProcessService.getProcessByBusi(teamObjective.getTeamId(), table);
		lCProcessService.next(process.getId(), null, user);
	}

	/**
	 * 医务部审核第二年的目标
	 */
	@Transactional
	public void auditAnnualAssess(MdtTeamObjective teamObjective, AuthUser user) {
		update(teamObjective);
		
		LCHistory yijian = teamObjective.getYijian();
		LCProcess process = lCProcessService.getProcessByBusi(teamObjective.getTeamId(), table);	
		
		String content = yijian.getAuditOpinion();
		if(StringUtils.isBlank(content)){
			content = yijian.getAuditResult()==-1?"不同意":"同意";
		}
		// 退回走退回逻辑
		if (-1 == yijian.getAuditResult()) {
			// 退回给首席
			mdtTeamService.launchAnnualAssess(teamObjective.getTeamId());
			lCProcessService.back(process.getId(), content, user);
		} else {
			mdtTeamService.auditAnnualAssess(teamObjective.getTeamId());
			lCProcessService.next(process.getId(), content, user);
		}	
	
	}

	/**
	 * 获取第一个设置的MDT团队目标
	 * 
	 * @param teamId
	 * @return
	 */
	public MdtTeamObjective getFirstByTeamId(Long teamId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId, "flag", "1");
		List<MdtTeamObjective> list = selectByWhere(parameters);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取第二个设置的MDT团队目标
	 * 
	 * @param teamId
	 * @return
	 */
	public MdtTeamObjective getSecondByTeamId(Long teamId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId, "flag", "2");
		List<MdtTeamObjective> list = selectByWhere(parameters);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取团队下所有建设目标
	 * 
	 * @param teamId
	 * @return
	 */
	public List<MdtTeamObjective> getTeamObjectiveList(Long teamId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId, "orderby", "id");
		List<MdtTeamObjective> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 发起团队年度评估
	 * 
	 * @param teamId
	 */
	public void faqi(Long teamId, AuthUser user) {
		LCProcess process = lCProcessService.getProcessByBusi(teamId, table);
		if(process == null){
			process = lCProcessService.start(3L, user, teamId, table);
		}	
		lCProcessService.next(process.getId(), null, user);
		mdtTeamService.launchAnnualAssess(teamId);
	}
}
