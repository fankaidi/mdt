package com.kensure.lc.service;

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

import com.kensure.basekey.BaseKeyService;
import com.kensure.lc.dao.LCProcessDao;
import com.kensure.lc.model.LCDefine;
import com.kensure.lc.model.LCProcess;
import com.kensure.lc.model.LCProcessItem;
import com.kensure.mdt.entity.AuthUser;

/**
 * 流程实例服务实现类
 * 
 * @author fankd created on 2019-7-22
 * @since
 */
@Service
public class LCProcessService extends JSBaseService {

	@Resource
	private LCProcessDao dao;
	@Resource
	private BaseKeyService baseKeyService;
	@Resource
	private LCDefineService lCDefineService;
	@Resource
	private LCProcessItemService lCProcessItemService;
	@Resource
	private LCDaiBanService lCDaiBanService;
	@Resource
	private LCHistoryService lCHistoryService;

	public LCProcess selectOne(Long id) {
		return dao.selectOne(id);
	}

	/**
	 * 详情
	 * 
	 * @param id
	 * @return
	 */
	public LCProcess detail(Long id) {
		LCProcess process = dao.selectOne(id);
		List<LCProcessItem> items = lCProcessItemService.selectByProcessId(id);
		process.setItems(items);
		return process;
	}

	public List<LCProcess> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<LCProcess> selectAll() {
		return dao.selectAll();
	}

	public List<LCProcess> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCount() {
		return dao.selectCount();
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(LCProcess obj) {
		obj.setId(baseKeyService.getKey("lc_process_item"));
		return dao.insert(obj);
	}

	public boolean update(LCProcess obj) {
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
	 * 根据业务id和业务名获取流程
	 * 
	 * @param busiid
	 * @param typeName
	 * @return
	 */
	public LCProcess getProcessByBusi(Long busiid, String typeName) {
		Map<String, Object> parameters = MapUtils.genMap("busiid", busiid, "typeName", typeName);
		List<LCProcess> list = selectByWhere(parameters);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 启动流程 传入流程定义id
	 * 
	 * @return
	 */
	public LCProcess start(Long lcid, AuthUser user) {
		LCDefine define = lCDefineService.detail(lcid);
		LCProcess lcprocess = new LCProcess(define);
		initBase(lcprocess, user);
		insert(lcprocess);

		lCProcessItemService.add(lcprocess, user);
		return lcprocess;
	}

	/**
	 * 启动流程 传入流程定义id
	 * 
	 * @return
	 */
	public LCProcess start(Long lcid, AuthUser user, Long busiid, String typeName) {
		LCProcess lcprocess = start(lcid, user);
		bindBusiid(lcprocess.getId(), busiid, typeName, user);
		return lcprocess;
	}

	/**
	 * 启动流程 之后绑定业务id
	 * 
	 * @return
	 */
	public synchronized void bindBusiid(Long processId, Long busiid, String typeName, AuthUser user) {
		LCProcess old = getProcessByBusi(busiid, typeName);
		if (old != null) {
			BusinessExceptionUtil.threwException("该业务已经绑定，无法二次绑定");
		}
		LCProcess lcprocess = detail(processId);
		lcprocess.setBusiid(busiid);
		lcprocess.setTypeName(typeName);
		update(lcprocess);
		// 获取第一个开始节点
		LCProcessItem curitem = lCProcessItemService.curItem(lcprocess);
		lCProcessItemService.updateItem(curitem, user);

		// 绑定之后启动一次工作流，完成第二个节点
		LCProcessItem item = lCProcessItemService.next(lcprocess);
		lCProcessItemService.updateItem(item, user);
		lcprocess.setCurItemid(item.getItemId());
		update(lcprocess);
	}

	/**
	 * 流程推进，并传入评语\生成待办
	 * 
	 * @param processId
	 * @param content
	 * @param user
	 * @return
	 */
	@Transactional
	public LCProcess next(Long processId, String content, AuthUser user) {
		LCProcess process = detail(processId);
		// 修改当前节点的执行人
		LCProcessItem curitem = lCProcessItemService.curItem(process);
		lCProcessItemService.updateItem(curitem, user);
		// 当前节点进行评语
		lCHistoryService.tongyi(curitem, process, content, user);

		// 让当前节点指向下一个
		LCProcessItem item = lCProcessItemService.next(process);
		process.setCurItemid(item.getItemId());
		update(process);
		// 生成待办
		lCDaiBanService.create(item, process);
		return process;
	}

	/**
	 * 流程退回，并传入评语\生成待办
	 * 
	 * @param processId
	 * @param content
	 * @param user
	 * @return
	 */
	@Transactional
	public LCProcess back(Long processId, String content, AuthUser user) {
		LCProcess process = detail(processId);
		// 修改当前节点的执行人
		LCProcessItem curitem = lCProcessItemService.curItem(process);
		lCProcessItemService.updateItem(curitem, user);
		// 当前节点进行评语
		lCHistoryService.jujue(curitem, process, content, user);

		LCProcessItem item = lCProcessItemService.back(process);
		process.setCurItemid(item.getItemId());
		update(process);
		// 生成待办
		lCDaiBanService.create(item, process);
		return process;
	}

}
