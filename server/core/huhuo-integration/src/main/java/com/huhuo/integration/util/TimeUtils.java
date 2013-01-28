package com.huhuo.integration.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {
	
	private static String patternShort = "yyyy-MM-dd";
	
	private static String patternLong = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Adds or subtracts the specified amount of time to the given calendar field,
     * based on the calendar's rules. For example, to subtract 5 days from
     * the current time of the calendar, you can achieve it by calling:
     * <p><code>add(Calendar.DAY_OF_MONTH, -5)</code>.
	 * @param offset 偏离量（负数则为baseTime之前offset个小时的时间）
	 * @param baseTime 基准时间
	 * @return 返回baseTime为基准，偏离offset小时后的时间
	 */
	public static long getHour(int offset, long baseTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(baseTime);
		c.add(Calendar.HOUR_OF_DAY, offset);
		return c.getTimeInMillis();
	}
	/**
	 * @see #getDate(int, long)
	 */
	public static long getHour(int offset, Date baseTime) {
		return getHour(offset, baseTime.getTime());
	}
	/**
	 * @param offset 偏离量（负数则为baseTime之前offset天的时间）
	 * @param baseTime 基准时间
	 * @return 返回baseTime为基准，偏离offset天后的时间
	 */
	public static long getDate(int offset, long baseTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(baseTime);
		c.add(Calendar.DATE, offset);
		return c.getTimeInMillis();
	}
	/**
	 * @see #getDate(int, long)
	 */
	public static long getDate(int offset, Date baseTime) {
		return getDate(offset, baseTime.getTime());
	}
	/**
	 * 
	 * @param offset 偏离量（负数则为baseTime之前offset分钟的时间）
	 * @param baseTime 基准时间
	 * @return 返回baseTime为基准，偏离offset分钟后的时间
	 */
	public static long getMinute(int offset, long baseTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(baseTime);
		c.add(Calendar.MINUTE, offset);
		return c.getTimeInMillis();
	}
	/**
	 * @see #getMinute(int, long)
	 */
	public static long getMinute(int offset, Date baseTime) {
		return getMinute(offset, baseTime.getTime());
	}
	/**
	 * 获取距离baseTime偏离offset个月的日期
	 * @param offset
	 * @param baseTime
	 * @return
	 */
	public static Date getMonth(int offset, Date baseTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(baseTime);
		c.add(Calendar.MONTH, offset);
		return c.getTime();
	}
	/**
	 * 根据字符串date转化成所需的日期
	 * @param date 格式：yyyy-MM-dd HH:mm:ss
	 * @return 如果出现异常，则返回null
	 */
	public static Date getLongDate(String date) {
		SimpleDateFormat f = new SimpleDateFormat();
		try {
			f.applyPattern(patternLong);
			return f.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据字符串date转化成所需的日期
	 * @param date 格式：yyyy-MM-dd
	 * @return 如果出现异常，则返回null
	 */
	public static Date getShortDate(String date) {
		SimpleDateFormat f = new SimpleDateFormat();
		try {
			f.applyPattern(patternShort);
			return f.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**计算给定Calendar的日期是当年第几天,如果是1月1日则返回去年的天数。
	 * @param offset （负数则为Calendar之前offset天）
	 * @param c
	 * @return
	 */
	public static int getDaysOfYear(int offset,Calendar c){
		int days = c.get(Calendar.DAY_OF_YEAR)+offset;
		if(days==0){
			c.set(Calendar.YEAR, c.get(Calendar.YEAR)-1);
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DAY_OF_MONTH,31);
			days=c.get(Calendar.DAY_OF_YEAR);
		}
		return days;
	}
	/**计算时间差
	 * @param startTime
	 * @param endTime
	 * @return 两个时间相差的毫秒数
	 */
	public static long dateDiff(Date startTime,Date endTime){
		Calendar sc = GregorianCalendar.getInstance();
		Calendar ec = GregorianCalendar.getInstance();
		sc.setTime(startTime);
		ec.setTime(endTime);
		long dateDiff = ec.getTimeInMillis()-sc.getTimeInMillis();
		return dateDiff;
	}
	/**
	 * 将字符串date按照格式pattern转换成日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将日期date按照格式pattern转换成日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String parse(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 获取日期date的开始时间，如2012-06-25 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getDateBeginFor(Date date) {
		if(date == null)
			return null;
		String dateStr = parse(date, patternShort);
		return parse(dateStr, patternShort);
	}
	/**
	 * 获取日期date的结束时间，如2012-06-25 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getDateEndFor(Date date) {
		if(date == null)
			return null;
		long yesterday = getDate(1, getDateBeginFor(date));
		yesterday = yesterday - 1000;
		return new Date(yesterday);
	}
	/**
	 * 获取date所在的天次（如2012-06-18，则为18）
	 * @param date
	 * @return
	 */
	public static Integer getDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获取date所在的周次
	 * @param date
	 * @return
	 */
	public static Integer getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	/**
	 * 获取date所在的月份
	 * @param date
	 * @return
	 */
	public static Integer getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}
	/**
	 * 获取date所在的年份
	 * @param date
	 * @return
	 */
	public static Integer getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		return c.get(Calendar.YEAR);
	}
	/**
	 * 获取某年某周的开始日期
	 * @param year 如果为null，则默认为今天所在的年份
	 * @param week
	 * @return
	 */
	public static Date getWeekBegin(Integer year, Integer week) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		if(year == null) {
			c.setTime(new Date());
			year = c.get(Calendar.YEAR);
		}
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getDateBeginFor(c.getTime());
	}
	/**
	 * 获取date所在周次的开始日期
	 * @param date
	 * @return
	 * @see #getWeekBegin(Integer, Integer)
	 */
	public static Date getWeekBegin(Date date) {
		Integer week = getWeek(date);
		Integer year = getYear(date);
		return getWeekBegin(year, week);
	}
	/**
	 * 获取某年某周的结束日期
	 * @param year 如果为null，则默认为今天所在的年份
	 * @param week
	 * @return
	 */
	public static Date getWeekEnd(Integer year, Integer week) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		if(year == null) {
			c.setTime(new Date());
			year = c.get(Calendar.YEAR);
		}
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getDateEndFor(c.getTime());
	}
	/**
	 * 获取date所在周次的结束日期
	 * @param date
	 * @return
	 * @see #getWeekEnd(Integer, Integer)
	 */
	public static Date getWeekEnd(Date date) {
		Integer week = getWeek(date);
		Integer year = getYear(date);
		return getWeekEnd(year, week);
	}
	/**
	 * 获取date所在月的第一天（如，2012-06-01 00:00:00）
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 01);
		return getDateBeginFor(c.getTime());
	}
	/**
	 * 获取date所在月的第最后一天（如，2012-06-30 23:59:59）
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(getMonthBegin(date));
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return getDateEndFor(c.getTime());
	}
	/**
	 * 格式化date
	 * @param date
	 * @param fullPattern 如果fullPattern==true，则返回yyyy-MM-dd HH:mm:ss长格式日期，否则返回yyyy-MM-dd短格式日期
	 * @return
	 */
	public static String format(Date date, boolean fullPattern) {
		String dateStr = null;
		if(fullPattern) {
			dateStr = parse(date, patternLong);
		} else {
			dateStr = parse(date, patternShort);
		}
		return dateStr;
	}
	/**
	 * @see #format(Date, boolean)
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, true);
	}
	/**
	 * @see #format(Date)
	 * @param date
	 * @return
	 */
	public static String format(Long date) {
		return format(new Date(date), true);
	}
	
	public static void main(String[] args) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date today = new Date();
		System.out.println("今天所在周次：" + getWeek(today));
		System.out.println("dfsd  " + parse("2011-12-30", "yyyy-MM-dd"));
		System.out.println("2011-12-25所在周次：" + getWeek(parse("2011-12-25", "yyyy-MM-dd")));
		System.out.println("2012-01-01所在周次：" + getWeek(parse("2012-01-01", "yyyy-MM-dd")));
		System.out.println("2012-01-02所在周次：" + getWeek(parse("2012-01-02", "yyyy-MM-dd")));
		System.out.println("2012-01-08所在周次：" + getWeek(parse("2012-01-08", "yyyy-MM-dd")));
		
		System.out.println("今天所在周的周一：" + parse(getWeekBegin(2012, 25), pattern));
		System.out.println("今天所在周的周日：" + parse(getWeekEnd(2012, 25), pattern));
		
		System.out.println("今天所在周的周日：" + parse(getWeekEnd(2012, 27), pattern));
		
		System.out.println("今天所在周的周一：" + parse(getWeekBegin(today), pattern));
		System.out.println("今天所在周的周日：" + parse(getWeekEnd(today), pattern));
		
		System.out.println(getWeek(parse("2012-01-03", "yyyy-MM-dd")));
		System.out.println(getWeek(parse("2012-01-02", "yyyy-MM-dd")));
		System.out.println(getWeek(parse("2012-01-01", "yyyy-MM-dd")));
		System.out.println(getWeek(parse("2011-12-31", "yyyy-MM-dd")));
		
		System.out.println(getWeekBegin(parse("2012-06-24", "yyyy-MM-dd")).getTime());
		System.out.println(getWeekEnd(new Date()).getTime());
		
	}
}
