/*
 * 文件名称: LCProcessDao.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-7-22
 * 修改内容: 
 */
package com.kensure.lc.dao;
import co.kensure.annotation.MyBatisRepository;
import co.kensure.frame.JSBaseDao;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.lc.model.LCProcess;

/**
 * 流程实例Dao接口类
 * 
 * @author fankd created on 2019-7-22
 * @since 
 */
 @MyBatisRepository
public interface LCProcessDao extends JSBaseDao<LCProcess> {
	
	
	public LCProcess selectOne(Long id);
	
	public List<LCProcess> selectByIds(Collection<Long> ids);
	
	public List<LCProcess> selectAll();
	
	public List<LCProcess> selectByWhere(Map<String, Object> parameters);
	
	
	public long selectCount();
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(LCProcess obj);
	
	public boolean insertInBatch(List<LCProcess> objs);
	
	
	public boolean update(LCProcess obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
