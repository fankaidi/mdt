package com.kensure.mdt.dao;

import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 角色权限表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysRoleMenuMapper {
	
	
	public SysRoleMenu selectOne(Long id);
	
	public List<SysRoleMenu> selectByIds(Collection<Long> ids);
	
	
	public List<SysRoleMenu> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysRoleMenu obj);	
	
	
	public boolean update(SysRoleMenu obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
