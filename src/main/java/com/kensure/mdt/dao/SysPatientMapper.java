package com.kensure.mdt.dao;

import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysPatient;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 患者信息表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysPatientMapper {
	
	
	public SysPatient selectOne(Long id);
	
	public List<SysPatient> selectByIds(Collection<Long> ids);
	
	
	public List<SysPatient> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysPatient obj);	
	
	
	public boolean update(SysPatient obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
