package co.kensure.time;
import java.util.TimerTask;

/**
 * 定时任务装饰类，供MyTimer调用
 * @author qiuxs
 */
public class MyTimerTaskerDec extends TimerTask {

	private MyTimerTasker task; // 需要执行的任务
	private boolean hasRun = false; // 当前是否有任务正在运行
	
	@Override
	public void run() {
		try {
			//判断当前任务是否可以多任务同时执行 若不
			//则判断是否当前是否有任务正在执行 有则结束
			if(task.getIsQueue() && hasRun) {
				return;
			}
			hasRun = true;//设置当前状态为有任务正在执行
			task.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hasRun = false;
		}
	}

	/**
	 * 设置需要执行的任务
	 * 
	 * @param task
	 *            任务对象 需实现MyTimerTasker接口
	 * @return 当前对象
	 */
	public MyTimerTaskerDec(MyTimerTasker task){
		this.task = task;
	}
	
}
