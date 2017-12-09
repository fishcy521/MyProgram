package com.rms.kernel.common.beans;

import org.quartz.CronExpression;
import org.quartz.Trigger;

public class CTBean{
	
	public CTBean() {
		
	}
	
	CronExpression cronExpression;
	Trigger trigger;
	
	
	public CronExpression getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(CronExpression cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Trigger getTrigger() {
		return trigger;
	}
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	public CTBean(CronExpression cronExpression, Trigger trigger) {
		this.cronExpression = cronExpression;
		this.trigger = trigger;
	}
}