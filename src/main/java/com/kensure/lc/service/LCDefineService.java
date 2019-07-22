package com.kensure.lc.service;

import com.kensure.lc.dao.LCDefineDao;
import com.kensure.lc.model.LCDefine;
import com.kensure.lc.model.LCDefineItem;
import com.kensure.lc.service.LCDefineService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;


/**
 * 流程定义表 服务实现类
 * @author fankd created on 2019-7-22
 * @since 
 */
@Service
public class LCDefineService extends JSBaseService{
	
	@Resource
	private LCDefineDao dao;
	@Resource
	private LCDefineItemService lCDefineItemService;
    
    
    public LCDefine selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<LCDefine> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<LCDefine> selectAll(){
		return dao.selectAll();
	}
	
	public List<LCDefine> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	
	public long selectCount(){
		return dao.selectCount();
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(LCDefine obj){
		return dao.insert(obj);
	}
	
	public boolean insertInBatch(List<LCDefine> objs){
		return dao.insertInBatch(objs);
	}
	
	
	public boolean update(LCDefine obj){
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
    
    /**
     * 详情
     * @param id
     * @return
     */
    public LCDefine detail(Long id){
    	LCDefine define = selectOne(id);
    	List<LCDefineItem> items = lCDefineItemService.selectByDefineId(id);
    	define.setItems(items);
    	return define;
    }
    
  

}
