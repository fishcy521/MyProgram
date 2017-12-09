/**
 * 
 */
package com.rms.job.jnhg;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rms.job.epms.WebMasterRegionJob;
import com.rms.utils.ApplicationProperties;
import com.rms.utils.DateUtils;

public class CupaaUserSysJob implements Job{

	private static final Logger log = Logger.getLogger(WebMasterRegionJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 生成今天的小区 txt文件
		try {
			log.info("网管接口定时任务开始。");
			/*ProjectRegionManager manager = (ProjectRegionManager) SpringContextHolder.getApplicationContext().getBean("projectRegionManager");
			if (manager != null) {
				manager.generatePrjRegionFile(ConvertUtil.getDate());
			}*/
			log.info("网管接口定时任务结束。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProcessName() { // 程序名称选择程序中出现
		return "测试进程";
	}
}
