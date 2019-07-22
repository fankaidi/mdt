package co.kensure.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import co.kensure.api.ApiUtil;
import co.kensure.frame.Const;
import co.kensure.time.MyTimer;
import co.kensure.time.MyTimerTasker;

/**
 * 初始化servlet，初始化路径和ip
 *
 * 获取端口这个功能只有在tomcat下能用
 * 
 * @author fankd
 */

public class InitStartUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(InitStartUpServlet.class);

	public static List<MyTimer> timerList = new ArrayList<MyTimer>();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// 设置spring节点
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		ServiceUtil.setContext(context);

		// 初始化根路径
		getRootFilePath(config.getServletContext());

		ApiUtil.init();
		
		// 启动一些定时器守护线程
		try {
			String[] tasknames = ServiceUtil.getBeanNamesForType(MyTimerTasker.class);
			if (tasknames != null && tasknames.length > 0) {
				for (String taskname : tasknames) {
					MyTimerTasker task = (MyTimerTasker) ServiceUtil.getBean(taskname);
					MyTimer taskdec = new MyTimer(task);
					taskdec.start();
					timerList.add(taskdec);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		for (MyTimer t : timerList) {
			t.cancel();
		}
		LOGGER.info("destroy");
		super.destroy();
	}

	/**
	 * 初始化文件路径
	 * 
	 * @param context
	 */
	private void getRootFilePath(ServletContext context) {
		String filePath;
		try {
			filePath = context.getResource("/").getPath();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		Const.ROOT_PATH = filePath;
	}
}
