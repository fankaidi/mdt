package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtTeam;

/**
 * MDT团队表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtTeamMapper {
	
	
	public MdtTeam selectOne(Long id);
	
	public List<MdtTeam> selectByIds(Collection<Long> ids);
	
	
	public List<MdtTeam> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtTeam obj);
	
	
	public boolean update(MdtTeam obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
