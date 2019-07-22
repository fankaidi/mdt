package co.kensure.tag;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量标签类
 * 
 * @author fankaidi
 *
 */
public class StaticTag extends SimpleTagSupport {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 将StaticVal中的值，放入map中，避免反复映射，消耗性能
	 */
	private static Map<String, String> valMap = new HashMap<String, String>();
	static {
		StaticVal bean = new StaticVal();
		Class<StaticVal> s = StaticVal.class;
		Field[] fs = s.getDeclaredFields();
		for (Field f : fs) {
			try {
				String dd = (String) f.get(bean);
				valMap.put(f.getName(), dd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doTag() throws JspException, IOException {
		/* 从属性中使用消息 */
		JspWriter out = getJspContext().getOut();
		String value = valMap.get(this.name);
		out.print(value);

	}

	// public static void main(String args[]) {
	// StaticVal bean = new StaticVal();
	// Class<StaticVal> s = StaticVal.class;
	// Field[] fs = s.getDeclaredFields();
	// for(Field f:fs){
	// try {
	// Object dd = f.get(bean);
	// System.out.print(dd);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
}
