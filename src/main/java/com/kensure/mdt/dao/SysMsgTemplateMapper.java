package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.SysMsgTemplate;

/**
 * 短信模板表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysMsgTemplateMapper {
	
	
	public SysMsgTemplate selectOne(Long id);
	
	public List<SysMsgTemplate> selectByIds(Collection<Long> ids);
	
	
	public List<SysMsgTemplate> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysMsgTemplate obj);	
	
	
	public boolean update(SysMsgTemplate obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
