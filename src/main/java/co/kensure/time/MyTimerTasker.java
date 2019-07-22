package co.kensure.time;

import java.util.Date;

/**
 * 定时任务继承这个类
 * @author fankaidi
 *
 */
public interface MyTimerTasker {
	
	/**
	 * 第一次启动的时间
	 */
	public Date getFirstTime();

	/**
	 * 获取触发周期  单位毫秒
	 */
	public long getPeriod();
	
	/**
	 * do something 
	 */
	public void run();

	/**
	 * 该任务是否需要启动
	 */
	public boolean getIsrun();
	
	/**
	 * 是否需要排队
	 * 假设 执行周期为10s 但是运行时间超过10s 此时应排队等待 还是启动新的任务
	 * @return  true:多线程排队等待  false:多线程并发
	 */
	public boolean getIsQueue();
}
