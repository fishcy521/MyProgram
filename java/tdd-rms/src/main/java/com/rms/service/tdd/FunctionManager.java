package com.rms.service.tdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rms.dao.tdd.FunctionDao;
import com.rms.dao.tdd.FunctionInterfaceRelationDao;
import com.rms.dao.tdd.InterfaceDao;
import com.rms.dao.tdd.InterfaceParamDao;
import com.rms.dao.tdd.InterfaceParamRelationDao;
import com.rms.entity.tdd.RmsFunction;
import com.rms.entity.tdd.RmsFunctionInterfaceRelation;
import com.rms.entity.tdd.RmsFunctionInterfaceRelationId;
import com.rms.entity.tdd.RmsInterface;
import com.rms.entity.tdd.RmsInterfaceCategory;
import com.rms.entity.tdd.RmsInterfaceCategoryRelation;
import com.rms.entity.tdd.RmsInterfaceCategoryRelationId;
import com.rms.entity.tdd.RmsInterfaceParam;
import com.rms.modules.orm.Page;
import com.rms.modules.utils.ConvertUtil;
import com.rms.tddenum.TddEnum;
import com.rms.utils.POIUtils;

@Service
@Transactional (
    rollbackFor = Exception.class)
public class FunctionManager {

	@Autowired
	private FunctionDao	                 functionDao;

	@Autowired
	private FunctionInterfaceRelationDao	functionInterfaceRelationDao;

	@Autowired
	private InterfaceDao	             interfaceDao;

	@Autowired
	private InterfaceParamDao	         interfaceParamDao;

	@Autowired
	private InterfaceParamRelationDao	 interfaceParamRelationDao;

	@Autowired
	private DictManager	                 dictManager;

	@Transactional (
	    readOnly = true)
	public RmsFunction getFunctionById (Integer funcId) {

		return functionDao.get (funcId);
	}

	@Transactional (
		    readOnly = true)
		public List<RmsFunction> getAllFuncNoPage (String orderByProperty, boolean isAsc) {

			if (StringUtils.isNotEmpty(orderByProperty)) {
				return functionDao.getAll (orderByProperty, isAsc);
			}
			else {
				
				return functionDao.getAll ();
			}
		}
	
	/**
	 * 查询出功能对应的接口
	 * 
	 * @param iCateId
	 * @param isSelect
	 * @return
	 */
	@Transactional (
	    readOnly = true)
	public List<RmsInterface> getInterfaceListByFuncId (Integer funcId, TddEnum isSelect) {

		List<RmsInterface> listResult = new ArrayList<RmsInterface> ();

		if (funcId != null) {

			if (TddEnum.interfaceSelect.equals (isSelect)) {
				listResult = interfaceDao.findInterfaceWithFuncId (funcId);
			}
			else {

				if (TddEnum.interfaceUnSelect.equals (isSelect)) {

					listResult = interfaceDao.findInterfaceWithoutFuncId (funcId);
				}
			}
		}
		else {

			if (TddEnum.interfaceUnSelect.equals (isSelect)) {

				listResult = interfaceDao.getAll ("IName",true);
			}

		}

		return listResult;
	}

	@Transactional (
	    readOnly = true)
	public Page<RmsFunction> getFunctionPage (Map<String, Object> queryMap, Page<RmsFunction> page) {

		Criterion [] criterions = functionDao.getQueryCriterions (queryMap);

		if (criterions != null) {

			page = functionDao.findPage (page, criterions);
		}
		else {
			page = functionDao.getAll (page);
		}

		return page;
	}

	@Transactional (
	    readOnly = true)
	public List<RmsFunction> getFunctionNoPage (Map<String, Object> queryMap) {

		Criterion [] criterions = functionDao.getQueryCriterions (queryMap);

		List<RmsFunction> lstResult = new ArrayList<RmsFunction> ();
		if (criterions != null) {
			lstResult = functionDao.find (criterions);
		}
		else {
			lstResult = functionDao.getAll ();
		}

		return lstResult;
	}

