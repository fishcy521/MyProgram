package com.rms.web.tdd;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Namespace;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import com.rms.commons.global.RmsGlobal;
import com.rms.entity.tdd.RmsDict;
import com.rms.entity.tdd.RmsDictDetail;
import com.rms.entity.tdd.RmsInterface;
import com.rms.entity.tdd.RmsInterfaceParam;
import com.rms.entity.tdd.RmsMessage;
import com.rms.modules.orm.Page;
import com.rms.modules.utils.encode.JsonBinder;
import com.rms.modules.utils.web.struts2.Struts2Utils;
import com.rms.service.tdd.DictManager;
import com.rms.service.tdd.FunctionManager;
import com.rms.service.tdd.InterfaceManager;
import com.rms.service.tdd.MessageManager;
import com.rms.service.utils.SigmaBean;
import com.rms.tddenum.TddEnum;
import com.rms.utils.ApplicationProperties;
import com.rms.utils.DESPlus;
import com.rms.utils.DESUtils;
import com.rms.utils.JsoupUtil;
import com.rms.utils.RmsUtils;
import com.rms.utils.SigmaPageInfoBean;
import com.rms.web.CrudActionSupport;

@Namespace ("/tdd")
// @Results({ @Result(name = InterfaceAction.RELOAD, location =
// "interface.action", type = "redirect") })
public class MessageAction extends CrudActionSupport<RmsMessage> {

	private static final long	serialVersionUID	= 8248163836557189340L;

	@Autowired
	private MessageManager	    messageManager;

	// 页面数据集
	private Page<RmsMessage>	page	         = new Page<RmsMessage>(10);

	// 页面参数集合
	private Map<String, Object>	pageMap	         = new HashMap<String, Object>();

	private Integer	            msgId;

	private RmsMessage	        entity;

	@Override
	public RmsMessage getModel() {

		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public String list() throws Exception {

		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		return "input";
	}

	@Override
	public String save() throws Exception {

		String jsonString = Struts2Utils.getRequest().getParameter("_gt_json");
		this.logger.info("jsonString=" + jsonString);
		SigmaBean<RmsMessage> sigmaBean = JsonBinder.buildNonNullBinder().getMapper()
		        .readValue(jsonString, new TypeReference<SigmaBean<RmsMessage>>() {
		        });

		return null;
	}

	@Override
	public String delete() throws Exception {

		return list();
	}

	public Integer getMsgId() {

		return msgId;
	}

	public void setMsgId(Integer msgId) {

		this.msgId = msgId;
	}

	public RmsMessage getEntity() {

		return entity;
	}

	public void setEntity(RmsMessage entity) {

		this.entity = entity;
	}

	@Override
	protected void prepareModel() throws Exception {

		/*if (RmsMessage != null) {
			entity = interfaceManager.getInterfaceById(RmsMessage);
		}
		else {
			entity = new RmsInterface();
		}*/

	}

	public String gridData() throws Exception {

		String jsonString = Struts2Utils.getRequest().getParameter("_gt_json");
		this.logger.info("jsonString=" + jsonString);

		SigmaBean<RmsMessage> sigmaBean = JsonBinder.buildNonNullBinder().getMapper()
		        .readValue(jsonString, new TypeReference<SigmaBean<RmsMessage>>() {
		        });
		page.setPageNo((Integer) sigmaBean.getPageInfo().get("pageNum"));
		page.setPageSize((Integer) sigmaBean.getPageInfo().get("pageSize"));
		page.setTotalCount((Integer) sigmaBean.getPageInfo().get("totalRowNum"));

		String exception = "";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("paramKey", (String) sigmaBean.getParameters().get("paramKey"));
		paramMap.put("paramValue", (String) sigmaBean.getParameters().get("paramValue"));

		if (!page.isOrderBySetted()) {
			page.setOrderBy("msgKey");
			page.setOrder(Page.DESC);
		}
		page = messageManager.getInterfacePage(paramMap, page);
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		SigmaPageInfoBean pageBean = new SigmaPageInfoBean(page.getPageSize(), page.getPageNo(), page.getTotalCount(),
		        page.getTotalPages(), null, null);
		jsonMap.put("pageInfo", pageBean);
		jsonMap.put("data", page.getResult());
		jsonMap.put("exception", exception);
		Struts2Utils.renderJson(jsonMap);

		return null;
	}

}
