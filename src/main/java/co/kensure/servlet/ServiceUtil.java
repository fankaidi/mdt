package co.kensure.servlet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * 获取spring的上下文
 * 
 * @author fankaidi
 *
 */
public class ServiceUtil {
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext scontext) {
		context = scontext;
	}

	public static BeanFactory factory;

	public static Object getBean(String name) {
		try {
			Object obj = context.getBean(name);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据接口类，获取bean名数组
	 * 
	 * @param name
	 */
	public static String[] getBeanNamesForType(Class<?> type) {
		String[] result = context.getBeanNamesForType(type);
		return result;
	}
	
	/**
	 * 根据接口类，获取bean名数组
	 * 
	 * @param name
	 */
	public static Object getBean(Class<?> type) {
		Object result = context.getBean(type);
		return result;
	}

}
