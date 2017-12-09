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

import com.rms.commons.global.RmsGlobal;
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
import com.rms.utils.RmsUtils;
import com.rms.utils.SigmaPageInfoBean;
import com.rms.web.CrudActionSupport;

@Namespace ("/tdd")
// @Results({ @Result(name = InterfaceAction.RELOAD, location =
// "interface.action", type = "redirect") })
public class InterfaceAction extends CrudActionSupport<RmsInterface> {

	private static final long	serialVersionUID	= 8248163836557189340L;

	@Autowired
	private InterfaceManager	interfaceManager;

	@Autowired
	private DictManager	        dictManager;
	
	@Autowired
	private FunctionManager funcManger;

	// 页面数据集
	private Page<RmsInterface>	page	         = new Page<RmsInterface> (10);

	// 页面参数集合
	private Map<String, Object>	pageMap	         = new HashMap<String, Object> ();

	private Integer	            IId;

	private RmsInterface	    entity;

	@Override
	public RmsInterface getModel () {

		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public String list () throws Exception {

		Struts2Utils.getRequest ().setAttribute ("listCategory", interfaceManager.getAllCategoryNoPage ("ICateName", true));
		Struts2Utils.getRequest ().setAttribute ("listFunc", funcManger.getAllFuncNoPage("funcName", true));
		
		return SUCCESS;
	}

	@Override
	public String input () throws Exception {

		Struts2Utils.getRequest ().setAttribute ("listCategory", interfaceManager.getAllCategoryNoPage ("ICateName",true));

		List<RmsDictDetail> lstDictDetailType = dictManager.getDictDetailByDictName ("param_type");
		StringBuilder sbDictDetailSelect = new StringBuilder ("<select  name='param_type'>");
		for (RmsDictDetail rmsDictDetail : lstDictDetailType) {
			sbDictDetailSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
			        + "</option>");
		}
		sbDictDetailSelect.append ("</select>");
		Struts2Utils.getRequest ().setAttribute ("strParamType", sbDictDetailSelect.toString ());

		List<RmsDictDetail> lstDictDetailRequired = dictManager.getDictDetailByDictName ("param_is_required");
		sbDictDetailSelect = new StringBuilder ("<select  name='param_is_required'>");
		for (RmsDictDetail rmsDictDetail : lstDictDetailRequired) {
			sbDictDetailSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
			        + "</option>");
		}
		sbDictDetailSelect.append ("</select>");

		Struts2Utils.getRequest ().setAttribute ("strParamIsRequired", sbDictDetailSelect.toString ());
		if (IId != null) {
			List<RmsInterfaceParam> lstParams = interfaceManager.getParamsByInterfaceId (IId);
			for (RmsInterfaceParam rmsInterfaceParam : lstParams) {

				StringBuilder typeSelect = new StringBuilder ("<select  name='param_type' style='width: 80%'>");
				for (RmsDictDetail rmsDictDetail : lstDictDetailType) {

					if (rmsDictDetail.getDetailName ().equals (rmsInterfaceParam.getIpType ())) {
						typeSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "' selected>"
						        + rmsDictDetail.getDetailValue () + "</option>");
					}
					else {
						typeSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
						        + "</option>");
					}
				}
				typeSelect.append ("</select>");

