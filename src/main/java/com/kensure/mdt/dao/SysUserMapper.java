package com.kensure.mdt.dao;

import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysUserMapper {
	
	
	public SysUser selectOne(Long id);
	
	public List<SysUser> selectByIds(Collection<Long> ids);
	
	
	public List<SysUser> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysUser obj);	
	
	
	public boolean update(SysUser obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
