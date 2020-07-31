package com.commons.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	public static String minusDays(String date, int days) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		LocalDate prevDay = localDate.minusDays(days);
		return prevDay.format(formatter);
	}
	
	

	
	public static String currentDate() {
		// 我要获取当前的日期
		Date date = new Date();
		// 设置要获取到什么样的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获取String类型的时间
		String createdate = sdf.format(date);

		return createdate;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			date = formatter.parse(strTime);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 获取一周中的第一天和最后一天
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getWeekMonAndSun() {
		Map map = new HashMap();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		map.put("monday", df.format(cal.getTime()));
		// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);

		map.put("sunday", df.format(cal.getTime()));
		return map;
	}

	/**
	 * 上一周的第一天
	 * 
	 * @return
	 */
	public static String getMonday() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
		cal.add(Calendar.DATE, -1 * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return monday;
	}

	/**
	 * 上一周的最后一天
	 *
	 */
	public static String getSunday() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
		cal.add(Calendar.DATE, -1 * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return sunday;
	}

	/**
	 * 上一周的第一天
	 * 
	 * @return
	 */
	public static String getBeforeLastMonday() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
		cal.add(Calendar.DATE, -2 * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return monday;
	}

	/**
	 * 上一周的最后一天
	 *
	 */
	public static String getBeforeLastSunday() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
		cal.add(Calendar.DATE, -2 * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return sunday;
	}

}
