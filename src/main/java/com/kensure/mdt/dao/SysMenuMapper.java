package com.kensure.mdt.dao;

import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 菜单表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysMenuMapper {
	
	
	public SysMenu selectOne(Long id);
	
	public List<SysMenu> selectByIds(Collection<Long> ids);
	
	
	public List<SysMenu> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysMenu obj);	
	
	
	public boolean update(SysMenu obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
