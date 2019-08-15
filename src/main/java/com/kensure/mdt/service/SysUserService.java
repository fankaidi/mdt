package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.lc.model.LCProcess;
import com.kensure.mdt.dao.SysUserMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.SysMenu;
import com.kensure.mdt.entity.SysOrg;
import com.kensure.mdt.entity.SysRole;
import com.kensure.mdt.entity.SysUser;
import com.kensure.mdt.entity.SysUserRole;

/**
 * 用户表服务实现类
 */
@Service
public class SysUserService extends JSBaseService {

	@Resource
	private SysUserMapper dao;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysRoleMenuService sysRoleMenuService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private MdtTeamInfoService mdtTeamInfoService;

	public SysUser selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<SysUser> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<SysUser> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(SysUser obj) {
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean update(SysUser obj) {
		super.beforeUpdate(obj);
		return dao.update(obj);
	}

	public boolean updateByMap(Map<String, Object> params) {
		return dao.updateByMap(params);
	}

	public boolean delete(Long id) {
		return dao.delete(id);
	}

	public boolean deleteMulti(Collection<Long> ids) {
		return dao.deleteMulti(ids);
	}

	public boolean deleteByWhere(Map<String, Object> parameters) {
		return dao.deleteByWhere(parameters);
	}

	public List<SysUser> selectList(PageInfo page, AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		MapUtils.putPageInfo(parameters, page);
		setOrgLevel(parameters, user);
		List<SysUser> userList = selectByWhere(parameters);
		for (SysUser sysUser : userList) {
			SysOrg org = sysOrgService.selectOne(sysUser.getDepartment());
			if (org != null) {
				sysUser.setDepartment(org.getName());
			}
		}
		return userList;
	}

	/**
	 * 根据用户，获取他当前园区的信息
	 * 
	 * @param user
	 * @return
	 */
	public List<SysUser> selectList(AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		setOrgLevel(parameters, user);
		List<SysUser> userList = selectByWhere(parameters);
		for (SysUser sysuser : userList) {
			SysOrg org = sysOrgService.selectOne(sysuser.getDepartment());
			String remark = sysuser.getNumber() + " " + org.getName() + " " + sysuser.getName();
			sysuser.setRemark(remark);
		}
		return userList;
	}

	public long selectListCount(AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		setOrgLevel(parameters, user);
		return selectCountByWhere(parameters);
	}

	/**
	 * 保存
	 * 
	 * @param user
	 */
	public void save(SysUser user) {
		SysOrg org = sysOrgService.selectOne(user.getDepartment());
		user.setCreatedOrgid(org.getCreatedOrgid());
		if (user.getId() == null) {
			user.setPassword("123456");
			insert(user);
		} else {
			update(user);
		}
	}

	/**
	 * 保存用户角色
	 * 
	 * @param userId
	 * @param checkedStr
	 */
	public void saveRole(Long userId, String checkedStr) {

		sysUserRoleService.delete(userId);

		String[] roleIds = checkedStr.split(",");

		for (String roleIdStr : roleIds) {

			Long roleId = Long.parseLong(roleIdStr);

			sysUserRoleService.save(userId, roleId);
		}
	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public AuthUser login(String username, String password) {

		Map<String, Object> parameters = MapUtils.genMap("name", username, "password", password);
		List<SysUser> list = selectByWhere(parameters);
		if (list.isEmpty()) {
			BusinessExceptionUtil.threwException("账号或者密码错误");
		}
		SysUser sysUser = list.get(0);

		AuthUser user = new AuthUser();
		BeanUtils.copyProperties(sysUser, user);
		List<SysUserRole> userRoles = sysUserRoleService.getSysUserRoleByUserId(user.getId());
		String roleIds = ""; // 角色id取所有
		int roleLevel = 5; // 数据权限级别最小
		for (SysUserRole userRole : userRoles) {
			SysRole sysRole = sysRoleService.selectOne(userRole.getRoleId());
			roleIds += sysRole.getId() + ",";
			if (roleLevel > sysRole.getLevel()) {
				roleLevel = sysRole.getLevel();
			}
		}
		user.setRoleIds(roleIds);
		user.setRoleLevel(roleLevel);

		List<SysOrg> deptList = sysOrgService.selectChildList(sysUser.getDepartment());
		List<String> deptIdList = new ArrayList<>();
		deptIdList.add(sysUser.getDepartment());
		if (CollectionUtils.isNotEmpty(deptList)) {
			for (SysOrg org : deptList) {
				if (!deptIdList.contains(org.getId())) {
					deptIdList.add(org.getId());
				}
			}
		}
		user.setDeptIdList(deptIdList);

		return user;

	}

	/**
	 * 获取用户的菜单Tree
	 * 
	 * @param user
	 * @return
	 */
	public SysMenu getTree(AuthUser user) {

		List<Long> roleIds = sysUserRoleService.getRoleIdByUserId(user.getId());

		Set<Long> menus = new HashSet<>();

		for (Long roleId : roleIds) {

			List<Long> menuIdByRoleId = sysRoleMenuService.getMenuIdByRoleId(roleId);
			for (Long aLong : menuIdByRoleId) {
				menus.add(aLong);
			}
		}

		SysMenu sysMenu = sysMenuService.selectOne(0L);

		List<SysMenu> menuList = sysMenuService.getSysMenuByPid(sysMenu.getId());

		// List<SysMenu> menus1 = new ArrayList<>();
		// for (SysMenu menu : menuList) {
		//
		// // if (menus.contains(menu.getId())) {
		// menus1.add(menu);
		// // }
		// }

		for (SysMenu menu : menuList) {
			List<SysMenu> menuList2 = sysMenuService.getSysMenuByPid(menu.getId());

			List<SysMenu> menus2 = new ArrayList<>();
			for (SysMenu men : menuList2) {

				if (menus.contains(men.getId())) {
					menus2.add(men);
				}
			}

			menu.setMenus(menus2);
		}

		Iterator<SysMenu> it = menuList.iterator();
		while (it.hasNext()) {
			SysMenu x = it.next();
			if (x.getMenus() == null || x.getMenus().isEmpty()) {
				it.remove();
			}
		}

		sysMenu.setMenus(menuList);

		return sysMenu;
	}

	public String getUsername(Long id) {

		SysUser sysUser = selectOne(id);
		if (sysUser != null) {
			return sysUser.getName();
		}

		return "";
	}

	/**
	 * 获取当前用户的科室主任
	 * 
	 * @return
	 */
	public List<SysUser> selectKSZR(Long applyPersonId) {
		SysUser one = selectOne(applyPersonId);

		Map<String, Object> parameters = MapUtils.genMap("department", one.getDepartment());
		// 部门下的人
		List<SysUser> deptUserList = selectByWhere(parameters);
		Map<String, SysUser> deptUserMap = new HashMap<>();
		for (SysUser user : deptUserList) {
			deptUserMap.put(user.getId().toString(), user);
		}

		// 获取科室主任角色
		SysRole role = sysRoleService.selectByCode("kszr", one.getCreatedOrgid());
		List<SysUserRole> list = sysUserRoleService.selectByRoleId(role.getId());

		List<SysUser> userList = new ArrayList<>();
		for (SysUserRole sysuerrole : list) {
			SysUser user = deptUserMap.get(sysuerrole.getUserId().toString());
			if (user != null) {
				userList.add(user);
			}
		}
		return userList;
	}

	/**
	 * 根据根据用户id和角色编码获取用户数据 其实这段代码写得不好，不应该这么写的，不过效率问题，先这样吧
	 */
	public List<Long> selectByRoleCode(String roleCode, LCProcess process, Long busiid) {
		List<Long> list = new ArrayList<>();
		// 科室主任
		if ("kszr".equals(roleCode)) {
			MdtTeamInfo shouxi =  mdtTeamInfoService.selectSxzjList(busiid).get(0);
			Long userId = shouxi.getUserId();
			List<SysUser> kszrs = selectKSZR(userId);
			if (CollectionUtils.isEmpty(kszrs)) {
				return null;
			}
			for (SysUser kszr : kszrs) {
				list.add(kszr.getId());
			}
		} else if ("tdsx".equals(roleCode)) {
			// 团队首席
			MdtTeamInfo shouxi =  mdtTeamInfoService.selectSxzjList(busiid).get(0);
			list.add(shouxi.getUserId());
		} else {
			SysUser one = selectOne(process.getCreatedUserid());
			SysRole role = sysRoleService.selectByCode(roleCode, one.getCreatedOrgid());
			List<SysUserRole> userlist = sysUserRoleService.selectByRoleId(role.getId());
			if (CollectionUtils.isEmpty(userlist)) {
				BusinessExceptionUtil.threwException("找不到对应人员");
			}
			for (SysUserRole ur : userlist) {
				list.add(ur.getUserId());
			}
		}
		return list;
	}
}
