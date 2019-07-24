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
public class SysPatientService extends JSBaseService{
	
	@Resource
	private SysPatientMapper dao;
    
    
    public SysPatient selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysPatient> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysPatient> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysPatient obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(SysPatient obj){
		super.beforeUpdate(obj);
		return dao.update(obj);
	}
    
    public boolean updateByMap(Map<String, Object> params){
		return dao.updateByMap(params);
	}
    
    
	public boolean delete(Long id){
		return dao.delete(id);
	}	
	
    public boolean deleteMulti(Collection<Long> ids){
		return dao.deleteMulti(ids);
	}
    
    public boolean deleteByWhere(Map<String, Object> parameters){
		return dao.deleteByWhere(parameters);
	}


	public List<SysPatient> selectList(SysPatientQuery query,PageInfo page,AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		MapUtils.putPageInfo(parameters, page);
		setOrgLevel(parameters, user);
		List<SysPatient> list = selectByWhere(parameters);
		return list;
	}

	public long selectListCount(SysPatientQuery query,AuthUser user) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, true);
		setOrgLevel(parameters, user);
		return selectCountByWhere(parameters);
	}

	public void save(SysPatient patient,AuthUser user) {
    	if (patient.getId() == null) {
    		initBase(patient, user);
			insert(patient);
		} else {
			update(patient);
		}
	}
}
