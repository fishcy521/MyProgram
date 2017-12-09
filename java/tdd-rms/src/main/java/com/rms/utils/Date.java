package com.rms.utils;

import java.util.*;

/**
 * 
 */
public class Date {
	public Date() {
		super();
	}

	// 返回YYYY-MM-DD 格式
	public static String getDate() {
		GregorianCalendar gt = new GregorianCalendar();
		String lsdate = "";
		int year = gt.get(Calendar.YEAR);
		int month = gt.get(Calendar.MONTH) + 1;
		int date = gt.get(Calendar.DATE);
		lsdate = "" + year + "-";
		if (month < 10)
			lsdate = lsdate + "0" + month;
		else
			lsdate += month;
		lsdate += "-";
		if (date < 10)
			lsdate = lsdate + "0" + date;
		else
			lsdate += date;
		return lsdate;
	}

	// 返回YYYY年MM月DD日**时**分**秒 格式
	public static String getYearTime() {
		GregorianCalendar gt = new GregorianCalendar();
		String lsdate = "";
		int year = gt.get(Calendar.YEAR);
		int month = gt.get(Calendar.MONTH) + 1;
		int date = gt.get(Calendar.DATE);
		int hour = gt.get(Calendar.HOUR_OF_DAY);
		int minute = gt.get(Calendar.MINUTE);
		int second = gt.get(Calendar.SECOND);

		lsdate = "" + year + "年";
		if (month < 10)
			lsdate = lsdate + "0" + month;
		else
			lsdate += month;
		lsdate += "月";
		if (date < 10)
			lsdate = lsdate + "0" + date;
		else
			lsdate += date;
		lsdate += "日";

		if (hour < 10) {
			lsdate += "0";
		}
		lsdate += "" + hour + "时";

		if (minute < 10) {
			lsdate += "0";
		}
		lsdate += "" + minute + "分";

		if (second < 10) {
			lsdate += "0";
		}
		lsdate += "" + second + "秒";

		return lsdate;
	}

	// 返回YYYYMMDD 格式
	public static String getYmd() {
		GregorianCalendar gc = new GregorianCalendar();
		String ymd = "";
		int ye = gc.get(Calendar.YEAR);
		int mo = gc.get(Calendar.MONTH) + 1;
		int da = gc.get(Calendar.DATE);
		ymd = "" + ye;
		if (mo < 10)
			ymd = ymd + "0" + mo;
		else
			ymd += mo;
		if (da < 10)
			ymd = ymd + "0" + da;
		else
			ymd += da;
		return ymd;
	}

	public static int getHour() {
		GregorianCalendar dt = new GregorianCalendar();
		return dt.get(Calendar.HOUR) + 12 * dt.get(Calendar.AM_PM);
	}

	public static int getMinute() {
		GregorianCalendar dt = new GregorianCalendar();
		return dt.get(Calendar.MINUTE);
	}

	public static int getHowLong(int initHour, int initMinute) {
		GregorianCalendar dt = new GregorianCalendar();
		return (dt.get(Calendar.HOUR) + 12 * dt.get(Calendar.AM_PM) - initHour)
				* 60 + dt.get(Calendar.MINUTE) - initMinute;
	}

	// 返回YYYY 格式
	public static int getYear() {
		GregorianCalendar gt = new GregorianCalendar();
		int year = gt.get(Calendar.YEAR);
		return year;
	}

}