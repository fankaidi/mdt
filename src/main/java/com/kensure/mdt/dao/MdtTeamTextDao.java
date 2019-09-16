/*
 * 文件名称: MdtTeamTextDao.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-8-13
 * 修改内容: 
 */
package com.kensure.mdt.dao;
import co.kensure.annotation.MyBatisRepository;
import co.kensure.frame.JSBaseDao;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.model.MdtTeamText;

/**
 * mdt团队文本数据Dao接口类
 * 
 * @author fankd created on 2019-8-13
 * @since 
 */
 @MyBatisRepository
public interface MdtTeamTextDao extends JSBaseDao<MdtTeamText> {
	
	
	public MdtTeamText selectOne(Long id);
	
	public List<MdtTeamText> selectByIds(Collection<Long> ids);
	
	public List<MdtTeamText> selectAll();
	
	public List<MdtTeamText> selectByWhere(Map<String, Object> parameters);
	
	
	public long selectCount();
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtTeamText obj);
	
	public boolean update(MdtTeamText obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
