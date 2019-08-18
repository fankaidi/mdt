package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
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

	/**
	 * 根据队伍和用户获取成员信息
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public List<MdtTeamInfo> selectByTeamIdAndUserId(Long teamId, Long userId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId, "userId", userId);
		List<MdtTeamInfo> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 根据队伍获取成员信息
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public List<MdtTeamInfo> selectList(Long teamId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId, "orderby", "specialist_type,id");
		List<MdtTeamInfo> list = selectByWhere(parameters);
		for (MdtTeamInfo mdtTeamInfo : list) {
			SysOrg org = sysOrgService.selectOne(mdtTeamInfo.getDepartment());
			if (org != null) {
				mdtTeamInfo.setDepartment(org.getName());
			}
		}
		return list;
	}
	
	/**
	 * 根据用户获取成员信息
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public List<MdtTeamInfo> selectByUserId(Long userId) {
		Map<String, Object> parameters = MapUtils.genMap("userId", userId);
		List<MdtTeamInfo> list = selectByWhere(parameters);
		return list;
	}
	
	/**
	 * 根据用户获取成员信息
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public List<MdtTeamInfo> selectByUserIds(Collection<Long> userIds,String specialistType) {
		Map<String, Object> parameters = MapUtils.genMap("userIdList", userIds,"specialistType",specialistType);
		List<MdtTeamInfo> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 获取首席专家列表
	 * 
	 * @param teamId
	 * @return
	 */
	public List<MdtTeamInfo> selectSxzjList(Long teamId) {
		List<MdtTeamInfo> menbers = selectList(teamId);
		List<MdtTeamInfo> zj = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(menbers)) {
			for (MdtTeamInfo mb : menbers) {
				if ("1".equals(mb.getSpecialistType())) {
					zj.add(mb);
				}
			}
		}
		return zj;
	}

	public long selectListCount(Long teamId) {

		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId);
		return selectCountByWhere(parameters);
	}

}
