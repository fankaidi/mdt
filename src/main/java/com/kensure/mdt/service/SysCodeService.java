
package com.kensure.mdt.service;

import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;
import com.kensure.mdt.dao.SysCodeMapper;
import com.kensure.mdt.entity.MdtTeam;
import com.kensure.mdt.entity.SysCode;
import com.kensure.mdt.entity.query.MdtTeamQuery;
import com.kensure.mdt.service.SysCodeService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * 字典维护表服务实现类
 */
@Service
public class SysCodeService {
	
	@Resource
	private SysCodeMapper dao;
    
    
    public SysCode selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysCode> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysCode> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysCode obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(SysCode obj){
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


	public List<SysCode> getCodeByType(String type) {

		Map<String, Object> parameters = MapUtils.genMap("type", type);
		List<SysCode> list = selectByWhere(parameters);
		return list;
	}
  

}
