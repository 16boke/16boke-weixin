package com.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期相关函数
 */
public final class DateUtil {
	public static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	// 通用日期格式
	public static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	// 完整日期格式
	public static SimpleDateFormat dateformatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 紧凑日期格式
	public static SimpleDateFormat dateformatShort = new SimpleDateFormat("yyyyMMdd");

	public DateUtil() {

	}

	/**
	 * 根据指定的Date生成相应的Calendar; 每次调用都是一个新的对象，防止单例模式下，线程安全问题
	 */
	public static Calendar getCalendar4Date(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 返回指定日期的真实年份 (如：2011-05-20返回2011)
	 * 
	 * 注意：它与date.getYear()的返回值不同。 当前方法返回的就是真实的年份值，
	 * 而date.getYear()返回的是真实年份减去1900, 所以date.getYear()方法已经废弃
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回年份表示的整型(如：2011)
	 */
	public static int getYear(Date date) {
		Calendar calendar = getCalendar4Date(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回当前日期
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 返回指定日期的真实月份 (如：2011-05-20返回5) 注意：返回值为1-12。1表示1月，2表示2月 与date.getMonth()
	 * 0表示1月不同。
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回表示月份的数值(1-12)
	 */
	public static int getMonth(Date date) {
		Calendar calendar = getCalendar4Date(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回指定日期的日份 （如：2011-5-20返回20)
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回指定日期天
	 */
	public static int getDay(Date date) {
		Calendar calendar = getCalendar4Date(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回指定日期中的小时(24小时制) (如：2011-05-20 18:52:49返回18)
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = getCalendar4Date(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回指定日期中的分钟 (如：2011-05-20 18:52:49 返回 52)
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = getCalendar4Date(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回指定日期中的秒钟 (如：2011-05-20 18:52:49 返回 49)
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = getCalendar4Date(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 返回指定日期(从1970 年 1 月 1 日 00:00:00 )的毫秒数
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar calendar = getCalendar4Date(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 功能描述：日期相加
	 * 
	 * @param date
	 *            Date 日期
	 * @param day
	 *            int 天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	// 获得日期差值
	public static Integer getDiffDay(Date start, Date end) {
		Long diffDay = (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
		return diffDay.intValue() + 1;
	}

	// 获得指定范围内的每一天列表
	public static List<String> getEveryDayBetweenRange(Date start, Date end) {
		List<String> list = new ArrayList<String>();
		for (Long time = start.getTime(); time <= end.getTime(); time += 1000 * 60 * 60 * 24) {
			list.add(dateformat.format(time));
		}
		return list;
	}

	/**
	 * 功能描述：日期相减
	 * 
	 * @param date
	 *            Date 日期
	 * @param date1
	 *            Date 日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 功能描述：日期想减
	 * 
	 * @param date
	 * @param date1
	 * @return 返回想减后的小时
	 */
	public static int diffDateToHour(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (3600 * 1000));
	}

	// /**
	// * 功能描述：取得指定月份的第一天
	// *
	// * @param strdate
	// * String 字符型日期
	// * @return String yyyy-MM-dd 格式
	// */
	// public static String getMonthBegin(String strdate) {
	// date = parseDate(strdate);
	// return format(date, "yyyy-MM") + "-01";
	// }
	//
	// /**
	// * 功能描述：取得指定月份的最后一天
	// *
	// * @param strdate
	// * String 字符型日期
	// * @return String 日期字符串 yyyy-MM-dd格式
	// */
	// public static String getMonthEnd(String strdate) {
	// date = parseDate(getMonthBegin(strdate));
	// calendar = Calendar.getInstance();
	// calendar.setTime(date);
	// calendar.add(Calendar.MONTH, 1);
	// calendar.add(Calendar.DAY_OF_YEAR, -1);
	// return formatDate(calendar.getTime());
	// }

	/**
	 * 用给定的格式解析字符型日期 (如：2011-05-20 18:56:49 yyyy-MM-dd hh:mm:ss)
	 * 
	 * 注意：给定格式必须与给定的字符型日期相匹配
	 * 
	 * @param dateStr
	 *            String 字符型日期
	 * @param format
	 *            String 格式
	 * @return Date 日期
	 * @throws ParseException
	 *             日期串与格式不匹配或日期串不合法时，抛出异常
	 */
	public static Date parseDate(String dateStr, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = (Date) dateFormat.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 解析日期
	public static Date parse(String date, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date result = dateFormat.parse(date);
			return result;
		} catch (ParseException e) {
			logger.warn("Error happened when parsing date: " + date, e);
		}
		return null;
	}

	/**
	 * Timestamp 转换成String
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return 返回字符型日期
	 */
	public static String parseTimestampTOStr(Timestamp timeStamp, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String result = dateFormat.format(timeStamp);
		return result;
	}

	/**
	 * 格式化给定日期
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return 返回字符型日期
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String result = dateFormat.format(date);
		return result;
	}

	/**
	 * 把指定日期解析成Timestamp
	 */
	public static Timestamp parseDateTOTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 用给定的格式解析字符型日期 (如：2011-05-20 18:56:49 yyyy-MM-dd hh:mm:ss)
	 * 
	 * 注意：给定格式必须与给定的字符型日期相匹配
	 * 
	 * @param dateStr
	 *            String 字符型日期
	 * @param format
	 *            String 格式
	 * @return Timestamp 日期
	 * @throws ParseException
	 *             日期串与格式不匹配或日期串不合法时，抛出异常
	 */
	public static Timestamp parseStrTOTimestamp(String dateStr, String format) throws ParseException {
		Date d = parseDate(dateStr, format);
		return new Timestamp(d.getTime());
	}

	/**
	 * 把形如yyyy-MM-dd HH:mm:ss的时间形式转换成Timestamp类型(形如yyyy-MM-dd HH:mm:ss.SSS)
	 */
	public static Timestamp strToTimestamp(String dateStr) {

		Date date = null;
		if (dateStr.length() == 5) {
			String nowDate = format(new Date(), "yyyy-MM-dd");
			dateStr = nowDate + " " + dateStr + ":00";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	/**
	 * 将timestamp类型转化成yyyy-MM-dd格式的字符串
	 */
	public static String timestampToStr(Timestamp timestamp) {
		// 2011-10-11 17:19:09.873
		if (timestamp == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Date date = sdf.parse(timestamp.toString());
			return format(date, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 用给定的格式解析字符型日期 (如：2011-05-20 18:56:49 yyyy-MM-dd hh:mm:ss)
	 * 注意：给定格式必须与给定的字符型日期相匹配
	 */
	public static Timestamp strToTimestamp(String dateStr, String format) {
		Date d = parseDate(dateStr, format);
		return new Timestamp(d.getTime());
	}

	/**
	 * timestamp 格式化为字符串
	 * 
	 * @param timestamp
	 *            时间 format 格式化格式
	 * @return 格式化后的字符串
	 */
	public static String timestampToStr(Timestamp timestamp, String format) {
		// 2011-10-11 17:19:09.873
		if (timestamp == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Date date = sdf.parse(timestamp.toString());
			return format(date, format);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * timestamp 转换为Date
	 */
	public static Date timestampToDate(Timestamp timestamp) {
		// 2011-10-11 17:19:09.873
		if (timestamp == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			return sdf.parse(timestamp.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 返回当前时间的Timestamp类型
	 */
	public static Timestamp nowTimeToTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	// 根据基准时间和时间差（天数）来获取新时间对象；基准时间不会被修改！
	public static Calendar getAnotherDayByInterval(Calendar basedCalendar, int interval) {
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTimeInMillis(basedCalendar.getTimeInMillis());
		newCalendar.add(Calendar.DATE, interval);
		return newCalendar;
	}

	public static String getAnotherDayByInterval(String baseDate, int interval) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateformat.parse(baseDate));
			Date date = getAnotherDayByInterval(calendar, interval).getTime();
			return dateformat.format(date);
		} catch (ParseException e) {
			logger.warn("Error happened when parsing date: " + baseDate, e);
		}
		return null;
	}

	// 获得日期前一天
	public static String getDayBefore(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar = getAnotherDayByInterval(calendar, -1);
		return dateformat.format(calendar.getTime());
	}

	// 获得日期前一天
	public static String getDayBefore(String date) {
		try {
			return getDayBefore(dateformat.parse(date));
		} catch (ParseException e) {
			logger.warn("Error happened when parsing date: " + date, e);
		}
		return null;
	}

	// 获得日期后一天
	public static String getDayAfter(String date) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateformat.parse(date));
			calendar = getAnotherDayByInterval(calendar, 1);
			return dateformat.format(calendar.getTime());
		} catch (Exception ex) {
			logger.warn("Error happened when parsing date: " + date, ex);
		}
		return null;
	}

	/**
	 * 返回本月第一天格式yyyy-MM-dd
	 */
	public static String getMonthBeginStr() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat(" yyyy-MM-dd ");
		return simpleFormate.format(calendar.getTime());

	}

	/**
	 * 返回本月第一天Timestamp
	 */
	public static Timestamp getMonthBeginTimestamp() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * 返回本月第一天格式yyyy-MM-dd
	 */
	public static String getMonthEndStr() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime());
	}

	/**
	 * 返回本月最后一天Timestamp
	 */
	public static Timestamp getMonthEndTimestamp() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * 根据出生日期返回年龄
	 * 
	 * @param birthday
	 *            出生日期
	 * @return 返回年龄
	 */
	public static String getAge(Date birthday) {
		if (birthday == null) {
			return "";
		}
		int days = diffDate(new Date(), birthday);
		return String.valueOf(days / 365);
	}

	/**
	 * 返回当天开始的Timestamp，比如2011-11-16 00:00:00.000
	 */
	public static Timestamp getTodayBeginTimestamp() {
		StringBuffer sb = new StringBuffer(DateUtil.format(DateUtil.getCurrentDate(), "yyyyMMdd")).append("00:00:00");
		return DateUtil.strToTimestamp(sb.toString(), "yyyyMMddHH:mm:ss");
	}

	/**
	 * 返回当天结束的Timestamp，比如2011-11-16 23:59:59.999
	 */
	public static Timestamp getTodayEndTimestamp() {
		StringBuffer sb = new StringBuffer(DateUtil.format(DateUtil.getCurrentDate(), "yyyyMMdd")).append("23:59:59.999");
		return DateUtil.strToTimestamp(sb.toString(), "yyyyMMddHH:mm:ss.SSS");
	}

	public static Timestamp getTimestamp(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return parseDateTOTimestamp(calendar.getTime());
	}

	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}

	/**
	 * 将当前时间 加秒钟
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 比较两个日期
	 * 
	 * @param date1
	 * @param date2
	 * @return date1 大于 date2 返回 1；相等返回0； 小于则返回-1
	 */
	public static int compareDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date d1 = df.parse(date1);
			java.util.Date d2 = df.parse(date2);
			if (d1.getTime() > d2.getTime()) {
				return 1;
			} else if (d1.getTime() < d2.getTime()) {

				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 比较两个日期(允许抛出异常)
	 */
	public static int compareDateThrowException(String date1, String date2) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date d1 = df.parse(date1);
		java.util.Date d2 = df.parse(date2);
		if (d1.getTime() > d2.getTime()) {
			return 1;
		} else if (d1.getTime() < d2.getTime()) {

			return -1;
		} else {
			return 0;
		}
	}

	public static boolean equalOrAfterTargetDate(String sourceDate, String targetDate) {
		boolean result = false;
		try {
			Date date1 = dateformat.parse(sourceDate);
			Date date2 = dateformat.parse(targetDate);
			result = date1.getTime() >= date2.getTime();
		} catch (ParseException e) {
			logger.warn("Error happened when parsing date.", e);
		}
		return result;
	}

	// 获得明天
	public static String getTomorrow(String date) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateformat.parse(date));
			calendar = getAnotherDayByInterval(calendar, 1);
			return dateformat.format(calendar.getTime());
		} catch (Exception ex) {
			logger.warn("Error happened when parsing date: " + date, ex);
		}
		return null;
	}

	public static String getDateCN(Date date) {
		if(date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
	}

	public static String getTimeCN(Date date) {
		if(date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return sdf.format(date);
	}
}
