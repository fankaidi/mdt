package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.mdt.dao.SysOrgMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysOrg;

/**
 * 机构表服务实现类
 */
@Service
public class SysOrgService extends JSBaseService{
	
	@Resource
	private SysOrgMapper dao;
    
    
    public SysOrg selectOne(String id){
    	return dao.selectOne(id);
    }
	
	public List<SysOrg> selectByIds(Collection<String> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysOrg> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysOrg obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(SysOrg obj){
		return dao.update(obj);
	}
    
    public boolean updateByMap(Map<String, Object> params){
		return dao.updateByMap(params);
	}
    
    
	public boolean delete(String id){
		return dao.delete(id);
	}	
	
    public boolean deleteMulti(Collection<Long> ids){
		return dao.deleteMulti(ids);
	}
    
    public boolean deleteByWhere(Map<String, Object> parameters){
		return dao.deleteByWhere(parameters);
	}


	public List<SysOrg> selectList(PageInfo page,AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		MapUtils.putPageInfo(parameters, page);
		setOrgLevel(parameters, user);
		List<SysOrg> orgList = selectByWhere(parameters);
		return orgList;
	}

	public long selectListCount(AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		setOrgLevel(parameters, user);
		return selectCountByWhere(parameters);
	}

	public List<SysOrg> selectAllList(AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		setOrgLevel(parameters, user);
		List<SysOrg> orgList = selectByWhere(parameters);
		return orgList;
	}

	public void save(SysOrg org) {

        SysOrg sysOrg = selectOne(org.getId());
        if (sysOrg != null) {
			BusinessExceptionUtil.threwException("该编号已存在");
		}
        if("1".equals(org.getPid())){
        	org.setCreatedOrgid(org.getId());
        }else{
        	org.setCreatedOrgid(selectOne(org.getPid()).getCreatedOrgid());
        }
        insert(org);
	}
  
	
	/**
	 * 获取自己和子机构,递归
	 */
	public List<SysOrg> selectChildList(String pid) {
		Map<String, Object> parameters = MapUtils.genMap("pid",pid);
		List<SysOrg> orgList = selectByWhere(parameters);	
		if(CollectionUtils.isNotEmpty(orgList)){
			List<SysOrg> childtemp = new ArrayList<>();
			for(SysOrg org:orgList){
				List<SysOrg> temp = selectChildList(org.getId());
				if(CollectionUtils.isNotEmpty(temp)){
					childtemp.addAll(temp);
				}
			}
			if(CollectionUtils.isNotEmpty(childtemp)){
				orgList.addAll(childtemp);
			}
		}
		return orgList;
	}

}
