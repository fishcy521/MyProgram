package com.rms.web.tdd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Namespace;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import com.rms.entity.tdd.RmsInterface;
import com.rms.entity.tdd.RmsInterfaceCategory;
import com.rms.modules.orm.Page;
import com.rms.modules.utils.encode.JsonBinder;
import com.rms.modules.utils.web.struts2.Struts2Utils;
import com.rms.service.tdd.InterfaceManager;
import com.rms.service.utils.SigmaBean;
import com.rms.tddenum.TddEnum;
import com.rms.utils.SigmaPageInfoBean;
import com.rms.web.CrudActionSupport;

@Namespace ("/tdd")
// @Results({ @Result(name = InterfaceAction.RELOAD, location =
// "interface.action", type = "redirect") })
public class CategoryAction extends CrudActionSupport<RmsInterfaceCategory> {

	private static final long	       serialVersionUID	= 8248163836557189340L;

	@Autowired
	private InterfaceManager	       interfaceManager;

	// 页面数据集
	private Page<RmsInterfaceCategory>	page	        = new Page<RmsInterfaceCategory> (10);

	// 页面参数集合
	private Map<String, Object>	       pageMap	        = new HashMap<String, Object> ();

	private Integer	                   ICateId;

	public Integer getICateId () {

		return ICateId;
	}

	public void setICateId (Integer iCateId) {

		ICateId = iCateId;
	}

	private RmsInterfaceCategory	entity;

	@Override
	public RmsInterfaceCategory getModel () {

		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public String list () throws Exception {

		return SUCCESS;
	}

	@Override
	public String input () throws Exception {

		List<RmsInterface> lstInterfaceSel = interfaceManager.getInterfaceListByCateId (ICateId, TddEnum.interfaceSelect);
		List<RmsInterface> lstInterfaceUnSel = interfaceManager.getInterfaceListByCateId (ICateId, TddEnum.interfaceUnSelect);

		Struts2Utils.getRequest ().setAttribute ("lstInterfaceSel", lstInterfaceSel);
		Struts2Utils.getRequest ().setAttribute ("lstInterfaceUnSel", lstInterfaceUnSel);

		return "input";
	}

	@Override
	public String save () throws Exception {

		String exception = "";
		Map<TddEnum, Object> jsonMap = new HashMap<TddEnum, Object> ();
		HttpServletRequest request = Struts2Utils.getRequest ();
		String interfaceIds = request.getParameter ("interfaceIds");
		List<String> lstInterfaceIs = Arrays.asList (interfaceIds.split (","));
		for (String string : lstInterfaceIs) {
			System.out.println ("string=" + string);
		}

		try {
			interfaceManager.savaInterfaceCategory (entity, lstInterfaceIs);
			exception = "新增成功";
			jsonMap.put (TddEnum.RTNTYPE, TddEnum.RTNTYPEY.toString ());
		}
		catch (Exception e) {
			exception = e.getMessage ();
		}
		jsonMap.put (TddEnum.RTNMSG, exception);

		Struts2Utils.renderJson (jsonMap);
		return null;
	}

	@Override
	public String delete () throws Exception {

		interfaceManager.deleteInterfaceCategory (entity);
		return SUCCESS;
	}

	@Override
	protected void prepareModel () throws Exception {

		if (ICateId != null) {
			entity = interfaceManager.getInterfaceCategoryById (ICateId);
		}
		else {
			entity = new RmsInterfaceCategory ();
		}
	}

	public RmsInterfaceCategory getEntity () {

		return entity;
	}

	public void setEntity (RmsInterfaceCategory entity) {

		this.entity = entity;
	}

	public String gridData () throws Exception {

		String jsonString = Struts2Utils.getRequest ().getParameter ("_gt_json");
		this.logger.info ("jsonString=" + jsonString);

		SigmaBean sigmaBean = JsonBinder.buildNonNullBinder ().getMapper ()
		        .readValue (jsonString, new TypeReference<SigmaBean<RmsInterface>> () {
		        });
		page.setPageNo ((Integer) sigmaBean.getPageInfo ().get ("pageNum"));
		page.setPageSize ((Integer) sigmaBean.getPageInfo ().get ("pageSize"));
		page.setTotalCount ((Integer) sigmaBean.getPageInfo ().get ("totalRowNum"));

		String exception = "";

		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put ("paramICateName", (String) sigmaBean.getParameters ().get ("paramICateName"));

		/*
		 * List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		 * PropertyFilter.buildFromMap(sigmaBean.getParameters(), filters); // 设置默认排序方式 if (!page.isOrderBySetted()) {
		 * page.setOrderBy("id"); page.setOrder(Page.ASC); } page = resourceManager.searchHrmResource(page, filters);
		 */

		if (!page.isOrderBySetted ()) {
			page.setOrderBy ("ICateName");
			page.setOrder (Page.ASC);
		}
		page = interfaceManager.getInterfaceCategoryPage (paramMap, page);
		Map<String, Object> jsonMap = new HashMap<String, Object> ();

		SigmaPageInfoBean pageBean = new SigmaPageInfoBean (page.getPageSize (), page.getPageNo (), page.getTotalCount (),
		        page.getTotalPages (), null, null);
		jsonMap.put ("pageInfo", pageBean);
		jsonMap.put ("data", page.getResult ());
		jsonMap.put ("exception", exception);
		Struts2Utils.renderJson (jsonMap);

		return null;
	}
}
