/*
 * 文件名称: MdtApplyTextDao.java
 * 版权信息: Copyright 2001-2017 hangzhou jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2019-9-23
 * 修改内容: 
 */
package com.kensure.mdt.dao;
import co.kensure.annotation.MyBatisRepository;
import co.kensure.frame.JSBaseDao;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import com.kensure.mdt.model.MdtApplyText;

/**
 * 申请大文本内容Dao接口类
 * 
 * @author fankd created on 2019-9-23
 * @since 
 */
 @MyBatisRepository
public interface MdtApplyTextDao extends JSBaseDao<MdtApplyText> {
	
	
	public MdtApplyText selectOne(Long id);
	
	public List<MdtApplyText> selectByIds(Collection<Long> ids);
	
	public List<MdtApplyText> selectAll();
	
	public List<MdtApplyText> selectByWhere(Map<String, Object> parameters);
	
	
	public long selectCount();
	
	public long selectCountByWhere(Map<String, Object> parameters);
	
	
	public boolean insert(MdtApplyText obj);
	
	public boolean insertInBatch(List<MdtApplyText> objs);
	
	
	public boolean update(MdtApplyText obj);
    
    public boolean updateByMap(Map<String, Object> params);
    
    
	public boolean delete(Long id);	
	
    public boolean deleteMulti(Collection<Long> ids);
    
    public boolean deleteByWhere(Map<String, Object> parameters);
	
	
}
