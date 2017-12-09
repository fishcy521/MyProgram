/**
 * 
 */
package com.rms.job;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 */
public class JobFactory extends SimpleJobFactory implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	

	@Override
	public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler)
			throws SchedulerException {
		Job job = super.newJob(bundle, scheduler);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
		return job;
	}



	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.applicationContext = context;
	}
}
