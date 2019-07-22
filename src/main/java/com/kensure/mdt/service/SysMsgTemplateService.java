
package com.kensure.mdt.service;

import co.kensure.mem.MapUtils;
import com.kensure.mdt.dao.SysMsgTemplateMapper;
import com.kensure.mdt.entity.SysGrade;
import com.kensure.mdt.entity.SysMsgTemplate;
import com.kensure.mdt.service.SysMsgTemplateService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * 短信模板表服务实现类
 */
@Service
public class SysMsgTemplateService {
	
	@Resource
	private SysMsgTemplateMapper dao;
    
    
    public SysMsgTemplate selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysMsgTemplate> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysMsgTemplate> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysMsgTemplate obj){
		obj.setCreateTime(new Date());
		obj.setUpdateTime(new Date());
		return dao.insert(obj);
	}
	
	
	public boolean update(SysMsgTemplate obj){
		obj.setUpdateTime(new Date());
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
	 * 获取短信模板
	 * @return
	 */
	public SysMsgTemplate getMsgTemplate(String type) {

		Map<String, Object> parameters = MapUtils.genMap("type", type);
		List<SysMsgTemplate> list = selectByWhere(parameters);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public List<SysMsgTemplate> selectList() {

		Map<String, Object> parameters = MapUtils.genMap();
		List<SysMsgTemplate> list = selectByWhere(parameters);

		return list;
	}

	public void save(SysMsgTemplate obj) {

		if (obj.getId() == null) {

			insert(obj);
		} else {

			update(obj);
		}

	}

    public SysMsgTemplate getSysMsgTempByType(String type) {

		Map<String, Object> parameters = MapUtils.genMap("type", type);
		List<SysMsgTemplate> list = selectByWhere(parameters);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
    }
}
