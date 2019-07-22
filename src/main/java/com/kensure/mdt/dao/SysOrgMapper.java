package com.kensure.mdt.dao;

import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysOrg;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 机构表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysOrgMapper {
	
	
	public SysOrg selectOne(String id);
	
	public List<SysOrg> selectByIds(Collection<String> ids);
	
	
	public List<SysOrg> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysOrg obj);	
	
	
	public boolean update(SysOrg obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(String id);
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
