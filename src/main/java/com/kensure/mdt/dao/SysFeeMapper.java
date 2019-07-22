package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.SysFee;

/**
 * MDT收费情况维护表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysFeeMapper {
	
	
	public SysFee selectOne(Long id);
	
	public List<SysFee> selectByIds(Collection<Long> ids);
	
	
	public List<SysFee> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysFee obj);	
	
	
	public boolean update(SysFee obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
