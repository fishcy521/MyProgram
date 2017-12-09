package com.rms.job.jnhg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		/*Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
		System.out.println(DateUtils.getStartOfDate(c.getTime()));
		System.out.println(DateUtils.getEndOfDate(c.getTime()));*/
		
		System.out.println("Test start..");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-quartz2.xml");
        //如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化
        //context.getBean("startQuertz");
        System.out.print("Test end..");
        
	}
}
