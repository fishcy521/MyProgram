/**
 * 
 */
package com.rms.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author liuguangrui
 * 
 */
public class DateUtils {

    /**
     * 取下周一的日期
     * 
     * @param date
     * @return
     */
    public static Date getNextMonday(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	int day = 0;
	if (calendar.get(Calendar.DAY_OF_WEEK) - 1 > 0) {
	    day = 7;
	}
	calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	calendar.add(Calendar.DAY_OF_MONTH, day);
	return calendar.getTime();
    }

    public static Date getCalcMonth(Date date, int month) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	calendar.add(Calendar.MONTH, month);
	return calendar.getTime();
    }

    public static Date getStartOfDate(Date date) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	return c.getTime();
    }

    public static Date getEndOfDate(Date date) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.set(Calendar.HOUR_OF_DAY, 23);
	c.set(Calendar.MINUTE, 59);
	c.set(Calendar.SECOND, 59);
	c.set(Calendar.MILLISECOND, 999);
	return c.getTime();
    }

    public static void main(String[] args) {
	System.out.println(getCalcMonth(new Date(), -2));
    }
}
