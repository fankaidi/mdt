package com.kensure.lc.service;

import com.kensure.lc.dao.LCDefineItemDao;
import com.kensure.lc.model.LCDefineItem;
import com.kensure.lc.service.LCDefineItemService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;


/**
 * 流程节点定义服务实现类
 * @author fankd created on 2019-7-22
 * @since 
 */
@Service
public class LCDefineItemService extends JSBaseService{
	
	@Resource
	private LCDefineItemDao dao;
    
    
    public LCDefineItem selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<LCDefineItem> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<LCDefineItem> selectAll(){
		return dao.selectAll();
	}
	
	public List<LCDefineItem> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	
	public long selectCount(){
		return dao.selectCount();
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(LCDefineItem obj){
		return dao.insert(obj);
	}
	
	public boolean insertInBatch(List<LCDefineItem> objs){
		return dao.insertInBatch(objs);
	}
	
	
	public boolean update(LCDefineItem obj){
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
    
    public List<LCDefineItem> selectByDefineId(Long lcid){
    	Map<String, Object> parameters = MapUtils.genMap("defineId",lcid);
		return selectByWhere(parameters);
	}
    
  

}
