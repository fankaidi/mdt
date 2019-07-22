package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtTeamObjective;

/**
 * MDT团队建设责任目标Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtTeamObjectiveMapper {
	
	
	public MdtTeamObjective selectOne(Long id);
	
	public List<MdtTeamObjective> selectByIds(Collection<Long> ids);
	
	
	public List<MdtTeamObjective> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtTeamObjective obj);	
	
	
	public boolean update(MdtTeamObjective obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
