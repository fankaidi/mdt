
package com.kensure.mdt.service;

import co.kensure.mem.MapUtils;
import com.kensure.mdt.dao.SysFeeMapper;
import com.kensure.mdt.entity.MdtApplyDoctor;
import com.kensure.mdt.entity.SysFee;
import com.kensure.mdt.entity.SysGrade;
import com.kensure.mdt.service.SysFeeService;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * MDT收费情况维护表服务实现类
 */
@Service
public class SysFeeService {
	
	@Resource
	private SysFeeMapper dao;
    
    
    public SysFee selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<SysFee> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<SysFee> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(SysFee obj){
		obj.setCreateTime(new Date());
		obj.setUpdateTime(new Date());
		return dao.insert(obj);
	}
	
	
	public boolean update(SysFee obj){
		obj.setUpdateTime(new Date());
		return dao.update(obj);
	}
    
    public boolean updateByMap(Map<String, Object> params){
		return dao.updateByMap(params);
	}
    
    
	public boolean delete(Long id){
		return dao.delete(id);
	}	
	
    public boolean deleteMulti(Collection<Long> ids){
		return dao.deleteMulti(ids);
	}
    
    public boolean deleteByWhere(Map<String, Object> parameters){
		return dao.deleteByWhere(parameters);
	}
    
    public List<SysFee> selectAll() {

		Map<String, Object> parameters = MapUtils.genMap();
        List<SysFee> list = selectByWhere(parameters);

        return list;
    }

    /**
     * 计算费用
     */
    public Long calculateFee(Integer deptNums) {

        List<SysFee> sysFees = selectAll();


        for (SysFee sysFee : sysFees) {
            if (deptNums >= sysFee.getMin() && (sysFee.getMax() == null || deptNums <= sysFee.getMax())) {
                return sysFee.getPrice();
            }
        }


        return 0L;
    }

    public List<SysFee> selectList() {

		Map<String, Object> parameters = MapUtils.genMap();
		List<SysFee> list = selectByWhere(parameters);
		return list;
    }

	public void save(SysFee fee) {

    	insert(fee);
	}
}
