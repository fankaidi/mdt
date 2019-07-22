package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtApplyFeedback;

/**
 * MDT随访反馈表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtApplyFeedbackMapper {
	
	
	public MdtApplyFeedback selectOne(Long id);
	
	public List<MdtApplyFeedback> selectByIds(Collection<Long> ids);
	
	
	public List<MdtApplyFeedback> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtApplyFeedback obj);	
	
	
	public boolean update(MdtApplyFeedback obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
