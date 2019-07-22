package com.kensure.mdt.dao;
import co.kensure.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtTeamInfo;

/**
 * MDT团队基本信息表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtTeamInfoMapper {
	
	
	public MdtTeamInfo selectOne(Long id);
	
	public List<MdtTeamInfo> selectByIds(Collection<Long> ids);
	
	
	public List<MdtTeamInfo> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtTeamInfo obj);	
	
	
	public boolean update(MdtTeamInfo obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
