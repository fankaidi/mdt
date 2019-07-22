package com.kensure.mdt.dao;

import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户角色表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysUserRoleMapper {
	
	
	public SysUserRole selectOne(Long id);
	
	public List<SysUserRole> selectByIds(Collection<Long> ids);
	
	
	public List<SysUserRole> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysUserRole obj);	
	
	
	public boolean update(SysUserRole obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
