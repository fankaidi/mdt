package com.kensure.mdt.dao;
import org.apache.ibatis.annotations.Mapper;
import co.kensure.annotation.MyBatisRepository;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.entity.MdtTeamPaper;

/**
 * MDT团队论文表Dao接口类
 * 
 */
@Mapper
@MyBatisRepository
public interface MdtTeamPaperMapper {
	
	
	public MdtTeamPaper selectOne(Long id);
	
	public List<MdtTeamPaper> selectByIds(Collection<Long> ids);
	
	
	public List<MdtTeamPaper> selectByWhere(Map<String, Object> parameters);
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtTeamPaper obj);	
	
	
	public boolean update(MdtTeamPaper obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
