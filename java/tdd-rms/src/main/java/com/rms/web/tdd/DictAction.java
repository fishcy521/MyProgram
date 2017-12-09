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
import com.rms.modules.orm.Page;
import com.rms.modules.utils.encode.JsonBinder;
import com.rms.modules.utils.web.struts2.Struts2Utils;
import com.rms.service.tdd.DictManager;
import com.rms.service.tdd.FunctionManager;
import com.rms.service.tdd.InterfaceManager;
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
public class DictAction extends CrudActionSupport<RmsDict> {

	private static final long	serialVersionUID	= 8248163836557189340L;

	@Autowired
	private InterfaceManager	interfaceManager;

	@Autowired
	private DictManager	        dictManager;

	@Autowired
	private FunctionManager	    funcManger;

	// 页面数据集
	private Page<RmsDict>	    page	         = new Page<RmsDict>(10);

	// 页面参数集合
	private Map<String, Object>	pageMap	         = new HashMap<String, Object>();

	private Integer	            IId;

	private RmsDict	            entity;

	@Override
	public RmsDict getModel() {

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

		return null;
	}

	@Override
	public String delete() throws Exception {

		return list();
	}

	public Integer getIId() {

		return IId;
	}

	public void setIId(Integer iId) {

		IId = iId;
	}

	public RmsDict getEntity() {

		return entity;
	}

	public void setEntity(RmsDict entity) {

		this.entity = entity;
	}

	@Override
	protected void prepareModel() throws Exception {

		/*
		 * if (IId != null) { entity = interfaceManager.getInterfaceById (IId); } else { entity = new RmsInterface (); }
		 */
	}

	public String gridData() throws Exception {

		String jsonString = Struts2Utils.getRequest().getParameter("_gt_json");
		this.logger.info("jsonString=" + jsonString);

		SigmaBean sigmaBean = JsonBinder.buildNonNullBinder().getMapper()
		        .readValue(jsonString, new TypeReference<SigmaBean<RmsInterface>>() {
		        });
		page.setPageNo((Integer) sigmaBean.getPageInfo().get("pageNum"));
		page.setPageSize((Integer) sigmaBean.getPageInfo().get("pageSize"));
		page.setTotalCount((Integer) sigmaBean.getPageInfo().get("totalRowNum"));

		String exception = "";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("paramIName", (String) sigmaBean.getParameters().get("paramIName"));
		String paramUrl = (String) sigmaBean.getParameters().get("paramIUrl");
		paramUrl = RmsUtils.doWithUrl(paramUrl, RmsGlobal.INTERFACE_HOST, RmsGlobal.INTERFACE_SUFFIX);
		paramMap.put("paramIUrl", paramUrl);

		String paramHasParam = StringUtils.equals((String) sigmaBean.getParameters().get("paramHasParam"), "-1") ? null
		        : (String) sigmaBean.getParameters().get("paramHasParam");
		paramMap.put("paramHasParam", paramHasParam);

		String paramFuncId = StringUtils.equals((String) sigmaBean.getParameters().get("paramFuncId"), "-1") ? null : (String) sigmaBean
		        .getParameters().get("paramFuncId");
		paramMap.put("paramFuncId", paramFuncId);

		String paramICateId = StringUtils.equals((String) sigmaBean.getParameters().get("paramICateId"), "-1") ? null : (String) sigmaBean
		        .getParameters().get("paramICateId");
		paramMap.put("paramICateId", paramICateId);

		String paramStatus = StringUtils.equals((String) sigmaBean.getParameters().get("paramStatus"), "-1") ? null : (String) sigmaBean
		        .getParameters().get("paramStatus");
		paramMap.put("paramStatus", paramStatus);

		if (!page.isOrderBySetted()) {
			page.setOrderBy("IId");
			page.setOrder(Page.DESC);
		}
		// page = interfaceManager.getInterfacePage (paramMap, page);
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
