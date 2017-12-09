package com.rms.commons.listeners;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;

public class PmsSessionMonitorListener implements HttpSessionListener {
	private static int activeSessions = 0;

	/* Session创建事件 */
	public void sessionCreated(HttpSessionEvent se) {
		// ServletContext ctx = event.getSession( ).getServletContext( );

	}

	/* Session失效事件 */
	public void sessionDestroyed(HttpSessionEvent se) {

	}
}