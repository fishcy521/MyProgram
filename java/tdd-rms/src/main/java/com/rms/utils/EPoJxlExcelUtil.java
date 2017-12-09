/**
 * 
 */
package com.rms.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rms.service.ServiceException;

import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 
 */
public class EPoJxlExcelUtil {
	public static final Integer DATA_LINE_BEGIN_NUMBER_PARAM_NAME = -1;
	public static final Integer SHEET_NUMBER_PARAM_NAME = -2;

	/**
	 * 使用JXL读取EXCEL
	 * 
	 * @param file
	 * @param specifiedMap
	 * @return
	 */
	public static Map<String, Object> readExcelByJxl(File file,
			Map<Integer, Object> specifiedMap) {
		List<Object> resultList = new ArrayList<Object>();// 保存读取后返回的信息
		List<String> errorList = new ArrayList<String>();// 保存错误信息
		Map<String, Object> resultMap = new HashMap<String, Object>();// 将错误信息和结果信息保存在Map中返回
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file);
			Integer sheetNum = (Integer) specifiedMap
					.get(SHEET_NUMBER_PARAM_NAME);// 通过specifiedMap得到sheet的序号
			Sheet sheet = workbook.getSheet(sheetNum.intValue());
			int lastRowNum = sheet.getRows();
			int lastColumnNum = sheet.getColumns();

			Integer detailLineBeginNum = (Integer) specifiedMap
					.get(DATA_LINE_BEGIN_NUMBER_PARAM_NAME);// 行数据起始行
			for (int rowIndex = 0; rowIndex < lastRowNum; rowIndex++) {
				if (rowIndex < detailLineBeginNum)
					continue;
				// 从明细行开始取值
				boolean checkRowUseful = false;
				// 判断该行是否需要存入数据库，如果全部为空，则不处理
				for (int columnIndex = 0; columnIndex < lastColumnNum; columnIndex++) {
					String cellValStr = toTrim(sheet.getCell(columnIndex,
							rowIndex).getContents());// 处理空格，add by Jimmy Xing
					if (cellValStr != null
							&& !("").equalsIgnoreCase(cellValStr)) {
						checkRowUseful = true;
						break;
					}
				}
				if (checkRowUseful) {// 如果该行有数据
					Map<String, String> rowMap = new HashMap<String, String>();// 封装当前行数据
					for (int columnIndex = 0; columnIndex < lastColumnNum; columnIndex++) {
						String colName = (String) specifiedMap.get(new Integer(
								columnIndex));
						Cell cell = sheet.getCell(columnIndex, rowIndex);
						if (cell.getType() == CellType.NUMBER) {
							NumberCell nc = (NumberCell) cell;
							rowMap.put(colName, "" + nc.getValue());
						} else {
							String cellValStr = sheet.getCell(columnIndex,
									rowIndex).getContents();
							rowMap.put(colName, cellValStr);
						}
					}
					resultList.add(rowMap);
				}
			}
			resultMap.put("errorList", errorList);
			resultMap.put("resultList", resultList);
		} catch (BiffException e) {
			throw new ServiceException(e);
		} catch (IOException e) {
			throw new ServiceException(e);
		} finally {
			if (workbook != null)
				workbook.close();
		}
		return resultMap;
	}

	/**
	 * 用jxl取得sheet的个数
	 * 
	 * @param file
	 * @return
	 */
	public static int getSheetCount(File file) {
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			throw new ServiceException(e);
		} catch (IOException e) {
			throw new ServiceException(e);
		} finally {
			workbook.close();
		}
		return workbook.getNumberOfSheets();
	}

	public static String toTrim(Object obj) {
		String str = "";
		if (obj != null) {
			str = obj.toString().trim(); // 如果不是null,则去掉空格;
		}
		return str; // 如果为null，则返回一个"";
	}
}
