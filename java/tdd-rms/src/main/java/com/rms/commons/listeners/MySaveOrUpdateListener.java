package com.rms.commons.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEventListener;

public class MySaveOrUpdateListener implements PreInsertEventListener, PreUpdateEventListener, PreDeleteEventListener {
	private static Log log = LogFactory.getLog(MySaveOrUpdateListener.class);

	public boolean onPreInsert(PreInsertEvent event) {
		// TODO 自动生成方法存根
		return false;
	}

	public boolean onPreUpdate(PreUpdateEvent event) {
		// TODO 自动生成方法存根
		// if (event.getEntity() instanceof PmsDemandData) {
		// log.info("保存PmsDemandData之前的操作");
		//
		// System.out.println(event.getEntity().getClass().getName() + ":更新");
		// for (int i = 0; i < event.getState().length; i++) {
		// // 更新前的值
		// Object oldValue = event.getOldState()[i];
		// // 更新后的新值
		// Object newValue = event.getState()[i];
		// // 更新的属性名
		// String propertyName = event.getPersister().getPropertyNames()[i];
		// log.info("propertyName=" + propertyName + ";oldValue=" + oldValue + ";newValue=" + newValue);
		// }
		// }
		return false;
	}

	public boolean onPreDelete(PreDeleteEvent event) {
		// TODO 自动生成方法存根
		return false;
	}
}