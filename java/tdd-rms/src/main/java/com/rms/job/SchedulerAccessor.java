/**
 * 
 */
package com.rms.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailAwareTrigger;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 */
public abstract class SchedulerAccessor implements ResourceLoaderAware {

	protected final Log logger = LogFactory.getLog(getClass());


	private boolean overwriteExistingJobs = false;

	private List<JobDetail> jobDetails;

	private Map<String, Calendar> calendars;

	private List<Trigger> triggers;


	private SchedulerListener[] schedulerListeners;

	private JobListener[] jobListeners;

	private TriggerListener[] triggerListeners;


	private PlatformTransactionManager transactionManager;

	protected ResourceLoader resourceLoader;


	/**
	 * Set whether any jobs defined on this SchedulerFactoryBean should overwrite
	 * existing job definitions. Default is "false", to not overwrite already
	 * registered jobs that have been read in from a persistent job store.
	 */
	public void setOverwriteExistingJobs(boolean overwriteExistingJobs) {
		this.overwriteExistingJobs = overwriteExistingJobs;
	}

	/**
	 * Register a list of JobDetail objects with the Scheduler that
	 * this FactoryBean creates, to be referenced by Triggers.
	 * <p>This is not necessary when a Trigger determines the JobDetail
	 * itself: In this case, the JobDetail will be implicitly registered
	 * in combination with the Trigger.
	 * @see #setTriggers
	 * @see org.quartz.JobDetail
	 * @see JobDetailBean
	 * @see JobDetailAwareTrigger
	 * @see org.quartz.Trigger#setJobName
	 */
	public void setJobDetails(JobDetail[] jobDetails) {
		// Use modifiable ArrayList here, to allow for further adding of
		// JobDetail objects during autodetection of JobDetailAwareTriggers.
		this.jobDetails = new ArrayList<JobDetail>(Arrays.asList(jobDetails));
	}

	/**
	 * Register a list of Quartz Calendar objects with the Scheduler
	 * that this FactoryBean creates, to be referenced by Triggers.
	 * @param calendars Map with calendar names as keys as Calendar
	 * objects as values
	 * @see org.quartz.Calendar
	 * @see org.quartz.Trigger#setCalendarName
	 */
	public void setCalendars(Map<String, Calendar> calendars) {
		this.calendars = calendars;
	}

	/**
	 * Register a list of Trigger objects with the Scheduler that
	 * this FactoryBean creates.
	 * <p>If the Trigger determines the corresponding JobDetail itself,
	 * the job will be automatically registered with the Scheduler.
	 * Else, the respective JobDetail needs to be registered via the
	 * "jobDetails" property of this FactoryBean.
	 * @see #setJobDetails
	 * @see org.quartz.JobDetail
	 * @see JobDetailAwareTrigger
	 * @see CronTriggerBean
	 * @see SimpleTriggerBean
	 */
	public void setTriggers(Trigger[] triggers) {
		this.triggers = Arrays.asList(triggers);
	}


	/**
	 * Specify Quartz SchedulerListeners to be registered with the Scheduler.
	 */
	public void setSchedulerListeners(SchedulerListener[] schedulerListeners) {
		this.schedulerListeners = schedulerListeners;
	}

	/**
	 * Specify named Quartz JobListeners to be registered with the Scheduler.
	 * Such JobListeners will only apply to Jobs that explicitly activate
	 * them via their name.
	 * @see org.quartz.JobListener#getName
	 * @see org.quartz.JobDetail#addJobListener
	 * @see JobDetailBean#setJobListenerNames
	 */
	public void setJobListeners(JobListener[] jobListeners) {
		this.jobListeners = jobListeners;
	}

	/**
	 * Specify named Quartz TriggerListeners to be registered with the Scheduler.
	 * Such TriggerListeners will only apply to Triggers that explicitly activate
	 * them via their name.
	 * @see org.quartz.TriggerListener#getName
	 * @see org.quartz.Trigger#addTriggerListener
	 * @see CronTriggerBean#setTriggerListenerNames
	 * @see SimpleTriggerBean#setTriggerListenerNames
	 */
	public void setTriggerListeners(TriggerListener[] triggerListeners) {
		this.triggerListeners = triggerListeners;
	}


