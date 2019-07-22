package com.kensure.mdt.service;

import co.kensure.mem.MapUtils;
import com.kensure.mdt.dao.SysMenuMapper;
import com.kensure.mdt.entity.SysMenu;
import com.kensure.mdt.entity.Tree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 菜单表服务实现类
 */
@Service
public class SysMenuService {
	
	@Resource
	private SysRoleMenuService sysRoleMenuService;

	@Resource
	private SysMenuMapper dao;
    
    
    public SysMenu selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysMenu> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysMenu> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysMenu obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(SysMenu obj){
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



	public SysMenu getTree() {


		SysMenu sysMenu = selectOne(0L);

        List<SysMenu> menuList = getSysMenuByPid(sysMenu.getId());
        sysMenu.setMenus(menuList);

        for (SysMenu menu : menuList) {
            List<SysMenu> menuList2 = getSysMenuByPid(menu.getId());
            menu.setMenus(menuList2);
        }

        return sysMenu;
	}

	public List<SysMenu> getSysMenuByPid(Long pid) {

		Map<String, Object> parameters = MapUtils.genMap("pid", pid);

        List<SysMenu> sysMenus = selectByWhere(parameters);
        return sysMenus;
	}


	/**
	 * 获取角色菜单树
	 */
	public List<Tree> readRoleMenus(Long roleId) {
		//获取当前角色的菜单集合
//		List<SysMenu> menus = getTree().getMenus();

		// getMenuIdByRoleId

        List<Long> roleIds = sysRoleMenuService.getMenuIdByRoleId(roleId);


        List<Tree> trees = new ArrayList();

		SysMenu menu0 = getTree();//获取根菜单
		for (SysMenu menu1 : menu0.getMenus()) {//循环一级菜单

			Tree tree1 = new Tree();
			tree1.setId(menu1.getId()+"");//一级菜单ID
			tree1.setText(menu1.getName());//一级菜单名称

			for (SysMenu menu2 : menu1.getMenus()) {
				Tree tree2 = new Tree();
				tree2.setId(menu2.getId()+"");//二级菜单ID
				tree2.setText(menu2.getName());//二级菜单名称
				if(roleIds.contains(menu2.getId())){//判断当前二级菜单是否在菜单集合中出现
					tree2.setChecked(true);//选中
				}
				tree1.getChildren().add(tree2);//将二级菜单挂到一级菜单下
			}
			trees.add(tree1);
		}

		return trees;
	}


}
