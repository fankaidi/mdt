/*
 * 文件名称: MdtApplyTextServiceImpl.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-9-23
 * 修改内容: 
 */
package com.kensure.mdt.service;

import com.kensure.mdt.dao.MdtApplyTextDao;
import com.kensure.mdt.model.MdtApplyText;
import com.kensure.mdt.service.MdtApplyTextService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import co.kensure.frame.JSBaseService;


/**
 * 申请大文本内容服务实现类
 * @author fankd created on 2019-9-23
 * @since 
 */
@Service
public class MdtApplyTextService extends JSBaseService{
	
	@Resource
	private MdtApplyTextDao dao;
    
    
    public MdtApplyText selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<MdtApplyText> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<MdtApplyText> selectAll(){
		return dao.selectAll();
	}
	
	public List<MdtApplyText> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	
	public long selectCount(){
		return dao.selectCount();
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(MdtApplyText obj){
		return dao.insert(obj);
	}
	
	public boolean insertInBatch(List<MdtApplyText> objs){
		return dao.insertInBatch(objs);
	}
	
	
	public boolean update(MdtApplyText obj){
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
    
    
  

}
