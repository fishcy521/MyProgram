/**
 * 
 */
package com.rms.job.jnhg;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rms.utils.ApplicationProperties;
import com.rms.utils.DateUtils;

public class CupaaUserSysJob1 {

	/*public void work() {
		System.out.println("==================exec time:" + new Date().toLocaleString());
		String enabled = ApplicationProperties.get("erp.if.nodify.job.enabled");
		if (!Boolean.parseBoolean(enabled)) {
			return;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);// 发送昨天的
		Date startDate = DateUtils.getStartOfDate(c.getTime());
		Date endDate = DateUtils.getEndOfDate(c.getTime());
		System.out.println(">>>>>>>>>>>>>>>>>>>haha>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}*/

	private static int counter = 0;  
    protected void execute()  {  
        long ms = System.currentTimeMillis();  
        System.out.println("\t\t" + new Date(ms));  
        System.out.println("(" + counter++ + ")");  
    }  
}
