package com.kensure.mdt.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kensure.frame.JSBaseService;
import co.kensure.mem.MapUtils;
import co.kensure.mem.PageInfo;

import com.kensure.basekey.BaseKeyService;
import com.kensure.mdt.dao.SysFileMapper;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysFile;

/**
 * 文件表服务实现类
 */
@Service
public class SysFileService extends JSBaseService {

	@Resource
	private SysFileMapper dao;
	@Resource
	private BaseKeyService baseKeyService;
	@Resource
	private SysFileItemService sysFileItemService;

	public SysFile selectOne(Long id) {
		return dao.selectOne(id);
	}

	public SysFile detail(Long id) {
		SysFile file = selectOne(id);
		file.setFileList(sysFileItemService.selectByFileId(id));
		return file;
	}

	public List<SysFile> selectByIds(Collection<Long> ids) {
		return dao.selectByIds(ids);
	}

	public List<SysFile> selectByWhere(Map<String, Object> parameters) {
		return dao.selectByWhere(parameters);
	}

	public long selectCountByWhere(Map<String, Object> parameters) {
		return dao.selectCountByWhere(parameters);
	}

	public boolean insert(SysFile obj, AuthUser user) {
		obj.setId(baseKeyService.getKey("sys_file"));
		initBase(obj, user);
		return dao.insert(obj);
	}

	public boolean update(SysFile obj) {
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

	@Transactional
	public void save(SysFile file, AuthUser user) {
		if (file.getId() == null) {
			insert(file, user);
		} else {
			update(file);
		}
		sysFileItemService.save(file);
	}

	public List<SysFile> selectList(PageInfo page, AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		MapUtils.putPageInfo(parameters, page);
		setOrgLevel(parameters, user);
		List<SysFile> list = selectByWhere(parameters);
		return list;
	}

	public long selectListCount(AuthUser user) {
		Map<String, Object> parameters = MapUtils.genMap();
		setOrgLevel(parameters, user);
		return selectCountByWhere(parameters);
	}

}
