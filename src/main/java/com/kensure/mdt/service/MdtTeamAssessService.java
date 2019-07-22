
package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.MdtTeamAssessMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.MdtTeam;
import com.kensure.mdt.entity.MdtTeamAssess;
import com.kensure.mdt.entity.MdtTeamInfo;
import com.kensure.mdt.entity.SysRole;
import com.kensure.mdt.entity.SysUserRole;
import com.kensure.mdt.lc.model.LCDaiBan;
import com.kensure.mdt.lc.model.LCHistory;
import com.kensure.mdt.lc.service.LCDaiBanService;
import com.kensure.mdt.lc.service.LCHistoryService;


/**
 * MDT团队建设期满2年评估表服务实现类
 */
@Service
public class MdtTeamAssessService extends JSBaseService{
	
	@Resource
	private MdtTeamAssessMapper dao;
	@Resource
	private MdtTeamService mdtTeamService;
	@Resource
	private LCDaiBanService lCDaiBanService;
	@Resource
	private LCHistoryService lCHistoryService;
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysRoleService sysRoleService;
	private static final String table = "mdt_team_assess";
    
    
    public MdtTeamAssess selectOne(Long id){
    	return dao.selectOne(id);
    }
	
	public List<MdtTeamAssess> selectByIds(Collection<Long> ids){
		return dao.selectByIds(ids);
	}
	
	public List<MdtTeamAssess> selectByWhere(Map<String, Object> parameters){
		return dao.selectByWhere(parameters);
	}
	
	public long selectCountByWhere(Map<String, Object> parameters){
		return dao.selectCountByWhere(parameters);
	}
	
	
	public boolean insert(MdtTeamAssess obj){
		return dao.insert(obj);
	}
	
	
	public boolean update(MdtTeamAssess obj){
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


	public MdtTeamAssess getTeamAssess(Long teamId) {
		Map<String, Object> parameters = MapUtils.genMap("teamId", teamId);
		List<MdtTeamAssess> list = selectByWhere(parameters);

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 保存
	 * @param obj
	 * @param user
	 */
	@Transactional
	public void save(MdtTeamAssess obj, AuthUser user) {
		if (obj.getId() == null) {
			initBase(obj, user);
			insert(obj);		
		} else {
			update(obj);
		}
	}
	
	
	/**
	 * 填写第二年的目标
	 */
	@Transactional
	public void tianxie(MdtTeamAssess obj, AuthUser user) {
		save(obj, user);
		mdtTeamService.toAuditTwoYearAssess(obj.getTeamId());

		MdtTeam team = mdtTeamService.selectOne(obj.getTeamId());
		SysRole role = sysRoleService.selectByCode("ywbzr", user.getCreatedOrgid());
		List<SysUserRole> userlist = sysUserRoleService.selectByRoleId(role.getId());
		if (CollectionUtils.isEmpty(userlist)) {
			BusinessExceptionUtil.threwException("找不到对应的医务部主任");
		}
		// 发送待办给医务部
		List<LCDaiBan> daibanlist = new ArrayList<>();
		for (SysUserRole kszruser : userlist) {
			LCDaiBan daiban = new LCDaiBan();
			daiban.setApplyPersonId(team.getCreatedUserid().intValue());
			daiban.setBisiid(team.getId());
			daiban.setEntryName("医务部主任审核");
			daiban.setTitle(team.getName());
			daiban.setBusitype(table);
			daiban.setUserid(kszruser.getUserId().intValue());
			daibanlist.add(daiban);
		}
		lCDaiBanService.liucheng(daibanlist, team.getId(), table);
	}
	
	
	/**
	 * 医务部审核第二年的目标
	 */
	@Transactional
	public void auditTwoYearAssess(MdtTeamAssess obj, AuthUser user) {
		update(obj);
		MdtTeam team = mdtTeamService.selectOne(obj.getTeamId());
		// 意见入库
		LCHistory yijian = obj.getYijian();
		// 科室领导审批
		yijian.setBisiid(team.getId());
		yijian.setBusitype(table);
		yijian.setUserid(user.getId());
		yijian.setEntryName("医务部主任审核");
		lCHistoryService.insert(yijian);
		// 退回走退回逻辑
		if (-1 == yijian.getAuditResult()) {
			// 退回给首席，相当于从新发起
			faqi(obj.getTeamId());
		} else {
			mdtTeamService.auditTwoYearAssess(obj.getTeamId());
			lCDaiBanService.liucheng(null, obj.getTeamId(), table);
		}
	}

	
	
	/**
	 * 发起 MDT团队建设期满2年评估
	 * 
	 * @param teamId
	 */
	@Transactional
	public void faqi(Long teamId) {
		MdtTeam team = mdtTeamService.getDetail(teamId);
		MdtTeamInfo shouxi = team.getMenbers().get(0);

		List<LCDaiBan> daibanlist = new ArrayList<>();

		LCDaiBan daiban = new LCDaiBan();
		daiban.setApplyPersonId(team.getCreatedUserid().intValue());
		daiban.setBisiid(team.getId());
		daiban.setEntryName("首席专家填写");
		daiban.setTitle(team.getName());
		daiban.setBusitype(table);
		daiban.setUserid(shouxi.getUserId().intValue());
		daibanlist.add(daiban);
		lCDaiBanService.liucheng(daibanlist, team.getId(), table);
		mdtTeamService.launchTwoYearAssess(teamId);
	}
}
