package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.mdt.dao.SysPatientMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysPatient;
import com.kensure.mdt.entity.query.SysPatientQuery;

/**
 * 患者信息表服务实现类
 */
@Service
public class SysPatientService extends JSBaseService {

	@Resource
	private SysPatientMapper dao;
	@Resource
	private WsPatientService wsPatientService;

	public SysPatient selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<SysPatient> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<SysPatient> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(SysPatient obj) {
		obj.setIsDel(0);
		return dao.insert(obj);
	}

	public boolean update(SysPatient obj) {
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
	 * 根据住院号获取住院病人信息
	 * 
	 * @param inHospitalNo
	 * @return
	 */
	public List<SysPatient> selectByNum(String patientType, String num) {
		Map<String, Object> parameters = MapUtils.genMap("patientType", patientType);
		if ("1".equalsIgnoreCase(patientType)) {
			parameters.put("inHospitalNo", num);
		} else {
			parameters.put("medicalNo", num);
		}
		List<SysPatient> list = selectByWhere(parameters);
		return list;
	}

	/**
	 * 根据预约号获取门诊病人信息
	 * 
	 * @param inHospitalNo
	 * @return
	 */
	public List<SysPatient> selectMenZhen(String treatmentNo) {
		Map<String, Object> parameters = MapUtils.genMap("patientType", "2", "treatmentNo", treatmentNo);
		List<SysPatient> list = selectByWhere(parameters);
		return list;
	}

	public List<SysPatient> selectList(SysPatientQuery query, PageInfo page, AuthUser user) {
		// 不是第一页，不进行同步
		if (page.getPageNo() != 1) {
			query.setSyncData(0);
		}
		if (query.getSyncData() != null && query.getSyncData() == 1) {
			wsPatientService.syncData(query, user);
		}
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		MapUtils.putPageInfo(parameters, page);
		parameters.put("isDel", 0);
		setOrgLevel(parameters, user);
		parameters.put("orderby", "id desc");
		List<SysPatient> list = selectByWhere(parameters);
		return list;
	}

	public long selectListCount(SysPatientQuery query, AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		parameters.put("isDel", 0);
		setOrgLevel(parameters, user);
		return selectCountByWhere(parameters);
	}

	/**
	 * 忽略
	 * 
	 * @param id
	 * @return
	 */
	public void hulue(Long id) {
		SysPatient pa = selectOne(id);
		pa.setIsDel(1);
		update(pa);
	}

	public void save(SysPatient patient, AuthUser user) {
		if (patient.getId() == null) {
			initBase(patient, user);
			insert(patient);
		} else {
			update(patient);
		}
	}
}