	@Transactional (
	    readOnly = true)
	public SXSSFWorkbook exportFunctionExcel (Map<String, Object> queryMap) {

		Map<String, String> typeDictMap = dictManager.getDictDetailNamesValueMap ("param_type");
		Map<String, String> requiredDictMap = dictManager.getDictDetailNamesValueMap ("param_is_required");
		Map<String, String> titleMap = new HashMap<String, String> ();
		titleMap.put ("funcName", "功能名称");
		titleMap.put ("funcNameZh", "功能中文名称");
		titleMap.put ("funcDesc", "功能说明");
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

		List<RmsFunction> lstResult = this.getFunctionNoPage (queryMap);
		SXSSFWorkbook workBook = new SXSSFWorkbook ();

		for (RmsFunction rmsFunction : lstResult) {
			Set<RmsInterface> setInterface = rmsFunction.getRmsInterfaceSet ();
			Iterator<RmsInterface> itInterface = setInterface.iterator ();

			Sheet sheet = workBook.createSheet (rmsFunction.getFuncNameZh ());

			sheet.setColumnWidth (0, 20 * 256);
			sheet.setColumnWidth (1, 50 * 256);
			sheet.setColumnWidth (2, 20 * 256);
			sheet.setColumnWidth (3, 50 * 256);

			Row rowFunc = sheet.createRow (0);
			Cell cellNameTitleFunc = rowFunc.createCell (0);
			cellNameTitleFunc.setCellStyle (POIUtils.getSXTitleStyle (workBook));
			cellNameTitleFunc.setCellValue (new HSSFRichTextString (titleMap.get ("funcName")));

			Cell cellNameValueFunc = rowFunc.createCell (1);
			cellNameValueFunc.setCellStyle (POIUtils.getSXValueStyle (workBook));
			cellNameValueFunc.setCellValue (new HSSFRichTextString (rmsFunction.getFuncName ()));

			Cell cellNameZhTitleFunc = rowFunc.createCell (2);
			cellNameZhTitleFunc.setCellStyle (POIUtils.getSXTitleStyle (workBook));
			cellNameZhTitleFunc.setCellValue (new HSSFRichTextString (titleMap.get ("funcNameZh")));

			Cell cellNameZhValueFunc = rowFunc.createCell (3);
			cellNameZhValueFunc.setCellStyle (POIUtils.getSXValueStyle (workBook));
			cellNameZhValueFunc.setCellValue (new HSSFRichTextString (rmsFunction.getFuncNameZh ()));
			
			rowFunc = sheet.createRow (1);
			rowFunc.setHeightInPoints (5 * 20);
			Cell cellDescTitleFunc = rowFunc.createCell (0);
			cellDescTitleFunc.setCellValue (new HSSFRichTextString (titleMap.get ("funcDesc")));
			cellDescTitleFunc.setCellStyle (POIUtils.getSXTitleStyle (workBook));
			Cell cellDescValueFunc = rowFunc.createCell (1);
			rowFunc.createCell (2);
			rowFunc.getCell (2).setCellStyle (POIUtils.getSXValueStyle (workBook));
			rowFunc.createCell (3);
			rowFunc.getCell (3).setCellStyle (POIUtils.getSXValueStyle (workBook));
			sheet.addMergedRegion (new CellRangeAddress (1, 1, 1, 3));
			cellDescValueFunc.setCellValue (new HSSFRichTextString (rmsFunction.getFuncDesc ()));
			cellDescValueFunc.setCellStyle (POIUtils.getSXValueStyle (workBook));
			
			Integer index = 3;
			Integer rowIndex = 0;
			while (itInterface.hasNext ()) {
				RmsInterface valueInterface = itInterface.next ();

				Row row = sheet.createRow (index + rowIndex);

				// 接口表格第一行 接口名称,接口中文名称
				Cell cellNameTitle = row.createCell (0);
				cellNameTitle.setCellStyle (POIUtils.getSXTitleStyle (workBook));
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

					// 参数表头
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

					// 参数说明

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
							String strRequired = StringUtils.isEmpty (ipRequired) ? requiredDictMap.get (0) : requiredDictMap
							        .get (ipRequired);
							cellParamDetailTitle2.setCellValue (new HSSFRichTextString (strRequired));
							cellParamDetailTitle2.setCellStyle (POIUtils.getSXValueStyle (workBook));

							Cell cellParamDetailValue2 = row.createCell (3);
							String ipType = rmsInterfaceParam.getIpType ();
							String strType = StringUtils.isEmpty (ipType) ? typeDictMap.get (0) : typeDictMap.get (ipType);
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

				index ++;
			}

		}

		return workBook;
	}

	/**
	 * 新增功能和功能接口
	 * 
	 * @param rmsInterfaceCategory
	 * @param interfaceIds
	 * @throws Exception
	 */
	public void savaFunctionInterface (RmsFunction rmsFunction, List<String> interfaceIds) throws Exception {

		if (ConvertUtil.isNotNull (interfaceIds) && rmsFunction != null) {

			Integer funcId = rmsFunction.getFuncId ();

			if (funcId != null) {

				functionInterfaceRelationDao.deleteRelationByFuncId (funcId);
			}

			RmsFunction entityAfterSave = functionDao.merge (rmsFunction);
			for (String interfaceId : interfaceIds) {

				RmsFunctionInterfaceRelation rmsFunctionInterfaceRelation = new RmsFunctionInterfaceRelation ();
				RmsFunctionInterfaceRelationId rmsFunctionInterfaceRelationId = new RmsFunctionInterfaceRelationId ();
				rmsFunctionInterfaceRelationId.setFuncId (entityAfterSave.getFuncId ());
				rmsFunctionInterfaceRelationId.setIId (ConvertUtil.convertInt (interfaceId));
				rmsFunctionInterfaceRelation.setId (rmsFunctionInterfaceRelationId);

				functionInterfaceRelationDao.save (rmsFunctionInterfaceRelation);
			}
		}
	}

	/**
	 * 删除功能和功能下的接口
	 * 
	 * @param rmsInterfaceCategory
	 * @throws Exception
	 */
	public void deleteFunctionInterface (RmsFunction rmsFunction) throws Exception {

		functionInterfaceRelationDao.deleteRelationByFuncId (rmsFunction.getFuncId ());
		functionDao.delete (rmsFunction);
	}
}
