
package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.MdtTeamIssueMapper;
import com.kensure.mdt.entity.MdtTeamIssue;


/**
 * MDT团队课题表服务实现类
 */
@Service
public class MdtTeamIssueService extends JSBaseService{
	
	@Resource
	private MdtTeamIssueMapper dao;
    
    
    public MdtTeamIssue selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<MdtTeamIssue> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<MdtTeamIssue> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(MdtTeamIssue obj){
		super.beforeInsert(obj);
		return dao.insert(obj);
	}
	
	
	public boolean update(MdtTeamIssue obj){
		super.beforeUpdate(obj);
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


	public List<MdtTeamIssue> selectList(Long teamId) {

		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId);

		List<MdtTeamIssue> list = selectByWhere(parameters);
		return list;
	}

	public void save(MdtTeamIssue obj) {

		if (obj.getId() == null) {

			insert(obj);
		} else {

			update(obj);
		}
	}
}
