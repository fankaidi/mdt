
package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.MdtApplyOpinionMapper;
import com.kensure.mdt.entity.MdtApply;
import com.kensure.mdt.entity.MdtApplyDoctor;
import com.kensure.mdt.entity.MdtApplyOpinion;
import com.kensure.mdt.entity.SysOrg;
import com.kensure.mdt.entity.SysUser;


/**
 * MDT专家意见表服务实现类
 */
@Service
public class MdtApplyOpinionService extends JSBaseService{
	
	@Resource
	private MdtApplyOpinionMapper dao;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysOrgService sysOrgService;
    
    
    public MdtApplyOpinion selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<MdtApplyOpinion> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<MdtApplyOpinion> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(MdtApplyOpinion obj){
		super.beforeInsert(obj);
		return dao.insert(obj);
	}
	
	
	public boolean update(MdtApplyOpinion obj){
		super.beforeUpdate(obj);
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


	public void save(MdtApplyOpinion obj) {
		if (obj.getId() == null) {
			insert(obj);
		} else {
			update(obj);
		}
	}
	
	
    /**
	 * 保存组织科室意见项目
	 */
	public void saveZJYJ(MdtApply apply) {
		//再加
		List<MdtApplyDoctor> doctors = apply.getDoctors();
		for (MdtApplyDoctor doctor : doctors) {
			MdtApplyOpinion yijian = doctor.getZjYiJian();
			yijian.setApplyId(apply.getId());
			yijian.setUserId(doctor.getUserId());
			MdtApplyOpinion old = getApplyOpinion(apply.getId(), doctor.getUserId());
			if(old != null){
				yijian.setId(old.getId());
				update(yijian);
			}else{
				insert(yijian);
			}
		}
	}

	public MdtApplyOpinion getApplyOpinion(Long applyId, Long userId) {

		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId, "userId", userId);
		List<MdtApplyOpinion> list = selectByWhere(parameters);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public List<MdtApplyOpinion> getApplyOpinion(Long applyId) {
		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId);
		List<MdtApplyOpinion> list = selectByWhere(parameters);
		for (MdtApplyOpinion mdtApplyOpinion : list) {
            SysUser sysUser = sysUserService.selectOne(mdtApplyOpinion.getUserId());
            if (sysUser != null) {
                mdtApplyOpinion.setUsername(sysUser.getName());
                SysOrg sysOrg = sysOrgService.selectOne(sysUser.getDepartment());
                if (sysOrg != null) {

                    mdtApplyOpinion.setDepartment(sysOrg.getName());
                }
            }
        }
		return list;
	}
}
