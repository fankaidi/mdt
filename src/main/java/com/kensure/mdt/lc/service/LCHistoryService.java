package com.kensure.mdt.lc.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;

import com.kensure.basekey.BaseKeyService;
import com.kensure.mdt.lc.dao.LCHistoryDao;
import com.kensure.mdt.lc.model.LCHistory;
import com.kensure.mdt.service.SysUserService;

/**
 * 审核意见 服务实现类
 * 
 * @author fankd created on 2019-7-20
 * @since
 */
@Service
public class LCHistoryService extends JSBaseService {

	@Resource
	private LCHistoryDao dao;
	@Resource
	private BaseKeyService baseKeyService;
	@Resource
	private SysUserService sysUserService;

	public LCHistory selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<LCHistory> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<LCHistory> selectAll() {
		return dao.selectAll();
	}

	public List<LCHistory> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCount() {
		return dao.selectCount();
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(LCHistory obj) {
		Long key = baseKeyService.getKey("lc_history");
		obj.setId(key);
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean insertInBatch(List<LCHistory> objs) {
		return dao.insertInBatch(objs);
	}

	public boolean update(LCHistory obj) {
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

	/**
	 * 根据业务id,获取业务
	 * 
	 * @param parameters
	 * @return
	 */
	public List<LCHistory> selectByBusiid(String busitype, Long bisiid) {
		Map<String, Object> parameters = MapUtils.genMap("busitype", busitype, "bisiid", bisiid, "orderby", "id desc");
		List<LCHistory> list = selectByWhere(parameters);
		if (CollectionUtils.isNotEmpty(list)) {
			for (LCHistory h : list) {
				h.setUser(sysUserService.selectOne(h.getUserid()));
			}
		}
		return list;
	}

}
