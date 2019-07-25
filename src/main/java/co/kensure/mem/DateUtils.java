package co.kensure.mem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import co.kensure.exception.BusinessExceptionUtil;

/**
 * number工具类
 * 
 * @author fankd created on 2017-5-8
 */
public class DateUtils {

	public static String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT1 = "yyyyMMddHHmmss";
	public static String DAY_FORMAT1 = "yyyyMMdd";

	public static String DAY_FORMAT = "yyyy-MM-dd";

	public static String DATE_START = "yyyy-MM-dd 00:00:00";
	public static String DATE_END = "yyyy-MM-dd 23:59:59";

	public static String D06 = "yyyy-MM-dd 06:00:00";
	public static String D07 = "yyyy-MM-dd 07:00:00";
	public static String D10 = "yyyy-MM-dd 10:00:00";
	public static String D14 = "yyyy-MM-dd 14:00:00";
	public static String D15 = "yyyy-MM-dd 15:00:00";
	public static String D20 = "yyyy-MM-dd 20:00:00";

	public static String MOBILE_TIME = "MM-dd HH:mm";

	public static void main(String[] args) {

		// String aa = format(new Date(), D07);

	}

	/**
	 * 功能描述：返回小
	 *
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 以默认格式掩码"yyyy-MM-dd HH:mm:ss"格式化日期
	 * 
	 * @param date
	 *            日期
	 * @return 日期字符串
	 * @author LuoJingtian created on 2012-2-29
	 * @since CDS Framework 1.0
	 */
	public static String format(Date date) {
		return format(date, DATE_FORMAT_PATTERN);
	}

	/**
	 * 获取年
	 */
	public static Integer getYear(Date date) {
		return NumberUtils.parseInteger(format(date, "yyyy"), null);
	}

	/**
	 * 获取月
	 */
	public static Integer getMonth(Date date) {
		return NumberUtils.parseInteger(format(date, "MM"), null);
	}

	/**
	 * 一天的开始一秒
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateStart(Date date) {
		return format(date, DATE_START);
	}

	/**
	 * 一天的最后一秒
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateEnd(Date date) {
		return format(date, DATE_END);
	}

	/**
	 * 以指定格式掩码格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期掩码
	 * @return 日期字符串
	 * @author LuoJingtian created on 2012-2-29
	 * @since CDS Framework 1.0
	 */
	public static String format(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date parse(String dateStr, String pattern) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			BusinessExceptionUtil.threwException(e);
		}
		return date;
	}

	/**
	 * 获取当前时间的+-past小时
	 * 
	 * @param past
	 *            小时
	 * @return date
	 */
	public static Date getPastHour(Date day, int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + past);
		Date pastday = calendar.getTime();
		return pastday;
	}

	/**
	 * 获取当前天数的+-past天数
	 * 
	 * @param past
	 *            天数
	 * @return date
	 */
	public static Date getPastDay(Date day, int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date pastday = calendar.getTime();
		return pastday;
	}

	/**
	 * 获取当前天数的+-past月数
	 * 
	 * @param past
	 *            月数
	 * @return date
	 */
	public static Date getPastMonth(Date day, int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.MONTH, past);
		Date pastday = calendar.getTime();
		return pastday;
	}

	/**
	 * 切分时间
	 * 
	 * @param startCreatedTime
	 * @param endCreatedTime
	 *            切分成一个日起数组 2019-01-01;2019-01-02;2019-01-03
	 */
	public static List<String> cutDate(Date startTime, Date endTime) {
		List<String> list = new ArrayList<String>();
		Date fir = parse(formatDateStart(startTime), DATE_FORMAT_PATTERN);
		while (fir.compareTo(endTime) <= 0) {
			list.add(format(fir, DAY_FORMAT));
			fir = getPastDay(fir, 1);
		}
		return list;
	}

}
