package com.rms.service.tdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rms.commons.global.RmsGlobal;
import com.rms.commons.global.RmsQuerySqlGlobal;
import com.rms.dao.tdd.DictDetailDao;
import com.rms.dao.tdd.InterfaceCategoryDao;
import com.rms.dao.tdd.InterfaceCategoryRelationDao;
import com.rms.dao.tdd.InterfaceDao;
import com.rms.dao.tdd.InterfaceParamDao;
import com.rms.dao.tdd.InterfaceParamRelationDao;
import com.rms.entity.tdd.RmsFunction;
import com.rms.entity.tdd.RmsInterface;
import com.rms.entity.tdd.RmsInterfaceCategory;
import com.rms.entity.tdd.RmsInterfaceCategoryRelation;
import com.rms.entity.tdd.RmsInterfaceCategoryRelationId;
import com.rms.entity.tdd.RmsInterfaceParam;
import com.rms.entity.tdd.RmsInterfaceParamRelation;
import com.rms.entity.tdd.RmsInterfaceParamRelationId;
import com.rms.modules.orm.Page;
import com.rms.modules.utils.ConvertUtil;
import com.rms.tddenum.TddEnum;
import com.rms.utils.ApplicationProperties;
import com.rms.utils.DESUtils;
import com.rms.utils.JsoupUtil;
import com.rms.utils.POIUtils;
import com.rms.utils.RmsUtils;

@Service
@Transactional (
    rollbackFor = Exception.class)
public class InterfaceManager {

	@Autowired
	private InterfaceCategoryDao	     interfaceCategoryDao;

	@Autowired
	private InterfaceCategoryRelationDao	interfaceCategoryRelationDao;

	@Autowired
	private InterfaceDao	             interfaceDao;

	@Autowired
	private InterfaceParamDao	         interfaceParamDao;

	@Autowired
	private InterfaceParamRelationDao	 interfaceParamRelationDao;

	@Autowired
	private DictManager dictManager;
	
	@Transactional (
	    readOnly = true)
	public RmsInterface getInterfaceById (Integer iId) {

		return interfaceDao.get (iId);
	}

	@Transactional (
	    readOnly = true)
	public RmsInterfaceCategory getInterfaceCategoryById (Integer icateId) {

		return interfaceCategoryDao.get (icateId);
	}

	@Transactional (
	    readOnly = true)
	public Page<RmsInterface> getInterfacePage (Map<String, Object> queryMap, Page<RmsInterface> page) {

		Criterion [] criterions = interfaceDao.getQueryCriterions (queryMap);
		if (criterions != null) {

			page = interfaceDao.findPage (page, criterions);
		}
		else {
			page = interfaceDao.getAll (page);
		}

		return page;
	}

	@Transactional (
	    readOnly = true)
	public List<RmsInterface> getInterfaceNoPage (Map<String, Object> queryMap) {

		Criterion [] criterions = interfaceDao.getQueryCriterions (queryMap);

		List<RmsInterface> lstResult = new ArrayList<RmsInterface> ();
		if (criterions != null) {
			lstResult = interfaceDao.find (criterions);
		}
		else {
			lstResult = interfaceDao.getAll ();
		}

		return lstResult;
	}

