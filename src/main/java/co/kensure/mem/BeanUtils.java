package co.kensure.mem;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * bean工具类
 * 
 * @author fankd created on 2017-5-8
 */
public class BeanUtils {

	/**
	 * 
	 * 将两个bean中Integer类型的值相加,并且返回bean1
	 * 
	 * @author fankd
	 */
	public static void beanSum(Object bean1, Object bean2) {
		if (bean1 == null || bean2 == null) {
			return;
		}
		PropertyDescriptor[] propertyDescriptors1 = PropertyUtils.getPropertyDescriptors(bean1.getClass());

		for (PropertyDescriptor property : propertyDescriptors1) {
			java.lang.String key = property.getName();
			Class<?> type = property.getPropertyType();

			if (type == Integer.class) {
				try {
					Integer value1 = (Integer) PropertyUtils.getProperty(bean1, key);
					if (value1 == null) {
						value1 = 0;
					}
					Integer value2 = (Integer) PropertyUtils.getProperty(bean2, key);
					if (value2 == null) {
						value2 = 0;
					}
					int sum = NumberUtils.integerAdd(value1, value2);
					PropertyUtils.setProperty(bean1, key, sum);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}else if (type == Double.class) {
				try {
					Double value1 = (Double) PropertyUtils.getProperty(bean1, key);
					if (value1 == null) {
						value1 = 0D;
					}
					Double value2 = (Double) PropertyUtils.getProperty(bean2, key);
					if (value2 == null) {
						value2 = 0D;
					}
					double sum = ArithmeticUtils.add(value1, value2);
					PropertyUtils.setProperty(bean1, key, sum);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		}
	}

}
