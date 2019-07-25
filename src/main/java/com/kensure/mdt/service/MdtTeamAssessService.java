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
import com.kensure.mdt.dao.MdtTeamAssessMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtTeamAssess;

/**
 * MDT团队建设期满2年评估表服务实现类
 */
@Service
public class MdtTeamAssessService extends JSBaseService {

	@Resource
	private MdtTeamAssessMapper dao;
	@Resource
	private MdtTeamService mdtTeamService;
	@Resource
	private LCProcessService lCProcessService;

	private static final String table = "mdt_team_assess";

	public MdtTeamAssess selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<MdtTeamAssess> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtTeamAssess> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(MdtTeamAssess obj) {
		return dao.insert(obj);
	}

	public boolean update(MdtTeamAssess obj) {
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

	public MdtTeamAssess getTeamAssess(Long teamId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId);
		List<MdtTeamAssess> list = selectByWhere(parameters);

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 保存
	 * 
	 * @param obj
	 * @param user
	 */
	@Transactional
	public void save(MdtTeamAssess obj, AuthUser user) {
		if (obj.getId() == null) {
			initBase(obj, user);
			insert(obj);
		} else {
			update(obj);
		}
	}

	/**
	 * 填写第二年的目标
	 */
	@Transactional
	public void tianxie(MdtTeamAssess obj, AuthUser user) {
		save(obj, user);
		mdtTeamService.toAuditTwoYearAssess(obj.getTeamId());

		LCProcess process = lCProcessService.getProcessByBusi(obj.getTeamId(), table);
		lCProcessService.next(process.getId(), null, user);
	}

	/**
	 * 医务部审核第二年的目标
	 */
	@Transactional
	public void auditTwoYearAssess(MdtTeamAssess obj, AuthUser user) {
		update(obj);

		LCHistory yijian = obj.getYijian();
		LCProcess process = lCProcessService.getProcessByBusi(obj.getTeamId(), table);

		String content = yijian.getAuditOpinion();
		if (StringUtils.isBlank(content)) {
			content = yijian.getAuditResult() == -1 ? "不同意" : "同意";
		}
		// 退回走退回逻辑
		if (-1 == yijian.getAuditResult()) {
			// 退回给首席
			mdtTeamService.launchTwoYearAssess(obj.getTeamId());
			lCProcessService.back(process.getId(), content, user);
		} else {
			mdtTeamService.auditTwoYearAssess(obj.getTeamId());
			lCProcessService.next(process.getId(), content, user);
		}
	}

	/**
	 * 发起 MDT团队建设期满2年评估
	 * 
	 * @param teamId
	 */
	@Transactional
	public void faqi(Long teamId, AuthUser user) {
		LCProcess process = lCProcessService.getProcessByBusi(teamId, table);
		if (process == null) {
			process = lCProcessService.start(4L, user, teamId, table);
		}
		lCProcessService.next(process.getId(), null, user);

		mdtTeamService.launchTwoYearAssess(teamId);
	}
}
