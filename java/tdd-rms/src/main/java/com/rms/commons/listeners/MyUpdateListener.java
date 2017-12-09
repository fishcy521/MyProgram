package com.rms.commons.listeners;

import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.event.def.DefaultUpdateEventListener;
//.DefaultLoadEventListener;

public class MyUpdateListener extends DefaultUpdateEventListener implements PostUpdateEventListener {
	public void onPostUpdate(PostUpdateEvent event) {
		System.out.println(event.getEntity().getClass().getName() + ":更新完毕");
		for (int i = 0; i < event.getState().length; i++) {
			// 更新前的值
			Object oldValue = event.getOldState()[i];
			// 更新后的新值
			Object newValue = event.getState()[i];
			// 更新的属性名
			String propertyName = event.getPersister().getPropertyNames()[i];
			/*if (event.getEntity() instanceof User) {
				System.out.println("保存User之前的操作");
			}*/
		}
	}
}