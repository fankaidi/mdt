package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtApplyOpinion;

/**
 * MDT专家意见表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtApplyOpinionMapper {
	
	
	public MdtApplyOpinion selectOne(Long id);
	
	public List<MdtApplyOpinion> selectByIds(Collection<Long> ids);
	
	
	public List<MdtApplyOpinion> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtApplyOpinion obj);	
	
	
	public boolean update(MdtApplyOpinion obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
