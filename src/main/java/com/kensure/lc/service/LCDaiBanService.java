package com.kensure.lc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.basekey.BaseKeyService;
import com.kensure.lc.dao.LCDaiBanDao;
import com.kensure.lc.model.LCDaiBan;
import com.kensure.lc.model.LCProcess;
import com.kensure.lc.model.LCProcessItem;
import com.kensure.lc.query.LCDaiBanQuery;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysUser;
import com.kensure.mdt.service.SysUserService;

/**
 * 待办 服务实现类
 * 
 * @author fankd created on 2019-7-20
 * @since
 */
@Service
public class LCDaiBanService extends JSBaseService {

	@Resource
	private LCDaiBanDao dao;
	@Resource
	private BaseKeyService baseKeyService;
	@Resource
	private SysUserService sysUserService;

	public LCDaiBan selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<LCDaiBan> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<LCDaiBan> selectAll() {
		return dao.selectAll();
	}

	public List<LCDaiBan> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCount() {
		return dao.selectCount();
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(LCDaiBan obj) {
		Long key = baseKeyService.getKey("lc_daiban");
		obj.setId(key);
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean update(LCDaiBan obj) {
		return dao.update(obj);
	}

	public boolean updateByMap(Map<String, Object> params) {
		return dao.updateByMap(params);
	}

	public boolean delete(Long id) {
		return dao.delete(id);
	}

	public boolean deleteMulti(Collection<Long> ids) {
		return dao.deleteMulti(ids);
	}

	public boolean deleteByWhere(Map<String, Object> parameters) {
		return dao.deleteByWhere(parameters);
	}

	public void add(LCDaiBan obj) {
		insert(obj);
	}

	/**
	 * 获取用户的某一个业务的代办
	 * 
	 * @param parameters
	 * @return
	 */
	public void deleteByBisiid(Long bisiid, String busitype) {
		Map<String, Object> parameters = MapUtils.genMap("bisiid", bisiid, "busitype", busitype);
		deleteByWhere(parameters);
	}
	
	/**
	 * 获取用户的某一个业务的代办
	 * 
	 * @param parameters
	 * @return
	 */
	public LCDaiBan getDaibanByBisiid(Long bisiid, String busitype) {
		Map<String, Object> parameters = MapUtils.genMap("bisiid", bisiid, "busitype", busitype);
		List<LCDaiBan> list = selectByWhere(parameters);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		LCDaiBan daiban = list.get(0);
		daiban.setUser(sysUserService.selectOne(daiban.getUserid().longValue()));
		return daiban;
	}

	/**
	 * 获取用户的某一个业务的代办
	 * 
	 * @param parameters
	 * @return
	 */
	public List<LCDaiBan> getUserDaiBanByBusitype(Long userid, String busitype) {
		Map<String, Object> parameters = MapUtils.genMap("busitype", busitype, "userid", userid);
		return selectByWhere(parameters);
	}

	/**
	 * 获取用户的所有代办
	 * 
	 * @param parameters
	 * @return
	 */
	public List<LCDaiBan> getUserDaiBan(AuthUser user, PageInfo page, LCDaiBanQuery query) {
		Map<String, Object> parameters = getUserParam(user, query);
		parameters.put("orderby", "id desc");
		MapUtils.putPageInfo(parameters, page);
		List<LCDaiBan> list = selectByWhere(parameters);
		if (CollectionUtils.isNotEmpty(list)) {
			for (LCDaiBan h : list) {
				// 申请人
				h.setUser(sysUserService.selectOne(h.getApplyPersonId().longValue()));
			}
		}
		return list;
	}

	/**
	 * 获取参数
	 * 
	 * @param user
	 * @param query
	 * @return
	 */
	public Map<String, Object> getUserParam(AuthUser user, LCDaiBanQuery query) {
		Map<String, Object> parameters = MapUtils.bean2Map(query, false);
		parameters.put("userid", user.getId());
		if (StringUtils.isNotBlank(query.getNameLike())) {
			List<SysUser> userlist = sysUserService.selectByName(query.getNameLike(), user);
			if (CollectionUtils.isEmpty(userlist)) {
				parameters.put("userid", -1000);
				return parameters;
			}
			List<Long> idList = new ArrayList<>();
			for (SysUser u : userlist) {
				idList.add(u.getId());
			}
			parameters.put("applyPersonIdList", idList);
		}

		return parameters;
	}

	public long getUserDaiBanCount(AuthUser user, LCDaiBanQuery query) {
		Map<String, Object> parameters = getUserParam(user, query);
		return selectCountByWhere(parameters);
	}

	/**
	 * 先删除，后增加代办
	 * 
	 * @param list
	 * @param bisiid
	 * @param table
	 */
	public void liucheng(List<LCDaiBan> list, Long bisiid, String table) {
		// 删除上一步的
		deleteByBisiid(bisiid, table);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (LCDaiBan daiban : list) {
			add(daiban);
		}
	}

	/**
	 * 生成待办
	 */
	public void create(LCProcessItem item, LCProcess process) {
		List<Long> list = new ArrayList<>();
		if (item.getTypeId() == -1) {
			// 如果是结束,啥都不干，等他把信息删除
		} else if (StringUtils.isBlank(item.getRoleId())) {
			// 如果为空，就为流程创建人
			list.add(process.getCreatedUserid());
		} else {
			list = sysUserService.selectByRoleCode(item.getRoleId(), process, process.getBusiid());
			if (CollectionUtils.isEmpty(list)) {
				BusinessExceptionUtil.threwException("找不到相应人员");
			}
		}

		// 获取科室主任
		List<LCDaiBan> daibanlist = new ArrayList<>();
		for (Long uid : list) {
			LCDaiBan daiban = new LCDaiBan();
			daiban.setApplyPersonId(process.getCreatedUserid().intValue());
			daiban.setBisiid(process.getBusiid());
			daiban.setEntryName(item.getEntryName());
			daiban.setTitle(item.getEntryName());
			daiban.setBusitype(process.getTypeName());
			daiban.setUserid(uid.intValue());
			daibanlist.add(daiban);
		}
		liucheng(daibanlist, process.getBusiid(), process.getTypeName());
	}

}
