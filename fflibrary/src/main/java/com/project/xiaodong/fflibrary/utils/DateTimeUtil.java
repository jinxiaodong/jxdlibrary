package com.project.xiaodong.fflibrary.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtil {

	public static final String CNYMD = "%s年%s月%s日";
	public static final String HYPHEN_YMD = "%s-%s-%s";
	public static final String SLASH_YMD = "%s/%s/%s";
	public static final String HYPHEN_YMDHM = "%s-%s-%s %s:%s";
	public static final String SLASH_YMDHM = "%s/%s/%s %s:%s";
	public static final String HYPHEN_YMDHMS = "%s-%s-%s %s:%s:%s";
	public static final String SLASH_YMDHMS = "%s/%s/%s %s:%s:%s";
	public static final String CN_YMDHM = "%s年%s月%s日 %s时%s分";
	public static final String JP_YMDHM = "%s年%s月%s日 %s時%s分";
	public static final String CN_YMDHM1 = "%s年%s月%s日 %s:%s";
	public static final String JP_YMDHM1 = "%s年%s月%s日 %s:%s";
	public static final String CN_YMDHMS = "%s年%s月%s日 %s时%s分%s秒";
	public static final String JP_YMDHMS = "%s年%s月%s日 %s時%s分%s秒";
	public static final String CN_YMDHMS1 = "%s年%s月%s日 %s:%s:%s";
	public static final String JP_YMDHMS1 = "%s年%s月%s日 %s:%s:%s";
	
	public static final String MM_dd_HH_mm = "MM-dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String MM_DD = "MM月dd日";
	public static final String HH_mm = "HH:mm";
	public static final String YYYY_MM_DD_HH_mm = "yyyy.MM.dd HH:mm";

	private static Calendar calendar;
	private static Date today;

	public DateTimeUtil(){
		if(calendar == null){
			calendar = Calendar.getInstance();
		}
		if(today == null){
			today = new Date();
		}
	}

	/**
	 * getWeek
	 * @return int
	 */
	public int getWeek(){
		today = new Date();
		calendar.setTime(today);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * getWeek
	 * @param baseDay
	 * @return
	 */
	public int getWeek(Date baseDay){
		calendar.setTime(baseDay);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * getMonth
	 * @return int
	 */
	public int getMonth(){
		today = new Date();
		calendar.setTime(today);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * getMonth
	 * @return int
	 */
	public int getMonth(Date baseDay){
		calendar.setTime(baseDay);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * getMonthStr
	 * @return str
	 */
	public String getMonthStr(){
		today = new Date();
		return today.toString().substring(4,7);
	}

	/**
	 * getMonthStr
	 * @param baseDay Date
	 * @return str
	 */
	public String getMonthStr(Date baseDay){
		return baseDay.toString().substring(4,7);
	}

	/**
	 * getYear
	 * @return int
	 */
	public int getYear(){
		today = new Date();
		calendar.setTime(today);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * getYear
	 * @param date
	 * @return
	 */
	public int getYear(Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * getDate
	 * @return int
	 */
	public int getDate(){
		today = new Date();
		calendar.setTime(today);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * getDate
	 * @param date Date
	 * @return int
	 */
	public int getDate(Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * getDaysOfWeek
	 * @return int
	 */
	public int getDayOfWeek(){
		today = new Date();
		calendar.setTime(today);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * getDaysOfWeek
	 * @return int
	 */
	public int getDayOfWeek(Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * canlendar转换星期
	 */
	public static String getDayOfWeek(Calendar c){
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			return "星期天";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";

		default:
			break;
		}
		return null;
	}
	
	/**
	 * getHour
	 * @return int
	 */
	public int getHour(){
		today = new Date();
		calendar.setTime(today);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * getMin
	 * @return int
	 */
	public int getMin(){
		today = new Date();
		calendar.setTime(today);
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * getDayPast
	 * @param daysCount int
	 * @return int
	 */
	public Date getDayPast(int daysCount){
		today = new Date();
		calendar.setTime(today);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-daysCount);
		return calendar.getTime();
	}

	/**
	 * getDayPast
	 * @param baseDay Date
	 * @param daysCount int
	 * @return Date
	 */
	public Date getDayPast(Date baseDay, int daysCount){
		calendar.setTime(baseDay);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-daysCount);
		return calendar.getTime();
	}
	
	public static String getTimeZoneFmt(TimeZone tz, String fmt) {
		try {
			DateFormat df = new SimpleDateFormat(fmt);
			df.setTimeZone(tz);
			return df.format(new Date(System.currentTimeMillis()));

		} catch (Exception e) {
			return "";
		}
	}

	public static String getSysDefaultFmt(String fmt) {
		return getTimeZoneFmt(TimeZone.getDefault(), fmt);
	}

	/**
	 * 字符串转日期
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseStr(String dateStr , String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date resultDate = null;
		try {
			resultDate = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}
	
	/**
	 * 日期转换为字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null || pattern == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	/**
	 * 日期转换为无时间日期
	 * @param date
	 * @return
	 */
	public static Date getDataWithOutTime(Date date) {
		if (date == null) {
			return null;
		}
		String datestr = format(date, YYYY_MM_DD);
		date = parseStr(datestr, YYYY_MM_DD);
		return date;
	}
	
	/**
	 * 根据long时间获取日期
	 * @param time
	 * @return
	 */
	public static int getDateFromTimeMillis(long time){
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time);
		return ca.get(Calendar.DATE);
	}
}