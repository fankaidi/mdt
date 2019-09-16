/*
 * 文件名称: MdtTeamTextServiceImpl.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-8-13
 * 修改内容: 
 */
package com.kensure.mdt.service;

import com.kensure.mdt.dao.MdtTeamTextDao;
import com.kensure.mdt.model.MdtTeamText;
import com.kensure.mdt.service.MdtTeamTextService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import co.kensure.frame.JSBaseService;


/**
 * mdt团队文本数据服务实现类
 * @author fankd created on 2019-8-13
 * @since 
 */
@Service
public class MdtTeamTextService extends JSBaseService{
	
	@Resource
	private MdtTeamTextDao dao;
    
    
    public MdtTeamText selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<MdtTeamText> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<MdtTeamText> selectAll(){
		return dao.selectAll();
	}
	
	public List<MdtTeamText> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	
	public long selectCount(){
		return dao.selectCount();
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(MdtTeamText obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(MdtTeamText obj){
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
