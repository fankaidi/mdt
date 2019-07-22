package com.kensure.mdt.service;

import co.kensure.mem.MapUtils;
import com.kensure.mdt.dao.SysRoleMenuMapper;
import com.kensure.mdt.entity.SysRoleMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * 角色权限表服务实现类
 */
@Service
public class SysRoleMenuService {
	
	@Resource
	private SysRoleMenuMapper dao;
    
    
    public SysRoleMenu selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysRoleMenu> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysRoleMenu> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysRoleMenu obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(SysRoleMenu obj){
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
    
    public List<SysRoleMenu> getSysRoleMenuByRoleId(Long roleId) {

        Map<String, Object> parameters = MapUtils.genMap("roleId", roleId);
        List<SysRoleMenu> sysRoleMenus = selectByWhere(parameters);

        return sysRoleMenus;
    }

    public List<Long> getMenuIdByRoleId(Long roleId) {
        List<SysRoleMenu> roleMenuList = getSysRoleMenuByRoleId(roleId);

        List<Long> list = new ArrayList<>();

        for (SysRoleMenu sysRoleMenu : roleMenuList) {
            list.add(sysRoleMenu.getMenuId());
        }

        return list;
    }

    public void save(Long roleId, Long muneId) {

        SysRoleMenu sysRoleMenu = new SysRoleMenu();

        sysRoleMenu.setRoleId(roleId);
        sysRoleMenu.setMenuId(muneId);

        insert(sysRoleMenu);
    }
}