	/**
	 * Set the transaction manager to be used for registering jobs and triggers
	 * that are defined by this SchedulerFactoryBean. Default is none; setting
	 * this only makes sense when specifying a DataSource for the Scheduler.
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


	/**
	 * Register jobs and triggers (within a transaction, if possible).
	 */
	protected void registerJobsAndTriggers() throws SchedulerException {
		TransactionStatus transactionStatus = null;
		if (this.transactionManager != null) {
			transactionStatus = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		}
		try {
			// Register JobDetails.
			if (this.jobDetails != null) {
				for (JobDetail jobDetail : this.jobDetails) {
					addJobToScheduler(jobDetail);
				}
			}
			else {
				// Create empty list for easier checks when registering triggers.
				this.jobDetails = new LinkedList<JobDetail>();
			}

			// Register Calendars.
			if (this.calendars != null) {
				for (String calendarName : this.calendars.keySet()) {
					Calendar calendar = this.calendars.get(calendarName);
					getScheduler().addCalendar(calendarName, calendar, true, true);
				}
			}

			// Register Triggers.
			if (this.triggers != null) {
				for (Trigger trigger : this.triggers) {
					addTriggerToScheduler(trigger);
				}
			}
		}

		catch (Throwable ex) {
			if (transactionStatus != null) {
				try {
					this.transactionManager.rollback(transactionStatus);
				}
				catch (TransactionException tex) {
					logger.error("Job registration exception overridden by rollback exception", ex);
					throw tex;
				}
			}
			if (ex instanceof SchedulerException) {
				throw (SchedulerException) ex;
			}
			if (ex instanceof Exception) {
				throw new SchedulerException("Registration of jobs and triggers failed: " + ex.getMessage(), ex);
			}
			throw new SchedulerException("Registration of jobs and triggers failed: " + ex.getMessage());
		}

		if (transactionStatus != null) {
			this.transactionManager.commit(transactionStatus);
		}
	}

	/**
	 * Add the given job to the Scheduler, if it doesn't already exist.
	 * Overwrites the job in any case if "overwriteExistingJobs" is set.
	 * @param jobDetail the job to add
	 * @return <code>true</code> if the job was actually added,
	 * <code>false</code> if it already existed before
	 * @see #setOverwriteExistingJobs
	 */
	private boolean addJobToScheduler(JobDetail jobDetail) throws SchedulerException {
		if (this.overwriteExistingJobs ||
		    getScheduler().getJobDetail(jobDetail.getKey()) == null) {
			getScheduler().addJob(jobDetail, true);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Add the given trigger to the Scheduler, if it doesn't already exist.
	 * Overwrites the trigger in any case if "overwriteExistingJobs" is set.
	 * @param trigger the trigger to add
	 * @return <code>true</code> if the trigger was actually added,
	 * <code>false</code> if it already existed before
	 * @see #setOverwriteExistingJobs
	 */
	private boolean addTriggerToScheduler(Trigger trigger) throws SchedulerException {
		boolean triggerExists = (getScheduler().getTrigger(trigger.getKey()) != null);
		if (!triggerExists || this.overwriteExistingJobs) {
			// Check if the Trigger is aware of an associated JobDetail.
			if (trigger instanceof JobDetailAwareTrigger) {
				JobDetail jobDetail = ((JobDetailAwareTrigger) trigger).getJobDetail();
				// Automatically register the JobDetail too.
				if (!this.jobDetails.contains(jobDetail) && addJobToScheduler(jobDetail)) {
					this.jobDetails.add(jobDetail);
				}
			}
			if (!triggerExists) {
				try {
					getScheduler().scheduleJob(trigger);
				}
				catch (ObjectAlreadyExistsException ex) {
					if (logger.isDebugEnabled()) {
						logger.debug("Unexpectedly found existing trigger, assumably due to cluster race condition: " +
								ex.getMessage() + " - can safely be ignored");
					}
					if (this.overwriteExistingJobs) {
						getScheduler().rescheduleJob(trigger.getKey(), trigger);
					}
				}
			}
			else {
				getScheduler().rescheduleJob(trigger.getKey(), trigger);
			}
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * Register all specified listeners with the Scheduler.
	 */
	protected void registerListeners() throws SchedulerException {
		if (this.schedulerListeners != null) {
			for (SchedulerListener listener : this.schedulerListeners) {
				getScheduler().getListenerManager().addSchedulerListener(listener);
			}
		}
		if (this.jobListeners != null) {
			for (JobListener listener : this.jobListeners) {
				getScheduler().getListenerManager().addJobListener(listener);
			}
		}
		if (this.triggerListeners != null) {
			for (TriggerListener listener : this.triggerListeners) {
				getScheduler().getListenerManager().addTriggerListener(listener);
			}
		}
	}


	/**
	 * Template method that determines the Scheduler to operate on.
	 * To be implemented by subclasses.
	 */
	protected abstract Scheduler getScheduler();

}