package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtGradeItem;

/**
 * 评分表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtGradeItemMapper {
	
	
	public MdtGradeItem selectOne(Long id);
	
	public List<MdtGradeItem> selectByIds(Collection<Long> ids);
	
	
	public List<MdtGradeItem> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtGradeItem obj);	
	
	
	public boolean update(MdtGradeItem obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
