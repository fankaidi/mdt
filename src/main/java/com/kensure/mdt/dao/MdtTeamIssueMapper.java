package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtTeamIssue;

/**
 * MDT团队课题表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtTeamIssueMapper {
	
	
	public MdtTeamIssue selectOne(Long id);
	
	public List<MdtTeamIssue> selectByIds(Collection<Long> ids);
	
	
	public List<MdtTeamIssue> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtTeamIssue obj);	
	
	
	public boolean update(MdtTeamIssue obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
