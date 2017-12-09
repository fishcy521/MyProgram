package com.rms.web.tdd;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Namespace;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import com.rms.entity.tdd.RmsFunction;
import com.rms.entity.tdd.RmsInterface;
import com.rms.modules.orm.Page;
import com.rms.modules.utils.encode.JsonBinder;
import com.rms.modules.utils.web.struts2.Struts2Utils;
import com.rms.service.tdd.DictManager;
import com.rms.service.tdd.FunctionManager;
import com.rms.service.utils.SigmaBean;
import com.rms.tddenum.TddEnum;
import com.rms.utils.SigmaPageInfoBean;
import com.rms.web.CrudActionSupport;

@Namespace ("/tdd")
// @Results({ @Result(name = InterfaceAction.RELOAD, location =
// "interface.action", type = "redirect") })
public class FunctionAction extends CrudActionSupport<RmsFunction> {

	private static final long	serialVersionUID	= 8248163836557189340L;

	@Autowired
	private FunctionManager functionManager;

	@Autowired
	private DictManager	        dictManager;

	// 页面数据集
	private Page<RmsFunction>	page	         = new Page<RmsFunction> (10);

	// 页面参数集合
	private Map<String, Object>	pageMap	         = new HashMap<String, Object> ();

	private Integer	            funcId;

	private RmsFunction	    entity;

	@Override
	public RmsFunction getModel () {

		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public String list () throws Exception {

		return SUCCESS;
	}

	@Override
	public String input () throws Exception {

		List<RmsInterface> lstInterfaceSel = functionManager.getInterfaceListByFuncId (funcId, TddEnum.interfaceSelect);
		List<RmsInterface> lstInterfaceUnSel = functionManager.getInterfaceListByFuncId (funcId, TddEnum.interfaceUnSelect);

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
			functionManager.savaFunctionInterface (entity, lstInterfaceIs);
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

		functionManager.deleteFunctionInterface (entity);
		return list ();
	}

	public Integer getFuncId () {

		return funcId;
	}

	public void setFuncId (Integer funcId) {

		this.funcId = funcId;
	}

	public RmsFunction getEntity () {

		return entity;
	}

	public void setEntity (RmsFunction entity) {

		this.entity = entity;
	}

	@Override
	protected void prepareModel () throws Exception {

		if (funcId != null) {
			entity = functionManager.getFunctionById (funcId);
		}
		else {
			entity = new RmsFunction ();
		}
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
		paramMap.put ("paramFuncName", (String) sigmaBean.getParameters ().get ("paramFuncName"));
		paramMap.put ("paramFuncNameZh", (String) sigmaBean.getParameters ().get ("paramFuncNameZh"));
		//paramMap.put ("paramIUrl", (String) sigmaBean.getParameters ().get ("paramIUrl"));
		paramMap.put ("paramFuncDesc", (String) sigmaBean.getParameters ().get ("paramFuncDesc"));

		/*String paramHasParam = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramHasParam"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramHasParam");
		paramMap.put ("paramHasParam", paramHasParam);
		String paramICateId = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramICateId"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramICateId");
		paramMap.put ("paramICateId", paramICateId);

		String paramStatus = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramStatus"), "-1") ? null : (String) sigmaBean
		        .getParameters ().get ("paramStatus");
		paramMap.put ("paramStatus", paramStatus);*/

		if (!page.isOrderBySetted ()) {
			page.setOrderBy ("funcNameZh");
			page.setOrder (Page.ASC);
		}
		page = functionManager.getFunctionPage (paramMap, page);
		Map<String, Object> jsonMap = new HashMap<String, Object> ();

		SigmaPageInfoBean pageBean = new SigmaPageInfoBean (page.getPageSize (), page.getPageNo (), page.getTotalCount (),
		        page.getTotalPages (), null, null);
		jsonMap.put ("pageInfo", pageBean);
		jsonMap.put ("data", page.getResult ());
		jsonMap.put ("exception", exception);
		Struts2Utils.renderJson (jsonMap);

		return null;
	}

	

	/**
	 * 导出excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public String export () throws Exception {

		String jsonString = Struts2Utils.getRequest ().getParameter ("_gt_json");
		this.logger.info ("jsonString=" + jsonString);

		SigmaBean<RmsInterface> sigmaBean = JsonBinder.buildNonNullBinder ().getMapper ()
		        .readValue (jsonString, new TypeReference<SigmaBean<RmsInterface>> () {
		        });

		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put ("paramFuncName", (String) sigmaBean.getParameters ().get ("paramFuncName"));
		paramMap.put ("paramFuncNameZh", (String) sigmaBean.getParameters ().get ("paramFuncNameZh"));
		//paramMap.put ("paramIUrl", (String) sigmaBean.getParameters ().get ("paramIUrl"));
		paramMap.put ("paramFuncDesc", (String) sigmaBean.getParameters ().get ("paramFuncDesc"));

		Workbook workBook = functionManager.exportFunctionExcel (paramMap);
		Struts2Utils.getResponse ().setHeader ("Pragma", "public");
		Struts2Utils.getResponse ().setHeader ("Expires", "0");
		Struts2Utils.getResponse ().setHeader ("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		Struts2Utils.getResponse ().setHeader ("Content-Type", "application/force-download");
		Struts2Utils.getResponse ().setHeader ("Content-Type", "application/octet-stream");
		Struts2Utils.getResponse ().setHeader ("Content-Type", "application/download");

		Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=\"rms_function_list.xlsx\"");
		Struts2Utils.getResponse ().setHeader ("Content-Transfer-Encoding", "binary");

		OutputStream os = Struts2Utils.getResponse ().getOutputStream ();

		workBook.write (os);
		return null;
	}
}
