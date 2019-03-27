package com.batteryErp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String FORMAT_DETAIL = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_SIMPLE = "yyyy-MM-dd";
	public static final String FORMAT_MONTH = "yyyyMM";
	public static final String FORMAT_MONTH1 = "yyyy-MM";
	public static final String FORMAT_MEDIUM = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_HAOMIAO = "yyyyMMddHHmmssSSS";
	public static final String FORMAT_HAOMIAO1 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_TIME = "HH:mm:ss";
	
	public static String formatDetialDate(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDetailDate(Date date) {
		return formatDate(date, "yyyyMMddHHmmss");
	}
	public static String formatSimpleDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	
	public static String formatMonth(Date date) {
		return formatDate(date, "yyyy-MM");
	}

	public static String formatHaoMiaoDate(Date date) {
		return formatDate(date, "yyyyMMddHHmmssSSS");
	}
	public static String formatMillisecond(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}
	public static String getMoosNow() {
		Date date = new Date();
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatStdDate(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	public static String formatJsonDate(Date date) {
		return formatDate(date, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}
	
	public static String formatMediumDate(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm");
	}

	
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat ft = new SimpleDateFormat(pattern);
		return ft.format(date);
	}

	public static Date convertToDate(String dateStr, String pattern) throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat(pattern);
		return ft.parse(dateStr);
	}

	public static Date convert(String dateStr) throws ParseException {
		SimpleDateFormat ft = null;
		try{
			ft = new SimpleDateFormat(FORMAT_DETAIL);
			return ft.parse(dateStr);
		}
		catch (Exception ex1){
			try{
				ft = new SimpleDateFormat(FORMAT_SIMPLE);
				return ft.parse(dateStr);
			}
			catch(Exception ex2){
				ft = new SimpleDateFormat(FORMAT_HAOMIAO1);
				return ft.parse(dateStr);
			}
		}
	}
	
	 public static Date addDateMinut(Date date ,int x)//返回的是字符串型的时间，输入的
	//是String day, int x
	 {   
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制  
	//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
	//量day格式一致
	        
	        Calendar cal = Calendar.getInstance(); 
	       
	        cal.setTime(date);   
	        cal.add(Calendar.MINUTE, x);// 24小时制   
	        Date date1 = cal.getTime();   
	      
	        return date1;   
	    }  
	 
	 
	public static Date addSeconds(Date date, int seconds)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);// 24小时制
		Date date1 = cal.getTime();
		return date1;
	}
		 
	 
	 public static long getDiffSeconds(Date beginDate ,Date endDate)
	 {   
	        long diff = endDate.getTime() - beginDate.getTime();
	        return diff / 1000;   
	 }  
	 
	 
	 public static Long getDateLong(Date date,int field){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 int year = cal.get(Calendar.YEAR);
		 int month = cal.get(Calendar.MONTH);
		 int mday = cal.get(Calendar.DATE);
		 int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		 if(field == Calendar.DATE){
			 cal.clear();
			 cal.set(year, month, mday);
		 }
		 else if(field == Calendar.HOUR_OF_DAY){
			 cal.clear();
			
			 cal.set(year, month, mday, hour, 0);			
		 }
		 
		 return cal.getTimeInMillis();
		 
	 }
	 
	 public static Long getLong(String date, String pattern) {
		 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		 Date sdate = null;
		try {
			sdate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return sdate.getTime();
	 }
	
	public static Date addDay(Date date,int periods){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE,periods);
			return cal.getTime();
	}
	public static Date addWeek(Date date,int periods){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,periods*7);
		return cal.getTime();
	}
	public static Date addMonth(Date date,int periods){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,periods);
		return cal.getTime();
	}
	public static Date addYear(Date date,int periods){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR,periods);
		return cal.getTime();
	}
	 
	public static Date[] getThisDate(Date date) {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH)+1);
		String mday = String.valueOf(cal.get(Calendar.DATE));
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-M-d H:m:s");
		try {
			dates[0] = ft.parse(year + "-" + month + "-" + mday + " 0:0:0");
			dates[1] = ft.parse(year + "-" + month + "-" + mday + " 23:59:59");
		} catch (ParseException e) {
		}
		return dates;
	}
	

	public static Date[] getThisWeek(Date date) {
		Date[] dates = new Date[2];

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
			cal.add(Calendar.DATE, -1);
		}
		Date d = cal.getTime();
		Date[] ds = getThisDate(d);
		dates[0] = ds[0];
		
		cal.setTime(date);
		while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
			cal.add(Calendar.DATE, 1);
		}
		d = cal.getTime();
		ds = getThisDate(d);
		dates[1] = ds[1];

		return dates;
	}
	
	
	public static Date[] getThisMonth(Date date) {
		Date[] dates = new Date[2];
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-M-d H:m:s");

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		String year = String.valueOf(cal1.get(Calendar.YEAR));
		String month = String.valueOf(cal1.get(Calendar.MONTH)+1);

		try {
			dates[0] = ft.parse(year + "-" + month + "-1 " + " 0:0:0");
		} catch (ParseException e) {
		}

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dates[0]);
		cal2.add(Calendar.MONTH, 1);
		cal2.add(Calendar.SECOND, -1);
		dates[1] = cal2.getTime();

		return dates;
	}
	
	public static Date[] getThisYear(Date date) {
		Date[] dates = new Date[2];
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-M-d H:m:s");

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		String year = String.valueOf(cal1.get(Calendar.YEAR));

		try {
			dates[0] = ft.parse(year + "-1-1 " + " 0:0:0");
		} catch (ParseException e) {
		}

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dates[0]);
		cal2.add(Calendar.YEAR, 1);
		cal2.add(Calendar.SECOND, -1);
		dates[1] = cal2.getTime();

		return dates;
	}
	/**
	 * 转换时间格式为：hh:mm:ss
	 * @param time
	 * @return
	 */
	public static String getFormateTimeOne(long time){
		long h = time/(60*60);
		long m = (time-(h*60*60))/60;
		long s = time-(h*60*60)-(m*60);
		String strH = h>9?""+h:h==0?"00":"0"+h;
		String strm = m>9?""+m:m==0?"00":"0"+m;
		String strs = s>9?""+s:s==0?"00":"0"+s;
		return strH+":"+strm+":"+strs;
	}
	/**
	 * 转换时间格式为：hh时mm分ss秒
	 * @param time
	 * @return
	 */
	public static String getFormateTimeTwo(long time){
		if(time<=0){
			return "0秒";
		}
		long h = time/(60*60);
		long m = (time-(h*60*60))/60;
		long s = time-(h*60*60)-(m*60);
		StringBuilder builder = new StringBuilder();
		if(h>0){
			builder.append(h+"时");
		}
		if(m>0){
			builder.append(m+"分");
		}
		if(s>0){
			builder.append(s+"秒");
		}
		return builder.toString();
	}
	
	public static String getFormateTimeThree(long time){
		if(time<=0){
			return "0分钟";
		}
		long m = time%60==0?time/60:time/60+1;
		return m+"分钟";
	}
	
	
	
	/**
	 * 获取以前某天的日期
	 * @Title: getBeforeDay 
	 * @Description:  
	 * @param date
	 * @param pattern
	 * @param dayNum
	 * @return String
	 * @throws
	 */
	public static String getBeforeDay(String date, String pattern, int dayNum) {// 根据日期求上周日期
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String beforeDay = null;
		try {
			Date rq = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(rq);// .add(rq, -7);
			c.add(Calendar.DATE, dayNum);
			beforeDay = sdf.format(c.getTime());

		} catch (ParseException e) {
			
		}
		
		return beforeDay;
	}
	
	/**
	 * 获取一以前某月
	 * @Title: getBeforeMonth 
	 * @Description:  
	 * @param date
	 * @param pattern
	 * @param monthNum
	 * @return String
	 * @throws
	 */
	public static String getBeforeMonth(String date, String pattern, int monthNum) {// 根据日期求上月日期
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String beforeMonth = null;
		try {
			Date rq = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(rq);// .add(rq, -3);
			c.add(Calendar.MONTH, monthNum);
			beforeMonth = sdf.format(c.getTime());

		} catch (ParseException e) {
			
		}
		
		return beforeMonth;
	}
	
	/**
	 * 获取一以前某年
	 * @Title: getBeforYear 
	 * @Description:  
	 * @param date
	 * @param pattern
	 * @param monthNum
	 * @return String
	 * @throws
	 */
	public static String getBeforeYear(String date, String pattern, int monthNum) {// 根据日期求上月日期
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String beforeYear = null;
		try {
			Date rq = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(rq);// .add(rq, -1);
			c.add(Calendar.YEAR, monthNum);
			beforeYear = sdf.format(c.getTime());

		} catch (ParseException e) {
			
		}
		
		return beforeYear;
	}
	
	/**
	 * 获取下一天
	 * @param date
	 * @return
	 */
	public static String getNextDay(String date, String pattern) {// 根据日期求上周日期
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String nextDay = null;
		try {
			Date rq = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(rq);// .add(rq, 1);
			c.add(Calendar.DATE, 1);
			nextDay = sdf.format(c.getTime());

		} catch (ParseException e) {
			
		}
		
		return nextDay;
	}
	
	/**
	 * 获取下一月同一日期
	 * @param date
	 * @return
	 */
	public static String getNextMonth(String date, String pattern) { // 根据日期求下月日期
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String nextMonthDay = null;
		try {
			Date rq = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(rq);// .add(rq, 1);
			c.add(Calendar.MONTH, 1);
			nextMonthDay = sdf.format(c.getTime());

		} catch (ParseException e) {
			
		} 
		
		return nextMonthDay;
	}
	
	/**
	 * 获取下一月同一日期
	 * @param date
	 * @return
	 */
	public static String getPreciousMonth(String date, String pattern) { // 根据日期求下月日期
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String nextMonthDay = null;
		try {
			Date rq = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(rq);// .add(rq, 1);
			c.add(Calendar.MONTH, -1);
			nextMonthDay = sdf.format(c.getTime());

		} catch (ParseException e) {
			
		} 
		
		return nextMonthDay;
	}
	
	/**
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getSpecifiedDateAfter(Date date,int days){
		Calendar c = Calendar.getInstance();
		if(date == null){
			return date;
		}
		c.setTime(date);
		int currentDays = c.get(Calendar.DATE);
		c.set(Calendar.DATE, currentDays+days);
		c.set(Calendar.HOUR_OF_DAY, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	
	public static long getMinute(long time){
		if(time<=0){
			return 0;
		}
		return time%60==0?(time/60):(time/60 + 1);
	}
	
	/**
	 * 北京时间（CST）转UTC时间
	 * @param date
	 * @return
	 */
	public static Date CSTToUTC(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-8);
		return calendar.getTime();
	}
}