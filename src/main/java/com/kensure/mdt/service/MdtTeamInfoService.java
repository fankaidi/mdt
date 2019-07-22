package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.MdtTeamInfoMapper;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.SysOrg;

/**
 * MDT团队基本信息表服务实现类
 * 
 * @author fankd created on 2019-6-23
 * @since
 */
@Service
public class MdtTeamInfoService extends JSBaseService {

	@Resource
	private MdtTeamInfoMapper dao;

	@Resource
	private SysOrgService sysOrgService;

	public MdtTeamInfo selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<MdtTeamInfo> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtTeamInfo> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(MdtTeamInfo obj) {
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean update(MdtTeamInfo obj) {
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

	public void save(MdtTeamInfo teamInfo) {

		if (teamInfo.getId() == null) {

			List<MdtTeamInfo> list = selectByTeamIdAndUserId(teamInfo.getTeamId(), teamInfo.getUserId());
			if (list.size() > 0) {
				BusinessExceptionUtil.threwException("该专家已存在不可重复添加!");
			}

			insert(teamInfo);
		} else {

			update(teamInfo);
		}
	}

	public List<MdtTeamInfo> selectByTeamIdAndUserId(Long teamId, Long userId) {

		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId, "userId", userId);
		List<MdtTeamInfo> list = selectByWhere(parameters);
		return list;
	}

	public List<MdtTeamInfo> selectList(Long teamId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId,"orderby","specialist_type,id");
		List<MdtTeamInfo> list = selectByWhere(parameters);
		for (MdtTeamInfo mdtTeamInfo : list) {
			SysOrg org = sysOrgService.selectOne(mdtTeamInfo.getDepartment());
			if (org != null) {
				mdtTeamInfo.setDepartment(org.getName());
			}
		}
		return list;
	}

	public long selectListCount(Long teamId) {

		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId);
		return selectCountByWhere(parameters);
	}

}
