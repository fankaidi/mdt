/*
 * 文件名称: LCHistoryDao.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-7-20
 * 修改内容: 
 */
package com.kensure.mdt.lc.dao;
import co.kensure.annotation.MyBatisRepository;
import co.kensure.frame.JSBaseDao;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.lc.model.LCHistory;

/**
 * 审核意见 Dao接口类
 * 
 * @author fankd created on 2019-7-20
 * @since 
 */
 @MyBatisRepository
public interface LCHistoryDao extends JSBaseDao<LCHistory> {
	
	
	public LCHistory selectOne(Long id);
	
	public List<LCHistory> selectByIds(Collection<Long> ids);
	
	public List<LCHistory> selectAll();
	
	public List<LCHistory> selectByWhere(Map<String, Object> parameters);
	
	
	public long selectCount();
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(LCHistory obj);
	
	public boolean insertInBatch(List<LCHistory> objs);
	
	
	public boolean update(LCHistory obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
