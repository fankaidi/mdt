package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.SysGrade;

/**
 * 评分项维护表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysGradeMapper {
	
	
	public SysGrade selectOne(Long id);
	
	public List<SysGrade> selectByIds(Collection<Long> ids);
	
	
	public List<SysGrade> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysGrade obj);	
	
	
	public boolean update(SysGrade obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
