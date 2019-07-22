package co.kensure.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 连接池工具类
 * 
 * @author fankd
 */
public final class ConnUtils {

	private final static Logger LOGGER = Logger.getLogger(ConnUtils.class);

	/**
	 * 关闭连接池
	 * 
	 * @return
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.error(e);
			}
		}
	}

	/**
	 * 关闭预编译窗口
	 * 
	 * @return
	 */
	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				LOGGER.error(e);
			}
		}
	}

	/**
	 * 关闭预编译窗口，再关闭连接池
	 * 
	 * @return
	 */
	public static void close(PreparedStatement pstmt, Connection conn) {
		close(pstmt);
		close(conn);
	}

}
