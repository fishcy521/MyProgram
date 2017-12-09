package com.rms.commons.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.rms.commons.dbcp.IFSysDBCP;
import com.rms.modules.utils.ConvertUtil;
import com.rms.modules.utils.spring.SpringContextHolder;

public class AutoLoginForALMFilter implements Filter {
	private static Log logger = LogFactory.getLog(AutoLoginForALMFilter.class);


	protected String verifyLogin;

	public void init(FilterConfig fConfig) throws ServletException {
		// 初始化外部接口数据源
		ApplicationContext context = SpringContextHolder.getApplicationContext();// WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		//accountManager = (AccountManager) context.getBean("accountManager");
		if (IFSysDBCP.getInstance().isDataSource()) {
			logger.info("*********初始化外部接口数据源开始************");
			//IFSysDBCP.getInstance().setDatasourceOA((DataSource) context.getBean("dataSourceOA"));
			//logger.info("************OA连接成功************");
			//IFSysDBCP.getInstance().setDatasourceCupaa((DataSource) context.getBean("dataSourceCupaa"));
			//logger.info("************三统一平台连接成功************");
			// IFSysDBCP.getInstance().setDatasourceOA((DataSource) context.getBean("dataSourceOA"));
			// logger.info("************OA连接成功************");
			
			IFSysDBCP.getInstance().setDatasourceTdd((DataSource) context.getBean("dataSourceTdd"));
			logger.info("************tdd接成功************");
			logger.info("*********初始化外部接口数据源结束************");
		}
		// 初始化定时任务
//		logger.info("*********初始化定时任务开始************");
//		ArchScheduleManager archScheduleManager = (ArchScheduleManager) context.getBean("archScheduleManager");
//
//		List list = archScheduleManager.getRunningSchedules(3);// 3 pms专用
//		Scheduler scheduler = KernelQuartz.getScheduler(context);
//		try {
//			for (int i = 0; i < list.size(); i++) {
//				ArchSchedule schedule = (ArchSchedule) list.get(i);
//				CTBean ce = ArchScheduleManager.parseCronExpressionFromString(schedule.getSchedulePeriodType(), schedule.getSchedulePeriod());
//				scheduler.start();
//				Properties properties = new Properties();
//				String param = schedule.getScheduleParameters();
//				if (param != null) {
//					properties.load(new ByteArrayInputStream(param.getBytes()));
//				}
//
//				Class _class = null;
//				try {
//					_class = Class.forName(schedule.getScheduleProcess());
//				} catch (Exception e) {
//					_class = null;
//					logger.error(e.getMessage());
//				}
//				if (_class != null && schedule.getScheduleStatus() == 3) {// 0 未开始 1 运行 3 pms专用
//					KernelQuartz.scheduleJob(schedule.getScheduleId(), scheduler, properties, _class, ce.getCronExpression(), ce.getTrigger());
//				}
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
//		logger.info("*********初始化定时任务结束************");
		verifyLogin = ConvertUtil.convertNull(fConfig.getInitParameter("verifyLogin"));
	}

	public void destroy() {
	}

	// HttpServletRequest
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		logger.info("*******调用的URI=" + uri);

		/*if ("true".equals(verifyLogin)) {
			if ("/pms/account/user!login.action".equals(uri) || "/pms/almdbcp".equals(uri) || "/pms/login.action".equals(uri) || uri.endsWith(".rar") || uri.endsWith(".zip") || uri.endsWith(".css")
					|| uri.endsWith(".js") || uri.endsWith(".gif") || uri.endsWith(".jpg")) {
				chain.doFilter(request, response);
				return;
			} else if (uri.indexOf("/pms/ws") == 0) {
				chain.doFilter(request, response);
				return;
			} else {
				// 普通userid登陆
				long userId = ConvertUtil.convertLong(request.getParameter("curUserId"));
				if (userId > 0) {
					try {
						User curUser = accountManager.getUser(Long.valueOf(userId));
						if (curUser != null) {
							long curOrgId = ConvertUtil.convertLong(request.getParameter("curOrgId"));
							if (curOrgId > 0) {// 自动取得二级组织
							} else {
								curOrgId = accountManager.getUserLevel2OrgId(userId);
							}
							httpRequest.getSession().setAttribute("s_userId", userId);
							httpRequest.getSession().setAttribute("s_userName", curUser.getScreenname());
							httpRequest.getSession().setAttribute("s_orgId", curOrgId);

							request.setAttribute("curUserId", userId);
							request.setAttribute("curOrgId", curOrgId);
							chain.doFilter(request, response);
						} else {
							httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.action");
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
						httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.action");
						return;
					}
				} else {
					long curUserId = ConvertUtil.toLong((Long) httpRequest.getSession().getAttribute("s_userId"));
					long curOrgId = ConvertUtil.toLong((Long) httpRequest.getSession().getAttribute("s_orgId"));
					if (curUserId > 0 && curOrgId > 0) {
						request.setAttribute("curUserId", curUserId);
						request.setAttribute("curOrgId", curOrgId);
						chain.doFilter(request, response);
					} else {
						httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.action");
						return;
					}

				}
			}
		} else {
			chain.doFilter(request, response);
			return;
		}*/
		
		chain.doFilter(request, response);
	}
}
