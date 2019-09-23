package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.MdtApplyDoctorMapper;
import com.kensure.mdt.entity.MdtApplyDoctor;
import com.kensure.mdt.entity.MdtApplyOpinion;
import com.kensure.mdt.entity.MdtGradeItem;
import com.kensure.mdt.entity.MdtTeam;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.SysOrg;
import com.kensure.mdt.entity.resp.ExpertGradeList;

/**
 * MDT参加专家表服务实现类
 */
@Service
public class MdtApplyDoctorService extends JSBaseService {

	@Resource
	private MdtApplyDoctorMapper dao;

	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private MdtTeamService mdtTeamService;
	@Resource
	private MdtGradeItemService mdtGradeItemService;
	@Resource
	private MdtApplyOpinionService mdtApplyOpinionService;

	public MdtApplyDoctor selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<MdtApplyDoctor> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtApplyDoctor> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(MdtApplyDoctor obj) {
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean update(MdtApplyDoctor obj) {
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
	 * 从MDT团队中将专家选入MDT专家库
	 * 
	 * @param applyId
	 * @param mdtTeamInfo
	 */
	public MdtApplyDoctor addApplyDoctor(Long applyId, MdtTeamInfo mdtTeamInfo) {
		MdtApplyDoctor entiy = new MdtApplyDoctor();
		entiy.setApplyId(applyId);
		entiy.setUserId(mdtTeamInfo.getUserId());
		entiy.setName(mdtTeamInfo.getName());
		entiy.setDepartment(mdtTeamInfo.getDepartment());
		entiy.setTitle(mdtTeamInfo.getTitle());
		entiy.setPhone(mdtTeamInfo.getPhone());
		entiy.setPhoneCornet(mdtTeamInfo.getPhoneCornet());
		entiy.setTeamId(mdtTeamInfo.getTeamId());
		save(entiy);
		return entiy;
	}

	/**
	 * 保存MDT拟请专家
	 * 
	 * @param entiy
	 */
	public void save(MdtApplyDoctor entiy) {

		List<MdtApplyDoctor> list = selectByApplyIdAndUserId(entiy.getApplyId(), entiy.getUserId());
		if (list.size() > 0) {
			BusinessExceptionUtil.threwException("该专家已存在不可重复添加!");
		}

		insert(entiy);
	}

	/**
	 * 获取申请的专家
	 * 
	 * @param applyId
	 * @return
	 */
	public List<MdtApplyDoctor> selectByApplyId(Long applyId) {
		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId);
		List<MdtApplyDoctor> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 获取申请的专家的团队
	 * 
	 * @param applyId
	 * @return
	 */
	public Long getTeamIdByApplyId(Long applyId) {
		List<MdtApplyDoctor> list = selectByApplyId(applyId);
		if (CollectionUtils.isEmpty(list)) {
			BusinessExceptionUtil.threwException("请选择MDT团队专家");
		}
		Set<Long> teamIdSet = new HashSet<>();
		for (MdtApplyDoctor doc : list) {
			if (doc.getTeamId() != null) {
				teamIdSet.add(doc.getTeamId());
			}
		}
		if (teamIdSet.size() == 0) {
			BusinessExceptionUtil.threwException("必须选择一个MDT团队的成员");
		}
		if (teamIdSet.size() > 1) {
			BusinessExceptionUtil.threwException("一次MDT申请只能选择一个MDT团队的成员");
		}
		return teamIdSet.iterator().next();
	}

	/**
	 * 获取申请的专家
	 * 
	 * @param applyId
	 * @return
	 */
	public List<MdtApplyDoctor> getDetailByApplyId(Long applyId) {
		List<MdtApplyDoctor> list = selectByApplyId(applyId);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (MdtApplyDoctor doc : list) {
			// 科室评分
			List<MdtGradeItem> ksPinFenList = mdtGradeItemService.getMdtGradeItem("2", applyId, doc.getUserId());
			doc.setKsPinFenList(ksPinFenList);
			MdtApplyOpinion zjYiJian = mdtApplyOpinionService.getApplyOpinion(applyId, doc.getUserId());
			doc.setZjYiJian(zjYiJian);
			// 专家评分
			List<MdtGradeItem> zjPinFenList = mdtGradeItemService.getMdtGradeItem("1", applyId, doc.getUserId());
			doc.setZjPinFenList(zjPinFenList);
		}

		return list;
	}

	public List<MdtApplyDoctor> selectByApplyIdAndUserId(Long applyId, Long userId) {
		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId, "userId", userId);
		List<MdtApplyDoctor> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 根据MDT申请id查询 MDT专家库
	 * 
	 * @param applyId
	 * @return
	 */
	public List<MdtApplyDoctor> selectList(Long applyId) {

		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId);

		List<MdtApplyDoctor> list = selectByWhere(parameters);

		for (MdtApplyDoctor mdtApplyDoctor : list) {
			SysOrg org = sysOrgService.selectOne(mdtApplyDoctor.getDepartment());
			if (org != null) {
				mdtApplyDoctor.setDepartment(org.getName());
			}
			if (mdtApplyDoctor.getTeamId() != null) {
				MdtTeam one = mdtTeamService.selectOne(mdtApplyDoctor.getTeamId());
				mdtApplyDoctor.setTeamName(one.getName());
			}
		}
		return list;
	}

	public List<ExpertGradeList> listExpertGrade(Long applyId) {

		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId);

		List<MdtApplyDoctor> list = selectByWhere(parameters);

		List<ExpertGradeList> expertGradeLists = new ArrayList<>();

		for (MdtApplyDoctor mdtApplyDoctor : list) {
			SysOrg org = sysOrgService.selectOne(mdtApplyDoctor.getDepartment());
			if (org != null) {
				mdtApplyDoctor.setDepartment(org.getName());
			}

			ExpertGradeList expertGrade = new ExpertGradeList();
			BeanUtils.copyProperties(mdtApplyDoctor, expertGrade);

			List<MdtGradeItem> gradeItemList = mdtGradeItemService.getMdtGradeItem("1", applyId, expertGrade.getUserId());
			expertGrade.setList(gradeItemList);

			if (!gradeItemList.isEmpty()) {
				expertGrade.setReply("已回复");
				expertGrade.setReplyTime(gradeItemList.get(0).getCreatedTime());
			} else {
				expertGrade.setReply("未回复");
			}

			expertGradeLists.add(expertGrade);
		}

		return expertGradeLists;
	}
}
