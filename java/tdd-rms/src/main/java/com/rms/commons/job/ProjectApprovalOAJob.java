package com.rms.commons.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ProjectApprovalOAJob implements Job {

	// @Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Scheduler scheduler = context.getScheduler();// 获得调度器
		// JobDetail smd = context.getJobDetail();// 获取任务详情
		// JobDataMap jdm = smd.getJobDataMap();// 获取动态参数
		//
		// // 迭代集合处理参数
		// Iterator keys = jdm.keySet().iterator();
		// while (keys.hasNext()) {
		// Object key = keys.next();
		// System.out.println("Key -> " + key + " Value -> " + jdm.get(key));
		// }
		// 打印指定key所对应的value值：
		System.out.println("---------->项目立项测试定时任务测试");
	}

	public String getProcessName() { // 程序名称选择程序中出现
		return "测试进程";
	}

}