				StringBuilder requiredSelect = new StringBuilder ("<select  name='param_is_required' style='width: 80%'>");
				for (RmsDictDetail rmsDictDetail : lstDictDetailRequired) {
					if (rmsDictDetail.getDetailName ().equals (rmsInterfaceParam.getIpRequired ())) {
						requiredSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "' selected>"
						        + rmsDictDetail.getDetailValue () + "</option>");
					}
					else {
						requiredSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
						        + "</option>");
					}
				}
				requiredSelect.append ("</select>");

				rmsInterfaceParam.setSelectIpType (typeSelect.toString ());
				rmsInterfaceParam.setSelectIpRequired (requiredSelect.toString ());
			}

			Struts2Utils.getRequest ().setAttribute ("lstParams", lstParams);
		}

		return "input";
	}

	@Override
	public String save () throws Exception {

		String exception = "";
		Map<TddEnum, Object> jsonMap = new HashMap<TddEnum, Object> ();
		HttpServletRequest request = Struts2Utils.getRequest ();
		String cateIds = request.getParameter ("cateId");
		List<String> lstcateIdsIds = Arrays.asList (cateIds.split (RmsGlobal.CONTENT_SPLIT));

		String paramNames = request.getParameter ("paramNames");
		String paramValues = request.getParameter ("paramValues");
		String paramDescs = request.getParameter ("paramDescs");
		String paramTypes = request.getParameter ("paramTypes");
		String paramRequireds = request.getParameter ("paramRequireds");

		if (StringUtils.endsWith (paramValues, RmsGlobal.CONTENT_SPLIT)) {
			paramValues += "NULL";
		}

		if (StringUtils.endsWith (paramDescs, RmsGlobal.CONTENT_SPLIT)) {
			paramDescs += "NULL";
		}
		List<String> lstNames = Arrays.asList (paramNames.split (RmsGlobal.CONTENT_SPLIT));
		List<String> lstValues = Arrays.asList (paramValues.split (RmsGlobal.CONTENT_SPLIT));
		List<String> lstDescs = Arrays.asList (paramDescs.split (RmsGlobal.CONTENT_SPLIT));
		List<String> lstTypes = Arrays.asList (paramTypes.split (RmsGlobal.CONTENT_SPLIT));
		List<String> lstRequireds = Arrays.asList (paramRequireds.split (RmsGlobal.CONTENT_SPLIT));

		Map<String, List<String>> paramInfoMap = new HashMap<String, List<String>> ();
		paramInfoMap.put ("lstNames", lstNames);
		paramInfoMap.put ("lstValues", lstValues);
		paramInfoMap.put ("lstDescs", lstDescs);
		paramInfoMap.put ("lstTypes", lstTypes);
		paramInfoMap.put ("lstRequireds", lstRequireds);

		try {
			Integer rtnInt = interfaceManager.savaInterface (entity, lstcateIdsIds, paramInfoMap);
			exception = "新增成功";
			jsonMap.put (TddEnum.RTNTYPE, TddEnum.RTNTYPEY.toString ());
			jsonMap.put (TddEnum.RTNID, rtnInt);
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

		interfaceManager.deleteInterface (entity);
		return list ();
	}

	public Integer getIId () {

		return IId;
	}

	public void setIId (Integer iId) {

		IId = iId;
	}

	public RmsInterface getEntity () {

		return entity;
	}

	public void setEntity (RmsInterface entity) {

		this.entity = entity;
	}

	@Override
	protected void prepareModel () throws Exception {

		if (IId != null) {
			entity = interfaceManager.getInterfaceById (IId);
		}
		else {
			entity = new RmsInterface ();
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
		paramMap.put ("paramIName", (String) sigmaBean.getParameters ().get ("paramIName"));
		String paramUrl = (String) sigmaBean.getParameters ().get ("paramIUrl");
		paramUrl = RmsUtils.doWithUrl(paramUrl, RmsGlobal.INTERFACE_HOST, RmsGlobal.INTERFACE_SUFFIX);
		paramMap.put ("paramIUrl", paramUrl);

		String paramHasParam = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramHasParam"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramHasParam");
		paramMap.put ("paramHasParam", paramHasParam);
		
		String paramFuncId = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramFuncId"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramFuncId");
		paramMap.put ("paramFuncId", paramFuncId);
		
		String paramICateId = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramICateId"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramICateId");
		paramMap.put ("paramICateId", paramICateId);
		
		String paramStatus = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramStatus"), "-1") ? null : (String) sigmaBean
		        .getParameters ().get ("paramStatus");
		paramMap.put ("paramStatus", paramStatus);

		if (!page.isOrderBySetted ()) {
			page.setOrderBy ("IId");
			page.setOrder (Page.DESC);
		}
		page = interfaceManager.getInterfacePage (paramMap, page);
		Map<String, Object> jsonMap = new HashMap<String, Object> ();

		SigmaPageInfoBean pageBean = new SigmaPageInfoBean (page.getPageSize (), page.getPageNo (), page.getTotalCount (),
		        page.getTotalPages (), null, null);
		jsonMap.put ("pageInfo", pageBean);
		jsonMap.put ("data", page.getResult ());
		jsonMap.put ("exception", exception);
		Struts2Utils.renderJson (jsonMap);

		return null;
	}

	public String viewJson () throws Exception {

		prepareModel ();

		String newJson = interfaceManager.getEncryptJsonFrom(entity);
		
		Struts2Utils.getResponse ().setContentType ("text/html; charset=utf-8");
		Struts2Utils.getResponse ().getWriter ().write (newJson);
		return null;
	}

	/*public String viewEncrypt () throws Exception {

		prepareModel ();
		
		String newJson = interfaceManager.getEncryptJsonFrom(entity, ApplicationProperties.get ("interface_remote_url"));
		
		Struts2Utils.getResponse ().setContentType ("text/html; charset=utf-8");
		Struts2Utils.getResponse ().getWriter ().write (newJson);
		return null;
	}*/

	
	/**
	 * 导出excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public String export () throws Exception {

		String jsonString = Struts2Utils.getRequest ().getParameter ("_gt_json");
		this.logger.info ("jsonString=" + jsonString);

		SigmaBean sigmaBean = JsonBinder.buildNonNullBinder ().getMapper ()
		        .readValue (jsonString, new TypeReference<SigmaBean<RmsInterface>> () {
		        });

		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put ("paramIName", (String) sigmaBean.getParameters ().get ("paramIName"));
		paramMap.put ("paramIUrl", (String) sigmaBean.getParameters ().get ("paramIUrl"));

		String paramHasParam = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramHasParam"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramHasParam");
		paramMap.put ("paramHasParam", paramHasParam);

		String paramFuncId = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramFuncId"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramFuncId");
		paramMap.put ("paramFuncId", paramFuncId);
		
		String paramICateId = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramICateId"), "-1") ? null
		        : (String) sigmaBean.getParameters ().get ("paramICateId");
		paramMap.put ("paramICateId", paramICateId);

		String paramStatus = StringUtils.equals ((String) sigmaBean.getParameters ().get ("paramStatus"), "-1") ? null : (String) sigmaBean
		        .getParameters ().get ("paramStatus");
		paramMap.put ("paramStatus", paramStatus);

		Workbook workBook = interfaceManager.exportInterfaceExcel (paramMap);
		Struts2Utils.getResponse ().setHeader ("Pragma", "public");
		Struts2Utils.getResponse ().setHeader ("Expires", "0");
		Struts2Utils.getResponse ().setHeader ("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		Struts2Utils.getResponse ().setHeader ("Content-Type", "application/force-download");
		Struts2Utils.getResponse ().setHeader ("Content-Type", "application/octet-stream");
		Struts2Utils.getResponse ().setHeader ("Content-Type", "application/download");

		Struts2Utils.getResponse ().setHeader ("Content-Disposition", "attachment;filename=\"rms_interface_list.xlsx\"");
		Struts2Utils.getResponse ().setHeader ("Content-Transfer-Encoding", "binary");

		// Struts2Utils.getResponse().setContentType("application/msexcel");
		// Struts2Utils.getResponse ().setContentType ("application/vnd.ms-excel");

		OutputStream os = Struts2Utils.getResponse ().getOutputStream ();

		workBook.write (os);
		return null;
	}

	public String view () throws Exception {
		prepareModel ();
		Struts2Utils.getRequest ().setAttribute ("listCategory", interfaceManager.getAllCategoryNoPage ("ICateName",true));

		List<RmsDictDetail> lstDictDetailType = dictManager.getDictDetailByDictName ("param_type");
		StringBuilder sbDictDetailSelect = new StringBuilder ("<select  name='param_type'>");
		for (RmsDictDetail rmsDictDetail : lstDictDetailType) {
			sbDictDetailSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
			        + "</option>");
		}
		sbDictDetailSelect.append ("</select>");
		Struts2Utils.getRequest ().setAttribute ("strParamType", sbDictDetailSelect.toString ());

		List<RmsDictDetail> lstDictDetailRequired = dictManager.getDictDetailByDictName ("param_is_required");
		sbDictDetailSelect = new StringBuilder ("<select  name='param_is_required'>");
		for (RmsDictDetail rmsDictDetail : lstDictDetailRequired) {
			sbDictDetailSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
			        + "</option>");
		}
		sbDictDetailSelect.append ("</select>");

		Struts2Utils.getRequest ().setAttribute ("strParamIsRequired", sbDictDetailSelect.toString ());
		if (IId != null) {
			List<RmsInterfaceParam> lstParams = interfaceManager.getParamsByInterfaceId (IId);
			for (RmsInterfaceParam rmsInterfaceParam : lstParams) {

				StringBuilder typeSelect = new StringBuilder ("<select  name='param_type' style='width: 80%'>");
				for (RmsDictDetail rmsDictDetail : lstDictDetailType) {

					if (rmsDictDetail.getDetailName ().equals (rmsInterfaceParam.getIpType ())) {
						typeSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "' selected>"
						        + rmsDictDetail.getDetailValue () + "</option>");
					}
					else {
						typeSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
						        + "</option>");
					}
				}
				typeSelect.append ("</select>");

				StringBuilder requiredSelect = new StringBuilder ("<select  name='param_is_required' style='width: 80%'>");
				for (RmsDictDetail rmsDictDetail : lstDictDetailRequired) {
					if (rmsDictDetail.getDetailName ().equals (rmsInterfaceParam.getIpRequired ())) {
						requiredSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "' selected>"
						        + rmsDictDetail.getDetailValue () + "</option>");
					}
					else {
						requiredSelect.append ("<option value='" + rmsDictDetail.getDetailName () + "'>" + rmsDictDetail.getDetailValue ()
						        + "</option>");
					}
				}
				requiredSelect.append ("</select>");

				rmsInterfaceParam.setSelectIpType (typeSelect.toString ());
				rmsInterfaceParam.setSelectIpRequired (requiredSelect.toString ());
			}

			Struts2Utils.getRequest ().setAttribute ("lstParams", lstParams);
		}

		return "view";
	}
}
