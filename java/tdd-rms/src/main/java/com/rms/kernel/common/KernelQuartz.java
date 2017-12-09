package com.rms.kernel.common;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.ApplicationContext;

/**
 * 编程式安排一个 Quartz Job 调度自己实现的Job类,主要是通过Quartz中的触发器用来告诉调度程序作业什么时候触发
 * 
 * @author SunChaoqun
 * 
 */
public class KernelQuartz {
	private static Log log = LogFactory.getLog(KernelQuartz.class);

	public static void scheduleJob(String jobId, final Scheduler scheduler, Map params, Class<Job> _class, CronExpression cexp, Trigger trigger) throws SchedulerException {
		if (scheduler.checkExists(new JobKey(jobId, "pmsGroup"))) {
			// scheduler.deleteJob(new JobKey(jobId, "pmsGroup"));
			log.info("------任务Jobid=" + jobId + "已存在，自动跳过");
			return;
		}
		log.info("------任务Jobid=" + jobId + "不存在，开始创建任务------");
		JobBuilder jobBuilder = JobBuilder.newJob(_class).withIdentity(jobId, "pmsGroup");
		JobDataMap jdm = new JobDataMap(params);
		jobBuilder.usingJobData(jdm);
		// jobBuilder.requestRecovery(true);
		// jobBuilder.storeDurably(true);
		JobDetail jobDetail = jobBuilder.build();

		Trigger runTrigger = null;
		if (cexp != null) {
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity(jobId + "T", "pmsGroup");
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cexp));
			runTrigger = triggerBuilder.build();
		} else {
			runTrigger = trigger;
		}
		// scheduler.setJobFactory(new SimpleJobFactory()); // add by wangtao
		scheduler.scheduleJob(jobDetail, runTrigger);
		log.info("------任务Jobid=" + jobId + "创建成功------");
	}

	public static boolean deleteJob(String jobId) {
		try {
			scheduler.deleteJob(new JobKey(jobId, "pmsGroup"));
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static Scheduler scheduler = null;

	public static Scheduler getScheduler(ApplicationContext context) {
		if (scheduler == null) {
			try {
				scheduler = (Scheduler) context.getBean("quartzScheduler");

				return scheduler;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return scheduler;
		}
		return null;
	}
}
