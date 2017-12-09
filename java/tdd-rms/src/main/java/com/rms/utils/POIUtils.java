package com.rms.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class POIUtils {

    public static HSSFCellStyle getTitleStyle(HSSFWorkbook workBook){
	HSSFFont font = workBook.createFont();
	font.setFontName("宋体");
	font.setFontHeightInPoints((short)11);
	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	
	HSSFCellStyle cellStyle = workBook.createCellStyle();
	cellStyle.setFont(font);
	cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setWrapText(true);
	cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	return cellStyle;
    }
    
    public static HSSFCellStyle getValueStyle(HSSFWorkbook workBook){
	HSSFFont font = workBook.createFont();
	font.setFontName("宋体");
	font.setFontHeightInPoints((short)10);
	//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	
	HSSFCellStyle cellStyle = workBook.createCellStyle();
	cellStyle.setFont(font);
	cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setWrapText(true);
	cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	
	return cellStyle;
    }
    
    public static CellStyle getSXTitleStyle(SXSSFWorkbook workBook){
	Font font = workBook.createFont();
	font.setFontName("宋体");
	font.setFontHeightInPoints((short)11);
	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	
	CellStyle cellStyle = workBook.createCellStyle();
	cellStyle.setFont(font);
	cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setWrapText(true);
	cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	return cellStyle;
    }
    
    public static CellStyle getSXValueStyle(SXSSFWorkbook workBook){
	Font font = workBook.createFont();
	font.setFontName("宋体");
	font.setFontHeightInPoints((short)10);
	//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	
	CellStyle cellStyle = workBook.createCellStyle();
	cellStyle.setFont(font);
	cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellStyle.setWrapText(true);
	cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	
	return cellStyle;
    }
}
