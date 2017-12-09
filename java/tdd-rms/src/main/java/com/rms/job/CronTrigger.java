/**
 * 
 */
package com.rms.job;

import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.quartz.Scheduler;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Constants;
import org.springframework.scheduling.quartz.JobDetailAwareTrigger;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import bsh.ParseException;

/**
 * 
 */
public class CronTrigger extends CronTriggerImpl implements
		JobDetailAwareTrigger, BeanNameAware, InitializingBean {

	/** Constants for the CronTrigger class */
	private static final Constants constants = new Constants(CronTrigger.class);

	private JobDetail jobDetail;

	private String beanName;

	/**
	 * Register objects in the JobDataMap via a given Map.
	 * <p>
	 * These objects will be available to this Trigger only, in contrast to
	 * objects in the JobDetail's data map.
	 * 
	 * @param jobDataAsMap
	 *            Map with String keys and any objects as values (for example
	 *            Spring-managed beans)
	 * @see JobDetailBean#setJobDataAsMap
	 */
	public void setJobDataAsMap(Map jobDataAsMap) {
		getJobDataMap().putAll(jobDataAsMap);
	}

	/**
	 * Set the misfire instruction via the name of the corresponding constant in
	 * the {@link org.quartz.CronTrigger} class. Default is
	 * <code>MISFIRE_INSTRUCTION_SMART_POLICY</code>.
	 * 
	 * @see org.quartz.CronTrigger#MISFIRE_INSTRUCTION_FIRE_ONCE_NOW
	 * @see org.quartz.CronTrigger#MISFIRE_INSTRUCTION_DO_NOTHING
	 * @see org.quartz.Trigger#MISFIRE_INSTRUCTION_SMART_POLICY
	 */
	public void setMisfireInstructionName(String constantName) {
		setMisfireInstruction(constants.asNumber(constantName).intValue());
	}

	/**
	 * Set the JobDetail that this trigger should be associated with.
	 * <p>
	 * This is typically used with a bean reference if the JobDetail is a
	 * Spring-managed bean. Alternatively, the trigger can also be associated
	 * with a job by name and group.
	 * 
	 * @see #setJobName
	 * @see #setJobGroup
	 */
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public JobDetail getJobDetail() {
		return this.jobDetail;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void afterPropertiesSet() throws ParseException {
		if (getName() == null) {
			setName(this.beanName);
		}
		if (getGroup() == null) {
			setGroup(Scheduler.DEFAULT_GROUP);
		}
		if (getStartTime() == null) {
			setStartTime(new Date());
		}
		if (getTimeZone() == null) {
			setTimeZone(TimeZone.getDefault());
		}
		if (this.jobDetail != null) {
			setJobName(this.jobDetail.getName());
			setJobGroup(this.jobDetail.getGroup());
		}
	}

}
