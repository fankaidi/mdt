package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtApply;

/**
 * MDT申请表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtApplyMapper {
	
	
	public MdtApply selectOne(Long id);
	
	public List<MdtApply> selectByIds(Collection<Long> ids);
	
	
	public List<MdtApply> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtApply obj);	
	
	
	public boolean update(MdtApply obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
