/**
 * 
 */
package com.rms.dao.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kunrey
 *
 */
public class RegisteredConverter implements Converter {
	
	private static Log _log = LogFactory.getLog(RegisteredConverter.class);
	
	private final String PATTERN_DATE = "yyyy-MM-dd" ;
	
	private final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss" ;
	/**
	 * 
	 * @describe 
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 * @param type
	 * @param value
	 * @return
	 * @author chenxb
	 * @date 2010-3-12
	 */
	public Object convert(Class type, Object value) {
		if(type==null||value==null) return value ;
        if (value instanceof java.util.Date) return value ; //鑻ヤ负姝ｇ‘绫诲瀷涓嶈浆鎹�
		DateFormat dateFormat = null ;
		String ret = value.toString() ;
		if(ret.length()==PATTERN_DATE.length()){
			dateFormat = new SimpleDateFormat(PATTERN_DATE) ;
			try {
				return dateFormat.parse(ret) ;
			} catch (ParseException e) {
				e.printStackTrace();
				_log.error(e) ;
			}
		}else if(ret.length()==PATTERN_DATETIME.length()){
			dateFormat = new SimpleDateFormat(PATTERN_DATETIME) ;
			try {
				return dateFormat.parse(ret) ;
			} catch (ParseException e) {
				e.printStackTrace();
				_log.error(e) ;
			}
		}
		return value ;
	}

}
