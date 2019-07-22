package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.frame.JSBaseService;
import co.kensure.mem.CollectionUtils;
import co.kensure.mem.MapUtils;

import com.kensure.mdt.dao.SysFileItemMapper;
import com.kensure.mdt.entity.SysFile;
import com.kensure.mdt.entity.SysFileItem;

/**
 * 文件项表服务实现类
 */
@Service
public class SysFileItemService extends JSBaseService {

	@Resource
	private SysFileItemMapper dao;

	public SysFileItem selectOne(Long id) {
		return dao.selectOne(id);
	}

	public List<SysFileItem> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<SysFileItem> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(SysFileItem obj) {
		super.beforeInsert(obj);
		return dao.insert(obj);
	}

	public boolean update(SysFileItem obj) {
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
	
	public List<SysFileItem> selectByFileId(Long fileId) {
		Map<String, Object> parameters = MapUtils.genMap("fileId", fileId);
		return selectByWhere(parameters);
	}
	

	public void save(SysFile file) {
		if (file.getId() == null) {
			BusinessExceptionUtil.threwException("错误代码");
		}
		Map<String, Object> parameters = MapUtils.genMap("fileId", file.getId());
		deleteByWhere(parameters);

		List<SysFileItem> list = file.getFileList();
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (SysFileItem item : list) {
			item.setFileId(file.getId());
			insert(item);
		}

	}

}
