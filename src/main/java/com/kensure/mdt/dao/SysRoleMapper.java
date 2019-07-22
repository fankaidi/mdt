package com.kensure.mdt.dao;

import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 角色表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysRoleMapper {
	
	
	public SysRole selectOne(Long id);
	
	public List<SysRole> selectByIds(Collection<Long> ids);
	
	
	public List<SysRole> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysRole obj);	
	
	
	public boolean update(SysRole obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
