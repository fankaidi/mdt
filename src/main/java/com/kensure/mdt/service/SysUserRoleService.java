package com.kensure.mdt.service;

import co.kensure.mem.MapUtils;
import com.kensure.mdt.dao.SysUserRoleMapper;
import com.kensure.mdt.entity.SysUserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 用户角色表服务实现类
 */
@Service
public class SysUserRoleService {
	
	@Resource
	private SysUserRoleMapper dao;
    
    
    public SysUserRole selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysUserRole> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysUserRole> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysUserRole obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(SysUserRole obj){
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



	public List<SysUserRole> getSysUserRoleByUserId(Long userId) {

		Map<String, Object> parameters = MapUtils.genMap("userId", userId);
		List<SysUserRole> sysRoleMenus = selectByWhere(parameters);

		return sysRoleMenus;
	}

	public List<Long> getRoleIdByUserId(Long userId) {
		List<SysUserRole> sysUserRoleList = getSysUserRoleByUserId(userId);

		List<Long> list = new ArrayList<>();

		for (SysUserRole sysUserRole : sysUserRoleList) {
			list.add(sysUserRole.getRoleId());
		}

		return list;
	}

	/**
	 * 根据角色获取角色的数据
	 * @param roleId
	 * @return
	 */
	public List<SysUserRole> selectByRoleId(Long roleId) {
		Map<String, Object> parameters = MapUtils.genMap("roleId", roleId);
		List<SysUserRole> sysRoleMenus = selectByWhere(parameters);
		return sysRoleMenus;
	}

	public void save(Long userId, Long roleId) {

		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(userId);
		sysUserRole.setRoleId(roleId);

		insert(sysUserRole);
	}
}
