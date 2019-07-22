package com.kensure.basekey;

import co.kensure.conn.ConnUtils;
import co.kensure.exception.BusinessExceptionUtil;
import co.kensure.mem.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 主键表服务实现类
 * 
 * @author fankd created on 2018-3-13
 * @since
 */
@Service
public class BaseKeyService {

	private final static Logger LOGGER = Logger.getLogger(BaseKeyService.class);

	private final static String updateSQL = " update base_key set nowid=? ,update_date=? where id=?";
	private final static String insertSQL = " insert into base_key (id,nowid,create_date,update_date) values(?,?,?,?)";
	private final static String selectSQL = " select * from base_key where id=?";

	// 主键步长
	private final static long deptsize = 100;

	@Resource
	private DataSource dataSource;

	public boolean insert(BaseKey obj) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(insertSQL);
			Timestamp time = new Timestamp(System.currentTimeMillis());
			pstmt.setString(1, obj.getId());
			pstmt.setString(2, obj.getMaxid() + "");
			pstmt.setTimestamp(3, time);
			pstmt.setTimestamp(4, time);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e);
			BusinessExceptionUtil.threwException(e);
		} finally {
			ConnUtils.close(pstmt, conn);
		}
		return true;
	}

	/**
	 * 不和业务保存事物一致性，自己管理连接
	 * 
	 * @param obj
	 * @return
	 */
	public boolean update(BaseKey obj) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(updateSQL);
			Timestamp time = new Timestamp(System.currentTimeMillis());
			pstmt.setString(1, obj.getMaxid() + "");
			pstmt.setTimestamp(2, time);
			pstmt.setString(3, obj.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e);
			BusinessExceptionUtil.threwException(e);
		} finally {
			ConnUtils.close(pstmt, conn);
		}
		return true;
	}

	/**
	 * 查询这个记录
	 * 
	 * @param obj
	 * @return
	 */
	public BaseKey selectOne(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BaseKey bk = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bk = new BaseKey();
				bk.setId(id);
				bk.setNowid(NumberUtils.parseLong(rs.getString("nowid"), null));
			}
		} catch (SQLException e) {
			LOGGER.error(e);
			BusinessExceptionUtil.threwException(e);
		} finally {
			ConnUtils.close(pstmt, conn);
		}
		return bk;
	}

	/**
	 * 根据表名获取 主键
	 * 
	 * @param tableName
	 * @return
	 */
	public Long getKey(String tableName) {
		// 优先从缓存里面拿，没有就从数据库拿
		Long key = getKeyCache(tableName);
		if (key == null) {
			key = getKeyTable(tableName);
		}
		return key;
	}

	public void clearCache() {
		keyMap.clear();
	}

	private static Map<String, BaseKey> keyMap = new HashMap<>();

	/**
	 * 优先从缓存里面取
	 * 
	 * @return
	 */
	private synchronized Long getKeyCache(String tableName) {
		BaseKey basekey = keyMap.get(tableName);
		Long id = null;
		if (basekey == null) {
			return null;
		} else {
			id = basekey.getNowid();
			// 如果大于最大值了，就不能取了
			if (id.compareTo(basekey.getMaxid()) == 1) {
				return null;
			}
			Long nextId = id + 1;
			basekey.setNowid(nextId);
		}
		return id;
	}

	/**
	 * 其次从数据库里面取
	 * 
	 * @return
	 */
	private synchronized Long getKeyTable(String tableName) {
		// 再次从缓存里面取下，这么做是为了防止高并发
		Long id = getKeyCache(tableName);
		if (id != null) {
			return id;
		}
		BaseKey bk = selectOne(tableName);
		// 没有，需要创建
		if (bk == null) {
			bk = new BaseKey();
			bk.setId(tableName);
			bk.setNowid(1l);
			bk.setMaxid(deptsize);
			bk.setMinid(1l);
			insert(bk);
		} else {
			Long lastId = bk.getNowid();
			bk.setNowid(lastId + 1);
			bk.setMaxid(lastId + deptsize);
			bk.setMinid(lastId + 1);
			update(bk);
		}
		Long nextId = bk.getNowid();
		bk.setNowid(bk.getNowid() + 1);
		keyMap.put(tableName, bk);
		return nextId;
	}

}
