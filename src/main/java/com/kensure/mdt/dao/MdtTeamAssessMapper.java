package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtTeamAssess;

/**
 * MDT团队建设期满2年评估表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtTeamAssessMapper {
	
	
	public MdtTeamAssess selectOne(Long id);
	
	public List<MdtTeamAssess> selectByIds(Collection<Long> ids);
	
	
	public List<MdtTeamAssess> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtTeamAssess obj);	
	
	
	public boolean update(MdtTeamAssess obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
