
package com.kensure.mdt.service;

import co.kensure.mem.MapUtils;
import com.kensure.mdt.dao.SysGradeMapper;
import com.kensure.mdt.entity.MdtTeamObjective;
import com.kensure.mdt.entity.SysGrade;
import com.kensure.mdt.service.SysGradeService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * 评分项维护表服务实现类
 */
@Service
public class SysGradeService {
	
	@Resource
	private SysGradeMapper dao;
    
    
    public SysGrade selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysGrade> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysGrade> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysGrade obj){
		obj.setCreateTime(new Date());
		obj.setUpdateTime(new Date());
		return dao.insert(obj);
	}
	
	
	public boolean update(SysGrade obj){
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
	 * 获取专家评分项
	 * @return
	 */
	public List<SysGrade> getExpertGradeItem(String type) {

		Map<String, Object> parameters = MapUtils.genMap("type", type);
		List<SysGrade> list = selectByWhere(parameters);

		return list;
	}


	public List<SysGrade> selectList(String type) {

		Map<String, Object> parameters = MapUtils.genMap("type", type);
		List<SysGrade> list = selectByWhere(parameters);

		return list;
	}

	public void save(SysGrade grade) {

		if (grade.getId() == null) {

			insert(grade);
		} else {

			update(grade);
		}

	}
}
