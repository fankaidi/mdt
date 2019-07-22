package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtApplyDoctor;

/**
 * MDT参加专家表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtApplyDoctorMapper {
	
	
	public MdtApplyDoctor selectOne(Long id);
	
	public List<MdtApplyDoctor> selectByIds(Collection<Long> ids);
	
	
	public List<MdtApplyDoctor> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtApplyDoctor obj);	
	
	
	public boolean update(MdtApplyDoctor obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
