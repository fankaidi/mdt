package com.kensure.mdt.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;
import co.kensure.mem.NumberUtils;

import com.kensure.mdt.dao.MdtGradeItemMapper;
import com.kensure.mdt.entity.MdtApply;
import com.kensure.mdt.entity.MdtApplyDoctor;
import com.kensure.mdt.entity.MdtGradeItem;
import com.kensure.mdt.entity.SysGrade;
import com.kensure.mdt.entity.bo.MdtGradeItemReq;
import com.kensure.mdt.entity.bo.MdtGradeReq;

/**
 * 评分表服务实现类
 */
@Service
public class MdtGradeItemService extends JSBaseService{

	@Resource
	private MdtGradeItemMapper dao;
	@Resource
	private SysGradeService sysGradeService;

	public MdtGradeItem selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<MdtGradeItem> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtGradeItem> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(MdtGradeItem obj) {
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean update(MdtGradeItem obj) {
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
	 * 保存专家评分项目
	 * 
	 * @param mdtGradeReq
	 */
	@Transactional
	public void saveExpertGrade(MdtGradeReq mdtGradeReq) {

		// 先删除评分
		delGradeItem("1", mdtGradeReq.getApplyId(), mdtGradeReq.getUserId());

		List<MdtGradeItemReq> list = mdtGradeReq.getList();

		for (MdtGradeItemReq mdtGradeItem : list) {

			MdtGradeItem obj = new MdtGradeItem();

			obj.setType("1"); // 1:专家打分
			obj.setApplyId(mdtGradeReq.getApplyId());
			obj.setUserId(mdtGradeReq.getUserId());

			obj.setSysGradeId(mdtGradeItem.getGradeId());
			obj.setGrade(mdtGradeItem.getGrade());
			obj.setDescription(mdtGradeItem.getDescription());
			obj.setMinValue(mdtGradeItem.getMinValue());
			obj.setMaxValue(mdtGradeItem.getMaxValue());

			insert(obj);
		}
	}

	/**
	 * 保存组织科室评分项目
	 */
	@Transactional
	public void saveDeptGrade(MdtApply apply) {
		// 先删除评分
		delItem("2", apply.getId());
		
		//再加
		List<MdtApplyDoctor> doctors = apply.getDoctors();
		for (MdtApplyDoctor doctor : doctors) {
			List<MdtGradeItem> pinfenlist = doctor.getKsPinFenList();
			for (MdtGradeItem item : pinfenlist) {
				item.setType("2"); // 1:专家打分,2是科室评分
				item.setApplyId(apply.getId());
				item.setUserId(doctor.getUserId());
				SysGrade sysgrade = sysGradeService.selectOne(item.getSysGradeId());
				item.setDescription(sysgrade.getDescription());
				item.setMinValue(NumberUtils.parseLong(sysgrade.getMinValue(), 0L));
				item.setMaxValue(NumberUtils.parseLong(sysgrade.getMaxValue(), 0L));
				insert(item);
			}
		}
	}
	
	/**
	 * 保存录入专家评分项目
	 */
	@Transactional
	public void saveLRZJPF(MdtApply apply) {
		// 先删除评分
		delItem("1", apply.getId());
		
		//再加
		List<MdtApplyDoctor> doctors = apply.getDoctors();
		for (MdtApplyDoctor doctor : doctors) {
			List<MdtGradeItem> pinfenlist = doctor.getZjPinFenList();
			for (MdtGradeItem item : pinfenlist) {
				item.setType("1"); // 1:专家打分,2是科室评分
				item.setApplyId(apply.getId());
				item.setUserId(doctor.getUserId());
				SysGrade sysgrade = sysGradeService.selectOne(item.getSysGradeId());
				item.setDescription(sysgrade.getDescription());
				item.setMinValue(NumberUtils.parseLong(sysgrade.getMinValue(), 0L));
				item.setMaxValue(NumberUtils.parseLong(sysgrade.getMaxValue(), 0L));
				insert(item);
			}
		}
	}

	/**
	 * 删除一个申请下的所有评分
	 * 
	 * @param applyId
	 */
	public void delItem(String type, Long applyId) {
		Map<String, Object> parameters = MapUtils.genMap("type", type, "applyId", applyId);
		deleteByWhere(parameters);
	}

	/**
	 * 删除评分
	 * 
	 * @param applyId
	 * @param userId
	 */
	public void delGradeItem(String type, Long applyId, Long userId) {
		Map<String, Object> parameters = MapUtils.genMap("type", type, "applyId", applyId, "userId", userId);
		deleteByWhere(parameters);
	}

	public List<MdtGradeItem> getMdtGradeItem(String type, Long applyId, Long userId) {
		Map<String, Object> parameters = MapUtils.genMap("type", type, "applyId", applyId, "userId", userId, "orderby", "sys_grade_id");
		List<MdtGradeItem> list = selectByWhere(parameters);
		return list;
	}
	
}
