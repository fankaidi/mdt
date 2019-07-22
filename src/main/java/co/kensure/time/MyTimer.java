package co.kensure.time;

import java.util.Timer;

/**
 * 定时任务包装类，供业务调用
 * @author fankd
 */
public class MyTimer{

	private MyTimerTasker task; // 需要执行的任务
	private Timer ttask = new Timer(true);		



	/**
	 * 设置需要执行的任务
	 * 
	 * @param task
	 *            任务对象 需实现MyTimerTasker接口
	 * @return 当前对象
	 */
	public MyTimer(MyTimerTasker task){
		this.task = task;
	}
	
	/**
	 * 启动任务
	 */
	public void start(){
		if(task.getIsrun()){
			MyTimerTaskerDec timerdec = new MyTimerTaskerDec(task);
			ttask.schedule(timerdec,task.getFirstTime(),task.getPeriod());
		}	
	}
	
	public void cancel(){
		ttask.cancel();
	}
	
	
}
