package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.SysFile;

/**
 * 文件表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface SysFileMapper {
	
	
	public SysFile selectOne(Long id);
	
	public List<SysFile> selectByIds(Collection<Long> ids);
	
	
	public List<SysFile> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(SysFile obj);	
	
	
	public boolean update(SysFile obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
