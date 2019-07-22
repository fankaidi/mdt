package com.kensure.lc.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;

import com.kensure.basekey.BaseKeyService;
import com.kensure.lc.dao.LCProcessItemDao;
import com.kensure.lc.model.LCProcess;
import com.kensure.lc.model.LCProcessItem;
import com.kensure.mdt.entity.AuthUser;

/**
 * 流程实例节点服务实现类
 * 
 * @author fankd created on 2019-7-22
 * @since
 */
@Service
public class LCProcessItemService extends JSBaseService {

	@Resource
	private LCProcessItemDao dao;
	@Resource
	private BaseKeyService baseKeyService;

	public LCProcessItem selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<LCProcessItem> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<LCProcessItem> selectAll() {
		return dao.selectAll();
	}

	public List<LCProcessItem> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCount() {
		return dao.selectCount();
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(LCProcessItem obj) {
		obj.setId(baseKeyService.getKey("lc_process_item"));
		return dao.insert(obj);
	}

	public boolean update(LCProcessItem obj) {
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

	/**
	 * 保存流程节点信息
	 * 
	 * @return
	 */
	public void add(LCProcess lcprocess, AuthUser user) {
		for (LCProcessItem item : lcprocess.getItems()) {
			item.setProcessId(lcprocess.getId());
			initBase(item, user);
			insert(item);
		}
	}

	public List<LCProcessItem> selectByProcessId(Long processId) {
		Map<String, Object> parameters = MapUtils.genMap("processId", processId);
		return selectByWhere(parameters);
	}

	/**
	 * 找到下一个
	 * 
	 * @param process
	 * @return
	 */
	public LCProcessItem next(LCProcess process) {
		Long curItemId = process.getCurItemid();
		List<LCProcessItem> items = process.getItems();
		// 当前节点
		LCProcessItem curItem = curItem(items, curItemId);
		if (curItem.getTypeId() == -1) {
			BusinessExceptionUtil.threwException("当前已经是结束节点，没有下一个了");
		}
		LCProcessItem nextItem = curItem(items, curItem.getNextId());
		return nextItem;
	}

	/**
	 * 找到退回的节点，有可能是上一个，也有可能是发起节点
	 * 
	 * @param process
	 * @return
	 */
	public LCProcessItem back(LCProcess process) {
		Long curItemId = process.getCurItemid();
		List<LCProcessItem> items = process.getItems();
		// 当前节点
		LCProcessItem curItem = curItem(items, curItemId);
		if (curItem.getBackId() == 0) {
			BusinessExceptionUtil.threwException("当前已经是开始节点，没有上一个了");
		}
		if (curItem.getBackType() == -1) {
			BusinessExceptionUtil.threwException("当前节点不允许退回");
		}
		LCProcessItem backItem = null;
		if (curItem.getBackType() == 1) {
			// 退回到开始后的那个节点
			LCProcessItem kaishi = curItem(items, null);
			backItem = curItem(items, kaishi.getNextId());
		} else {
			backItem = curItem(items, curItem.getBackId());
		}
		return backItem;
	}

	/**
	 * 找到退回的节点，有可能是上一个，也有可能是发起节点
	 * 
	 * @param process
	 * @return
	 */
	public LCProcessItem curItem(LCProcess process) {
		Long curItemId = process.getCurItemid();
		List<LCProcessItem> items = process.getItems();
		// 当前节点
		LCProcessItem curItem = curItem(items, curItemId);
		return curItem;
	}

	/**
	 * 根据itemid找到这个节点
	 * 
	 * @param process
	 * @return
	 */
	public LCProcessItem curItem(List<LCProcessItem> items, Long curItemId) {
		LCProcessItem curItem = null;
		for (LCProcessItem item : items) {
			if (curItemId == null) {
				// 为空说明刚开始，找到开始
				if (item.getTypeId() == 0) {
					curItem = item;
					break;
				}
			} else {
				if (item.getItemId().compareTo(curItemId) == 0) {
					curItem = item;
				}
			}
		}
		return curItem;
	}

	/**
	 * 执行一个节点
	 */
	public void updateItem(LCProcessItem item, AuthUser user) {
		item.setUserid(user.getId());
		update(item);
	}

}
