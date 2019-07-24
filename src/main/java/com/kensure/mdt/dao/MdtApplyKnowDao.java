package com.kensure.mdt.dao;
import co.kensure.annotation.MyBatisRepository;
import co.kensure.frame.JSBaseDao;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.model.MdtApplyKnow;

/**
 * mdt知情同意书Dao接口类
 * 
 * @author fankd created on 2019-7-24
 * @since 
 */
 @MyBatisRepository
public interface MdtApplyKnowDao extends JSBaseDao<MdtApplyKnow> {
	
	
	public MdtApplyKnow selectOne(Long id);
	
	public List<MdtApplyKnow> selectByIds(Collection<Long> ids);
	
	public List<MdtApplyKnow> selectAll();
	
	public List<MdtApplyKnow> selectByWhere(Map<String, Object> parameters);
	
	
	public long selectCount();
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtApplyKnow obj);
	
	public boolean update(MdtApplyKnow obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
