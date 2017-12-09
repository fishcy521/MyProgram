package com.rms.commons.dwr;

//import org.directwebremoting.ScriptBuffer;
//import org.directwebremoting.ScriptSession;
//import org.directwebremoting.ServerContext;
//import org.directwebremoting.ServerContextFactory;
//import org.directwebremoting.util.Logger;
//import uk.ltd.getahead.dwr.WebContext;

public class SysNoticeMsg {
	//protected static final Logger log = Logger.getLogger(SysNoticeMsg.class);

	public void noticeNewMessage(String hfmpguid, String title) {
		try {
			// GlobalObjecFactory globalFactory = GlobalObjecFactory.getInstance();
			// WebContext wctx = null;
			// wctx = uk.ltd.getahead.dwr.WebContextFactory.get();
			// // if (globalFactory.getObject("dwr.webcontext") == null) {
			// // wctx = uk.ltd.getahead.dwr.WebContextFactory.get();
			// // globalFactory.putObject("dwr.webcontext", wctx);
			// // } else {
			// // wctx = (WebContext) globalFactory.getObject("dwr.webcontext");
			// // }
			// ScriptBuffer script = new ScriptBuffer();
			// script.appendScript("receiveMessages(").appendData(hfmpguid).appendScript(",").appendData(title).appendScript(");");
			// ServerContext sctx = ServerContextFactory.get(wctx.getServletContext());
			// Collection pages = sctx.getScriptSessionsByPage("/hfmpwlmq/jsp/sysNotice/sysNoticeShow.jsp");
			// log.info("dwr excute!");
			// for (Object obj : pages) {
			// ScriptSession session = (ScriptSession) obj;
			// log.info("����֪ͨsessionID=" + session.toString());
			// session.addScript(script);
			// }
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
