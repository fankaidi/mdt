package com.kensure.mdt.dao;
import co.kensure.annotation.MyBatisRepository;
import com.kensure.mdt.entity.SysFileItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 文件项表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysFileItemMapper {
	
	
	public SysFileItem selectOne(Long id);
	
	public List<SysFileItem> selectByIds(Collection<Long> ids);
	
	
	public List<SysFileItem> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysFileItem obj);	
	
	
	public boolean update(SysFileItem obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
