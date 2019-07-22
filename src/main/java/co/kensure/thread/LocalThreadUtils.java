package co.kensure.thread;

/**
 * 线程变量工具类
 *
 * @author fankd
 */

public class LocalThreadUtils {

	/**
	 * 本地会话的一些信息
	 */
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

	/**
	 * 获取本地信息
	 */
	public static Session getSession() {
		return threadSession.get();
	}

	/**
	 * 设置本地信息
	 */
	public static void putSession(Session se) {
		threadSession.set(se);
	}

	public static void clear() {
		threadSession.set(null);
	}
}
