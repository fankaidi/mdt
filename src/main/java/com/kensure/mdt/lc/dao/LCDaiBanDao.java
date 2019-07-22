/*
 * 文件名称: LCDaiBanDao.java
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
import com.kensure.mdt.lc.model.LCDaiBan;

/**
 * 待办 Dao接口类
 * 
 * @author fankd created on 2019-7-20
 * @since 
 */
 @MyBatisRepository
public interface LCDaiBanDao extends JSBaseDao<LCDaiBan> {
	
	
	public LCDaiBan selectOne(Long id);
	
	public List<LCDaiBan> selectByIds(Collection<Long> ids);
	
	public List<LCDaiBan> selectAll();
	
	public List<LCDaiBan> selectByWhere(Map<String, Object> parameters);
	
	
	public long selectCount();
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(LCDaiBan obj);
	
	public boolean insertInBatch(List<LCDaiBan> objs);
	
	
	public boolean update(LCDaiBan obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
