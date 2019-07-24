package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.MdtApplyKnowDao;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.model.MdtApplyKnow;

/**
 * mdt知情同意书服务实现类
 * 
 * @author fankd created on 2019-7-24
 * @since
 */
@Service
public class MdtApplyKnowService extends JSBaseService {

	@Resource
	private MdtApplyKnowDao dao;
	@Resource
	private MdtApplyService mdtApplyService;

	public MdtApplyKnow selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<MdtApplyKnow> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<MdtApplyKnow> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCount() {
		return dao.selectCount();
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(MdtApplyKnow obj) {
		return dao.insert(obj);
	}

	public boolean update(MdtApplyKnow obj) {
		super.beforeUpdate(obj);
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

	public void save(MdtApplyKnow apply, AuthUser user) {
		MdtApplyKnow obj = selectOne(apply.getId());
		if (obj == null) {
			initBase(apply, user);
			insert(apply);
		} else {
			update(apply);
		}
		mdtApplyService.saveDaYinZhiQing(apply.getApplyId());
	}

	public MdtApplyKnow selectByApplyId(Long applyId) {
		if (applyId == null) {
			return null;
		}
		Map<String, Object> parameters = MapUtils.genMap("applyId", applyId);
		List<MdtApplyKnow> list = selectByWhere(parameters);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

}
