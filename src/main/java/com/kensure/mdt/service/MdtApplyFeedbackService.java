
package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.MdtApplyFeedbackMapper;
import com.kensure.mdt.entity.MdtApplyFeedback;


/**
 * MDT随访反馈表服务实现类
 */
@Service
public class MdtApplyFeedbackService extends JSBaseService{
	
	@Resource
	private MdtApplyFeedbackMapper dao;

	@Resource
	private MdtApplyService mdtApplyService;
    
    
    public MdtApplyFeedback selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<MdtApplyFeedback> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<MdtApplyFeedback> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(MdtApplyFeedback obj){
		super.beforeInsert(obj);
		return dao.insert(obj);
	}
	
	
	public boolean update(MdtApplyFeedback obj){
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


	/**
	 * 根据MDT申请id查询 MDT反馈
	 * @param applyId
	 * @return
	 */
	public List<MdtApplyFeedback> selectList(Long applyId) {

		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId);

		List<MdtApplyFeedback> list = selectByWhere(parameters);
		return list;
	}

	@Transactional
	public void save(MdtApplyFeedback obj) {
		if (obj.getId() == null) {
			insert(obj);	
		} else {
			update(obj);
		}
		if("1".equalsIgnoreCase(obj.getShare())){
			mdtApplyService.share(obj.getApplyId());
		}
		mdtApplyService.saveFankui(obj.getApplyId());
		
	}
}
