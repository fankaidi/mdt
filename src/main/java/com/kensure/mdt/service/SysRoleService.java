package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.SysRoleMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysRole;
import com.kensure.mdt.entity.Tree;


/**
 * 角色表服务实现类
 */
@Service
public class SysRoleService extends JSBaseService{
	
	@Resource
	private SysRoleMapper dao;

	@Resource
	private SysRoleMenuService sysRoleMenuService;

	@Resource
	private SysUserRoleService sysUserRoleService;
    
    
    public SysRole selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysRole> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysRole> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysRole obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(SysRole obj){
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


	public List<SysRole> findAll(AuthUser user) {
        Map<String, Object> parameters = MapUtils.genMap();
    	setOrgLevel(parameters, user);
        List<SysRole> roleList = selectByWhere(parameters);

        return roleList;
    }

	/**
	 * 角色和菜单的关系
	 * @param roleId
	 * @param checkedStr
	 */
    public void save(Long roleId, String checkedStr) {
        sysRoleMenuService.delete(roleId);
        String[] menuIds = checkedStr.split(",");
        for (String menuIdStr : menuIds) {
            Long menuId = Long.parseLong(menuIdStr);
            sysRoleMenuService.save(roleId, menuId);
        }
    }
    
    /**
	 *根据 code和机构id获取特殊角色
	 */
	public SysRole selectByCode(String code,String createdOrgid) {
		Map<String, Object> parameters = MapUtils.genMap("code", code,"createdOrgid",createdOrgid);
		List<SysRole> roles = selectByWhere(parameters);
		if(CollectionUtils.isEmpty(roles)){
			return null;
		}
		return roles.get(0);
	}
	

    public List<Tree> readEmpRoles(Long userId) {
        Map<String, Object> parameters = MapUtils.genMap();
        List<SysRole> roleList = selectByWhere(parameters);
        List<Long> roleIds = sysUserRoleService.getRoleIdByUserId(userId);
//        List<Role> roles = empDao.get(uuid).getRoles();
        List<Tree> trees = new ArrayList<>();
//        List<Role> roleList = roleDao.getList(null, null, null);
        for (SysRole role : roleList) {
            Tree tree = new Tree();
            tree.setId(String.valueOf(role.getId()));
            tree.setText(role.getName());
            if(roleIds.contains(role.getId())){
                tree.setChecked(true);
            }
            trees.add(tree);
        }
        return trees;
    }
}