	@Transactional (
	    readOnly = true)
	public SXSSFWorkbook exportInterfaceExcel (Map<String, Object> queryMap) {

		Map<String, String> typeDictMap = dictManager.getDictDetailNamesValueMap ("param_type");
		Map<String, String> requiredDictMap = dictManager.getDictDetailNamesValueMap ("param_is_required");
		Map<String, String> titleMap = new HashMap<String, String> ();
		titleMap.put ("name", "接口名称");
		titleMap.put ("nameZh", "接口中文名称");
		titleMap.put ("category", "接口分类");
		titleMap.put ("status", "开发状态");
		titleMap.put ("desc", "接口描述");
		titleMap.put ("url", "接口地址");
		titleMap.put ("param", "参数");
		titleMap.put ("paramDesc", "参数");
		titleMap.put ("json", "生成json串");
		titleMap.put ("returnDesc", "json说明");
		
		titleMap.put ("paramTitle1", "参数名称");
		titleMap.put ("paramTitle2", "参数描述");
		titleMap.put ("paramTitle3", "是否必输");
		titleMap.put ("paramTitle4", "参数类型");

		List<RmsInterface> lstResult = this.getInterfaceNoPage (queryMap);
		// HSSFWorkbook workBook = new HSSFWorkbook();
		SXSSFWorkbook workBook = new SXSSFWorkbook ();
		Map<Map<Integer, String>, List<RmsInterface>> cateoryInterfaceMap = new LinkedHashMap<Map<Integer, String>, List<RmsInterface>> ();

		Set<Map<Integer, String>> cateItSet = new HashSet<Map<Integer, String>> ();

		for (RmsInterface rmsInterface : lstResult) {
			Map<Integer, String> cateMap = new HashMap<Integer, String> ();
			cateMap.put (rmsInterface.getCateId (), rmsInterface.getCateName ());
			cateItSet.add (cateMap);
		}

		Iterator<Map<Integer, String>> cateIt = cateItSet.iterator ();

		while (cateIt.hasNext ()) {
			Map<Integer, String> cateMap = cateIt.next ();
			Integer integerKey = null;
			String strValue = null;

			for (Map.Entry<Integer, String> entry : cateMap.entrySet ()) {
				integerKey = entry.getKey ();
				strValue = entry.getValue ();
			}

			List<RmsInterface> lstTmp = new ArrayList<RmsInterface> ();
			for (RmsInterface rmsInterface : lstResult) {

				if (rmsInterface.getCateId ().equals (integerKey)) {
					lstTmp.add (rmsInterface);
				}
			}

			cateoryInterfaceMap.put (cateMap, lstTmp);
		}

		for (Map.Entry<Map<Integer, String>, List<RmsInterface>> entry : cateoryInterfaceMap.entrySet ()) {
			Map<Integer, String> keyMap = entry.getKey ();
			Integer integerKey = null;
			String strValue = null;
			for (Map.Entry<Integer, String> entryKeyMap : keyMap.entrySet ()) {
				integerKey = entryKeyMap.getKey ();
				strValue = entryKeyMap.getValue ();
			}

			Sheet sheet = workBook.createSheet (strValue);

			sheet.setColumnWidth (0, 20 * 256);
			sheet.setColumnWidth (1, 50 * 256);
			sheet.setColumnWidth (2, 20 * 256);
			sheet.setColumnWidth (3, 50 * 256);
			List<RmsInterface> valueList = entry.getValue ();

			Integer rowIndex = 0;
			for (int index = 0; index < valueList.size (); index ++) {

				RmsInterface valueInterface = valueList.get (index);
				Row row = sheet.createRow (index + rowIndex);

				// 接口表格第一行 接口名称,接口中文名称
				Cell cellNameTitle = row.createCell (0);
				cellNameTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));;
				cellNameTitle.setCellValue (new HSSFRichTextString (titleMap.get ("name")));

				Cell cellNameValue = row.createCell (1);
				cellNameValue.setCellStyle (POIUtils.getSXValueStyle (workBook));
				cellNameValue.setCellValue (new HSSFRichTextString (valueInterface.getIName ()));

				Cell cellNameZhTitle = row.createCell (2);
				cellNameZhTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));
				cellNameZhTitle.setCellValue (new HSSFRichTextString (titleMap.get ("nameZh")));

				Cell cellNameZhValue = row.createCell (3);
				cellNameZhValue.setCellStyle (POIUtils.getSXValueStyle (workBook));
				cellNameZhValue.setCellValue (new HSSFRichTextString (valueInterface.getINameZh ()));

				rowIndex ++;

				// 接口表格第二行 接口分类 开发状态
				row = sheet.createRow (index + rowIndex);
				Cell cellCategoryTitle = row.createCell (0);
				cellCategoryTitle.setCellValue (new HSSFRichTextString (titleMap.get ("category")));
				cellCategoryTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));

				Cell cellCategoryValue = row.createCell (1);
				cellCategoryValue.setCellValue (new HSSFRichTextString (valueInterface.getCateName ()));
				cellCategoryValue.setCellStyle (POIUtils.getSXValueStyle (workBook));

				Cell cellStatusTitle = row.createCell (2);
				cellStatusTitle.setCellValue (new HSSFRichTextString (titleMap.get ("status")));
				cellStatusTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));

				Cell cellStatusZhValue = row.createCell (3);
				cellStatusZhValue.setCellValue (new HSSFRichTextString (valueInterface.getStatus ()));
				cellStatusZhValue.setCellStyle (POIUtils.getSXValueStyle (workBook));

				rowIndex ++;

				// 接口表格第三行 接口描述
				row = sheet.createRow (index + rowIndex);
				row.setHeightInPoints (5 * 20);
				Cell cellDescTitle = row.createCell (0);
				cellDescTitle.setCellValue (new HSSFRichTextString (titleMap.get ("desc")));
				cellDescTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));
				Cell cellDescValue = row.createCell (1);
				row.createCell (2);
				row.getCell (2).setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (3);
				row.getCell (3).setCellStyle (POIUtils.getSXValueStyle (workBook));
				sheet.addMergedRegion (new CellRangeAddress (index + rowIndex, index + rowIndex, 1, 3));
				cellDescValue.setCellValue (new HSSFRichTextString (valueInterface.getIDesc ()));
				cellDescValue.setCellStyle (POIUtils.getSXValueStyle (workBook));
				rowIndex ++;

				// 接口表格第四行 接口地址
				row = sheet.createRow (index + rowIndex);
				Cell cellUrlTitle = row.createCell (0);
				cellUrlTitle.setCellValue (new HSSFRichTextString (titleMap.get ("url")));
				cellUrlTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));

				Cell cellUrlValue = row.createCell (1);
				cellUrlValue.setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (2);
				row.getCell (2).setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (3);
				row.getCell (3).setCellStyle (POIUtils.getSXValueStyle (workBook));
				sheet.addMergedRegion (new CellRangeAddress (index + rowIndex, index + rowIndex, 1, 3));
				cellUrlValue.setCellValue (new HSSFRichTextString (valueInterface.getIUrl ()));
				rowIndex ++;

				// 接口表格第五行 接口参数
				row = sheet.createRow (index + rowIndex);
				Cell cellParamTitle = row.createCell (0);
				cellParamTitle.setCellValue (new HSSFRichTextString (titleMap.get ("param")));
				cellParamTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));

				Cell cellParamValue = row.createCell (1);
				cellParamValue.setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (2);
				row.getCell (2).setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (3);
				row.getCell (3).setCellStyle (POIUtils.getSXValueStyle (workBook));
				sheet.addMergedRegion (new CellRangeAddress (index + rowIndex, index + rowIndex, 1, 3));
				String strParams = StringUtils.equals ("y", valueInterface.getIHasParam ()) ? valueInterface.getParams () : "无参数";
				cellParamValue.setCellValue (new HSSFRichTextString (strParams));
				rowIndex ++;

				if (StringUtils.equals ("y", valueInterface.getIHasParam ())) {
					
					//  参数表头
					row = sheet.createRow (index + rowIndex);
					
					Cell cellParamTitle1 = row.createCell (0);
					cellParamTitle1.setCellValue (new HSSFRichTextString (titleMap.get ("paramTitle1")));
					cellParamTitle1.setCellStyle (POIUtils.getSXTitleStyle (workBook));
					
					Cell cellParamTitle2 = row.createCell (1);
					cellParamTitle2.setCellValue (new HSSFRichTextString (titleMap.get ("paramTitle2")));
					cellParamTitle2.setCellStyle (POIUtils.getSXTitleStyle (workBook));
					
					Cell cellParamTitle3 = row.createCell (2);
					cellParamTitle3.setCellValue (new HSSFRichTextString (titleMap.get ("paramTitle3")));
					cellParamTitle3.setCellStyle (POIUtils.getSXTitleStyle (workBook));
					
					Cell cellParamTitle4 = row.createCell (3);
					cellParamTitle4.setCellValue (new HSSFRichTextString (titleMap.get ("paramTitle4")));
					cellParamTitle4.setCellStyle (POIUtils.getSXTitleStyle (workBook));
					rowIndex ++;
					
					//  参数说明
					
					Set<RmsInterfaceParam> tmpSet = valueInterface.getRmsInterfaceParamSet ();
					if (tmpSet != null && !tmpSet.isEmpty ()) {
						
						Iterator<RmsInterfaceParam> it = tmpSet.iterator ();
						
						while (it.hasNext ()) {
							row = sheet.createRow (index + rowIndex);
							RmsInterfaceParam rmsInterfaceParam = it.next ();
							
							Cell cellParamDetailTitle1 = row.createCell (0);
							cellParamDetailTitle1.setCellValue (new HSSFRichTextString (rmsInterfaceParam.getIpName ()));
							cellParamDetailTitle1.setCellStyle (POIUtils.getSXValueStyle (workBook));
							
							Cell cellParamDetailValue1 = row.createCell (1);
							cellParamDetailValue1.setCellValue (new HSSFRichTextString (rmsInterfaceParam.getIpDesc ()));
							cellParamDetailValue1.setCellStyle (POIUtils.getSXValueStyle (workBook));
							
							Cell cellParamDetailTitle2 = row.createCell (2);
							String ipRequired = rmsInterfaceParam.getIpRequired ();
							String strRequired = StringUtils.isEmpty (ipRequired)? requiredDictMap.get (0):requiredDictMap.get (ipRequired);
							cellParamDetailTitle2.setCellValue (new HSSFRichTextString (strRequired));
							cellParamDetailTitle2.setCellStyle (POIUtils.getSXValueStyle (workBook));
							
							Cell cellParamDetailValue2 = row.createCell (3);
							String ipType = rmsInterfaceParam.getIpType ();
							String strType = StringUtils.isEmpty (ipType)? typeDictMap.get (0) : typeDictMap.get (ipType);
							cellParamDetailValue2.setCellValue (new HSSFRichTextString (strType));
							cellParamDetailValue2.setCellStyle (POIUtils.getSXValueStyle (workBook));
							
							rowIndex ++;
						}
					}
					
				}
				
				// 生成json串
				row = sheet.createRow (index + rowIndex);
				row.setHeightInPoints (5 * 20);
				Cell cellJsonTitle = row.createCell (0);
				cellJsonTitle.setCellValue (new HSSFRichTextString (titleMap.get ("json")));
				cellJsonTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));

				Cell cellJsonValue = row.createCell (1);
				cellJsonValue.setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (2);
				row.getCell (2).setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (3);
				row.getCell (3).setCellStyle (POIUtils.getSXValueStyle (workBook));
				sheet.addMergedRegion (new CellRangeAddress (index + rowIndex, index + rowIndex, 1, 3));
				cellJsonValue.setCellValue (new HSSFRichTextString (valueInterface.getIJson ()));
				rowIndex ++;

				// json说明
				row = sheet.createRow (index + rowIndex);
				row.setHeightInPoints (5 * 20);
				Cell cellReturnDescTitle = row.createCell (0);
				cellReturnDescTitle.setCellValue (new HSSFRichTextString (titleMap.get ("returnDesc")));
				cellReturnDescTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));

				Cell cellReturnDescValue = row.createCell (1);
				cellReturnDescValue.setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (2);
				row.getCell (2).setCellStyle (POIUtils.getSXValueStyle (workBook));
				row.createCell (3);
				row.getCell (3).setCellStyle (POIUtils.getSXValueStyle (workBook));
				sheet.addMergedRegion (new CellRangeAddress (index + rowIndex, index + rowIndex, 1, 3));
				cellReturnDescValue.setCellValue (new HSSFRichTextString (valueInterface.getIJsonDesc ()));
				rowIndex ++;
			}
		}

		return workBook;
	}

	@Transactional (
	    readOnly = true)
	public Page<RmsInterfaceCategory> getInterfaceCategoryPage (Map<String, Object> queryMap, Page<RmsInterfaceCategory> page) {

		List<Criterion> criterionList = new ArrayList<Criterion> ();

		if (queryMap != null && !queryMap.isEmpty ()) {

			String paramICateName = (String) queryMap.get ("paramICateName");

			if (StringUtils.isNotEmpty (paramICateName)) {

				Criterion criterionIName = Restrictions.like ("ICateName", paramICateName, MatchMode.ANYWHERE);
				criterionList.add (criterionIName);
			}

			Criterion [] criterions = criterionList.toArray (new Criterion [criterionList.size ()]);
			page = interfaceCategoryDao.findPage (page, criterions);
		}
		else {
			page = interfaceCategoryDao.getAll (page);
		}

		return page;
	}

	@Transactional (
	    readOnly = true)
	public List<RmsInterfaceCategory> getAllCategoryNoPage (String orderByProperty, boolean isAsc) {

		if (StringUtils.isNotEmpty(orderByProperty)) {
			
			return interfaceCategoryDao.getAll (orderByProperty, isAsc);
		}
		else {
			return interfaceCategoryDao.getAll ();
		}
	}

	/**
	 * 查询出分类下已有接口
	 * 
	 * @param iCateId
	 * @param isSelect
	 * @return
	 */
	@Transactional (
	    readOnly = true)
	public List<RmsInterface> getInterfaceListByCateId (Integer iCateId, TddEnum isSelect) {

		List<RmsInterface> listResult = new ArrayList<RmsInterface> ();

		if (iCateId != null) {

			if (TddEnum.interfaceSelect.equals (isSelect)) {
				listResult = interfaceDao.findInterfaceWithCataId (iCateId);
			}
			else{
				
				if (TddEnum.interfaceUnSelect.equals (isSelect)) {
					
					listResult = interfaceDao.findInterfaceWithoutCataId (iCateId);
				}
			}
		}
		else {

			if (TddEnum.interfaceUnSelect.equals (isSelect)) {
				
				listResult = interfaceDao.getAll ();
			}
			
			/*if (TddEnum.interfaceSelect.equals (isSelect)) {
				listResult = interfaceDao.findInterfaceWithCataId (iCateId);
			}
			else{
			}*/
		}

		return listResult;
	}

	/**
	 * 更具接口id查询接口下所有的参数
	 * 
	 * @param interfaceId
	 * @return
	 */
	@Transactional (
	    readOnly = true)
	public List<RmsInterfaceParam> getParamsByInterfaceId (Integer interfaceId) {

		return interfaceParamDao.findParamsByInterface (interfaceId);
	}

	/**
	 * 新增接口分类
	 * 
	 * @param rmsInterfaceCategory
	 * @param interfaceIds
	 * @throws Exception
	 */
	public void savaInterfaceCategory (RmsInterfaceCategory rmsInterfaceCategory, List<String> interfaceIds) throws Exception {

		if (ConvertUtil.isNotNull (interfaceIds) && rmsInterfaceCategory != null) {

			Integer cateId = rmsInterfaceCategory.getICateId ();

			if (cateId != null) {

				interfaceCategoryRelationDao.deleteRelationByCateId (cateId);
			}

			RmsInterfaceCategory entityAfterSave = interfaceCategoryDao.merge (rmsInterfaceCategory);
			for (String interfaceId : interfaceIds) {

				RmsInterfaceCategoryRelation rmsInterfaceCategoryRelation = new RmsInterfaceCategoryRelation ();
				RmsInterfaceCategoryRelationId rmsInterfaceCategoryRelationId = new RmsInterfaceCategoryRelationId ();
				rmsInterfaceCategoryRelationId.setICateId (entityAfterSave.getICateId ());
				rmsInterfaceCategoryRelationId.setIId (ConvertUtil.convertInt (interfaceId));
				rmsInterfaceCategoryRelation.setId (rmsInterfaceCategoryRelationId);

				interfaceCategoryRelationDao.save (rmsInterfaceCategoryRelation);
			}
		}
	}

	/**
	 * 删除接口分类
	 * 
	 * @param rmsInterfaceCategory
	 * @throws Exception
	 */
	public void deleteInterfaceCategory (RmsInterfaceCategory rmsInterfaceCategory) throws Exception {

		interfaceCategoryRelationDao.deleteRelationByCateId (rmsInterfaceCategory.getICateId ());
		interfaceCategoryDao.delete (rmsInterfaceCategory);
	}

	/**
	 * 新增接口
	 * 
	 * @param rmsInterface
	 * @param categoryIds
	 * @throws Exception
	 */
	public Integer savaInterface (RmsInterface rmsInterface, List<String> categoryIds, Map<String, List<String>> paramInfoMap)
	        throws Exception {

		String url = RmsUtils.doWithUrl(rmsInterface.getIUrl(), RmsGlobal.INTERFACE_HOST, RmsGlobal.INTERFACE_SUFFIX);
						
		rmsInterface.setIUrl(url);		
		Integer rtnInt = 0;
		if (ConvertUtil.isNotNull (categoryIds) && rmsInterface != null) {

			Integer interfaceId = rmsInterface.getIId ();

			if (interfaceId != null) {

				interfaceCategoryRelationDao.deleteRelationByInterfaceId (interfaceId);

				List<RmsInterfaceParam> lstResult = interfaceParamDao.findParamsByInterface (interfaceId);
				for (RmsInterfaceParam rmsInterfaceParam : lstResult) {
					interfaceParamDao.delete (rmsInterfaceParam);
				}
				interfaceParamRelationDao.deleteRelationByInterfaceId (interfaceId);
			}

			RmsInterface entityAfterSave = interfaceDao.merge (rmsInterface);
			rtnInt = entityAfterSave.getIId ();
			for (String cateId : categoryIds) {

				RmsInterfaceCategoryRelation rmsInterfaceCategoryRelation = new RmsInterfaceCategoryRelation ();
				RmsInterfaceCategoryRelationId rmsInterfaceCategoryRelationId = new RmsInterfaceCategoryRelationId ();
				rmsInterfaceCategoryRelationId.setICateId (ConvertUtil.convertInt (cateId));
				rmsInterfaceCategoryRelationId.setIId (entityAfterSave.getIId ());
				rmsInterfaceCategoryRelation.setId (rmsInterfaceCategoryRelationId);

				interfaceCategoryRelationDao.save (rmsInterfaceCategoryRelation);
			}

			Map<String, String> paramMap = new HashMap<String, String> ();
			Map<String, String> encryptMap = new HashMap<String, String> ();
			DESUtils crypt = new DESUtils (DESUtils.AES_MICHI_MIN);
			
			if (StringUtils.equals ("y", rmsInterface.getIHasParam ())) {
				if (ConvertUtil.isNotNull (paramInfoMap)) {
					List<String> lstNames = paramInfoMap.get ("lstNames");
					List<String> lstValues = paramInfoMap.get ("lstValues");
					List<String> lstDescs = paramInfoMap.get ("lstDescs");
					List<String> lstTypes = paramInfoMap.get ("lstTypes");
					List<String> lstRequireds = paramInfoMap.get ("lstRequireds");

					if (ConvertUtil.isNotNull (lstNames)) {
						for (int i = 0; i < lstNames.size (); i ++) {
							String paramName = lstNames.get (i);
							String paramValue = lstValues.get (i);
							String paramDesc = lstDescs.get (i);
							String paramType = lstTypes.get (i);
							String paramRequired = lstRequireds.get (i);

							paramValue = StringUtils.equals ("NULL", paramValue) ? "" : paramValue;
							paramDesc = StringUtils.equals ("NULL", paramDesc) ? "" : paramDesc;

							RmsInterfaceParam rmsInterfaceParam = new RmsInterfaceParam ();
							rmsInterfaceParam.setIpName (paramName);
							rmsInterfaceParam.setIpValue (paramValue);
							rmsInterfaceParam.setIpDesc (paramDesc);
							rmsInterfaceParam.setIpType (paramType);
							rmsInterfaceParam.setIpRequired (paramRequired);
							RmsInterfaceParam paramSave = interfaceParamDao.merge (rmsInterfaceParam);

							RmsInterfaceParamRelationId rmsInterfaceParamRelationId = new RmsInterfaceParamRelationId ();
							rmsInterfaceParamRelationId.setIId (entityAfterSave.getIId ());
							rmsInterfaceParamRelationId.setIpId (paramSave.getIpId ());
							RmsInterfaceParamRelation rmsInterfaceParamRelation = new RmsInterfaceParamRelation ();
							rmsInterfaceParamRelation.setId (rmsInterfaceParamRelationId);
							interfaceParamRelationDao.save (rmsInterfaceParamRelation);
							
							paramMap.put (paramName, paramValue);
						}
					}
				}
				
				String jsonParam = JSONObject.fromObject (paramMap).toString ();
				System.out.println ("jsonParam="+jsonParam);
				String encryJsonParam = crypt.encrypt (crypt.encrypt (crypt.encrypt (jsonParam))); 
				encryptMap.put ("key", encryJsonParam);
			}
			else {
				String encryJsonParam = crypt.encrypt (crypt.encrypt (crypt.encrypt (RmsGlobal.NULL_KEY))); 
				encryptMap.put ("key", encryJsonParam);
			}
			
			Map<String, String> jsonMap = new HashMap<String, String> ();
			String json = JsoupUtil.getJsonStrByPost (entityAfterSave.getUrlStr(), encryptMap);
			entityAfterSave.setIJsonEncrypt (json);
			if (StringUtils.isNotEmpty  (json)) {
				String value = JSONObject.fromObject (json).getString ("value");
				String decodeStr = crypt.decrypt (crypt.decrypt (crypt.decrypt (value)));
				
				jsonMap.put ("value", decodeStr);
			}
			else {
				jsonMap.put ("value", RmsGlobal.JSON_ERROR);
			}
			String newJson = JSONObject.fromObject (jsonMap).toString ();
			System.out.println ("newJson="+newJson);
			// String jsonstr = this.getEncryptJsonFrom(entityAfterSave ,null);
			entityAfterSave.setIJson (newJson);
			interfaceDao.save (entityAfterSave);
			
		}
		
		return rtnInt;
	}

	/**
	 * 删除接口
	 * 
	 * @param rmsInterface
	 * @throws Exception
	 */
	public void deleteInterface (RmsInterface rmsInterface) throws Exception {

		Integer interfaceId = rmsInterface.getIId ();
		if (interfaceId != null) {

			List<RmsInterfaceParam> lstResult = interfaceParamDao.findParamsByInterface (interfaceId);
			for (RmsInterfaceParam rmsInterfaceParam : lstResult) {
				interfaceParamDao.delete (rmsInterfaceParam);
			}
			interfaceParamRelationDao.deleteRelationByInterfaceId (interfaceId);
		}

		interfaceCategoryRelationDao.deleteRelationByInterfaceId (rmsInterface.getIId ());
		interfaceDao.delete (rmsInterface);
	}

	@Transactional
	public String getEncryptJsonFrom(RmsInterface rmsObj) throws Exception {
		
		String url = rmsObj.getUrlStr();
		System.out.println("urlStr="+url);
		Set<RmsInterfaceParam> paramSet = rmsObj.getRmsInterfaceParamSet ();
		Map<String, String> paramMap = new HashMap<String, String> ();
		Map<String, String> encryptMap = new HashMap<String, String> ();
		DESUtils crypt = new DESUtils (DESUtils.AES_MICHI_MIN);
		if (StringUtils.equals ("y", rmsObj.getIHasParam ())) {

			if (paramSet != null && !paramSet.isEmpty ()) {
				Iterator<RmsInterfaceParam> it = paramSet.iterator ();
				while (it.hasNext ()) {
					RmsInterfaceParam rmsInterfaceParam = it.next ();
					System.out.println (rmsInterfaceParam.getIpName () + "=" + rmsInterfaceParam.getIpValue ());
					paramMap.put (rmsInterfaceParam.getIpName (), rmsInterfaceParam.getIpValue ());
				}

			}
			
			String jsonParam = JSONObject.fromObject (paramMap).toString ();
			System.out.println ("jsonParam="+jsonParam);
			String encryJsonParam = crypt.encrypt (crypt.encrypt (crypt.encrypt (jsonParam))); 
			System.out.println("encryJsonParam="+encryJsonParam);
			encryptMap.put ("key", encryJsonParam);
		}
		else {
			
			String encryJsonParam = crypt.encrypt (crypt.encrypt (crypt.encrypt (RmsGlobal.JSON_ERROR))); 
			encryptMap.put ("key", encryJsonParam);

		}
		
		Map<String, String> jsonMap = new HashMap<String, String> ();
		String json = JsoupUtil.getJsonStrByPost (url, encryptMap);
		rmsObj.setIJsonEncrypt (json);
		if (StringUtils.isNotEmpty  (json)) {
			String value = JSONObject.fromObject (json).getString ("value");
			String decodeStr = crypt.decrypt (crypt.decrypt (crypt.decrypt (value)));
			jsonMap.put ("value", decodeStr);
		}
		else {
			jsonMap.put ("value", RmsGlobal.JSON_ERROR);
		}
		String newJson = JSONObject.fromObject (jsonMap).toString ();
		rmsObj.setIJson (newJson);
		interfaceDao.save (rmsObj);
		System.out.println ("newJson="+newJson);
		return newJson;
	}
	
	
}
