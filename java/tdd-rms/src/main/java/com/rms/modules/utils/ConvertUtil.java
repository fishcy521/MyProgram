package com.rms.modules.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public final class ConvertUtil {

	private static Calendar	calendar	= null;
	
	// 返回传入日期加制定天数后的日期
	public static String addDate (String str, int days) {

		String date1 = "";
		try {
			Calendar rightNow = Calendar.getInstance ();

			DateFormat df = DateFormat.getDateInstance ();
			try {
				rightNow.setTime (df.parse (str));
			}
			catch (Exception e) {
			}
			rightNow.add (Calendar.DATE, days);

			String year = "" + rightNow.get (Calendar.YEAR);
			String month = ((rightNow.get (Calendar.MONTH) + 1) > 9) ? ("" + (rightNow.get (Calendar.MONTH) + 1)) : ("0" + (rightNow
			        .get (Calendar.MONTH) + 1));
			String day = (rightNow.get (Calendar.DAY_OF_MONTH) > 9) ? ("" + rightNow.get (Calendar.DAY_OF_MONTH)) : ("0" + rightNow
			        .get (Calendar.DAY_OF_MONTH));

			date1 = (year + "-" + month + "-" + day);

		}
		catch (Exception e) {
		}
		return date1;
	}

	/**
	 * NULL 返回 空字符串
	 * 
	 * @param inStr
	 * @return
	 */
	public static String convertNull (String inStr) {

		if (inStr == null) {
			return "";
		}
		else {
			return inStr.trim ();
		}
	}

	// NULL 返回 空字符串
	public static String convertNull (String inStr, String defaultStr) {

		if (inStr == null || "".equals (inStr)) {
			return defaultStr;
		}
		else {
			return inStr.trim ();
		}
	}

	public static boolean isNull (List tmpList) {

		if (tmpList != null && tmpList.size () > 0) {
			return false;
		}
		return true;
	}

	public static boolean isNotNull (List tmpList) {

		return !isNull (tmpList);
	}

	public static boolean isNull (Map tmpList) {

		if (tmpList != null && tmpList.size () > 0) {
			return false;
		}
		return true;
	}

	public static boolean isNotNull (Map tmpList) {

		return !isNull (tmpList);
	}

	// 将8859的字符转化成gb2312
	public static String convertGBK (String strCode) {

		try {
			if ((strCode == null) || (strCode.equals (""))) {
				return "";
			}
			strCode = strCode.trim ();
			strCode = new String (strCode.getBytes ("ISO-8859-1"), "GBK");
		}
		catch (IOException ioe) {
			System.out.println (ioe);
		}
		return strCode;
	}

	public static String convertUTF8 (String strCode) {

		try {
			if ((strCode == null) || (strCode.equals (""))) {
				return "";
			}
			strCode = strCode.trim ();
			strCode = new String (strCode.getBytes ("ISO-8859-1"), "UTF-8");
		}
		catch (IOException ioe) {
			System.out.println (ioe);
		}
		return strCode;
	}

	public static String [] convertUTF8 (String [] values) {

		if (values == null || values.length == 0) {
			return values;
		}
		try {
			for (int k = 0; k < values.length; k ++) {
				String strCode = values[k];
				if ((strCode == null) || strCode.equals ("")) {
					return values;
				}
				strCode = strCode.trim ();
				values[k] = new String (strCode.getBytes ("ISO-8859-1"), "UTF-8");
			}
		}
		catch (IOException ioe) {
			System.out.println (ioe);
		}
		return values;
	}

	// 将gb2312的字符转化成8859
	public static String convertISO (String strCode) {

		try {
			if ((strCode == null) || (strCode.equals (""))) {
				return "";
			}
			// strCode = strCode;
			strCode = new String (strCode.getBytes ("GBK"), "ISO-8859-1");
		}
		catch (IOException ioe) {
			System.out.println (ioe);
		}
		return strCode;
	}

	// 处理建委字典 5位提取最后2位
	public static String getLastTwoChar (String inStr) {

		if (inStr == null)
			return "";
		else {
			int lens = inStr.length ();
			if (lens >= 2) {
				inStr = inStr.substring (lens - 2, lens);
			}
		}
		return inStr;
	}

	// java.util.Date 转为java.sql.Date
	public static java.sql.Date dateToDate (Date date1) {

		if (date1 == null)
			return null;
		else
			return new java.sql.Date (date1.getTime ());
	}

	// 返回整形转为字符串
	public static String convertStr (Integer inStr) {

		return String.valueOf (inStr);
	}

	// 返回整形转为字符串
	public static String convertStr (Long inStr) {

		if (inStr == null)
			return "";
		return String.valueOf (inStr.longValue ());
	}

	// 返回整形转为字符串
	public static String convertStr (Double inStr) {

		if (inStr == null)
			return "";
		return String.valueOf (inStr);
	}

	// 返回整形转为字符串
	public static String convertStr (BigDecimal inStr) {

		if (inStr == null)
			return "";
		else
			return String.valueOf (inStr);
	}

	// 将传入的字符串转化成整型(若字符串非法,取默认值)
	public static int convertInt (String strInt, int defaultInt) {

		int outInt = defaultInt;
		if ((strInt == null) || (strInt.equals (""))) {
			outInt = defaultInt;
		}
		else {
			try {
				outInt = Integer.parseInt (strInt);
			}
			catch (Exception e) {
				outInt = defaultInt;
			}
		}
		if (outInt <= 0) {
			outInt = defaultInt;
		}
		return outInt;
	}

	public static int convertInt (Integer intObj, int defaultInt) {

		int outInt = defaultInt;
		if (intObj == null) {
			outInt = defaultInt;
		}
		else {
			try {
				outInt = intObj.intValue ();
			}
			catch (Exception e) {
				outInt = defaultInt;
			}
		}
		if (outInt <= 0) {
			outInt = defaultInt;
		}
		return outInt;
	}

	// 将传入的字符串转化成整型(若字符串非法,取默认值)
	public static Long convertLong (String strInt, long defaultInt) {

		Long outInt = new Long (defaultInt);
		if ((strInt != null) && (!strInt.equals (""))) {
			try {
				outInt = new Long (Long.parseLong (strInt));
			}
			catch (Exception e) {
				outInt = new Long (defaultInt);
			}
		}
		if (outInt <= 0) {
			outInt = new Long (defaultInt);
		}
		return outInt;
	}

	public static Long convertLong (String strInt) {

		Long outInt = null;
		if ((strInt != null) && (!strInt.equals (""))) {
			try {
				outInt = new Long (Long.parseLong (strInt));
			}
			catch (Exception e) {
			}
		}
		return outInt;
	}

	public static Long convertLong (Integer intObj) {

		Long outInt = null;
		if (intObj != null) {
			try {
				outInt = intObj.longValue ();
			}
			catch (Exception e) {
			}
		}
		return outInt;
	}

	// 将传入的字符串转化成整型(若字符串非法,取默认值)
	public static Double convertDouble (String strInt, double defaultInt) {

		Double outInt = new Double (defaultInt);
		if ((strInt != null) && (!strInt.equals (""))) {
			try {
				outInt = new Double (Double.parseDouble (strInt));
			}
			catch (Exception e) {
				outInt = new Double (defaultInt);
			}
		}
		if (outInt <= 0) {
			outInt = new Double (defaultInt);
		}
		return outInt;
	}

	public static Double convertDouble (String strInt) {

		Double outInt = null;
		if ((strInt != null) && (!strInt.equals (""))) {
			try {
				outInt = new Double (Double.parseDouble (strInt));
			}
			catch (Exception e) {
			}
		}
		return outInt;
	}

	// 将字符串转为大数据类型.
	public static BigDecimal convertBigDecimal (String strInt) {

		BigDecimal outBigDec = new BigDecimal ("0.00");
		if (convertNull (strInt).equals (""))
			return outBigDec;
		else {
			try {
				outBigDec = new BigDecimal (strInt);
			}
			catch (Exception e) {
			}
		}
		return outBigDec;
	}

	public static String toDateString (Date date) {

		if (null == date) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		return simpleDateFormat.format (date);
	}

	public static String toTimeString (Date date) {

		if (null == date) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("HH:mm:ss");
		return simpleDateFormat.format (date);
	}

	public static String toDateTimeString (Date date) {

		if (null == date) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format (date);
	}

	public static String toDateTimeString (String datetimeStr) {

		if (null == datetimeStr) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpleDateFormat.parse (datetimeStr);
			return simpleDateFormat.format (date);
		}
		catch (ParseException pe) {
			return "";
		}
	}

	public static Date dateStrToDate (String dateStr) {

		if (null == dateStr) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		try {
			Date date = simpleDateFormat.parse (dateStr);
			return date;
		}
		catch (ParseException pe) {
			return null;
		}
	}

	public static Date datetimeStrToDate (String datetimeStr) {

		if (null == datetimeStr) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpleDateFormat.parse (datetimeStr);
			return date;
		}
		catch (ParseException pe) {
			return null;
		}
	}

	public static String datetimeStrToDateNoBlank (Date date) {
		
		if (null == date) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyyMMddHHmmss");
		return simpleDateFormat.format (date);
	}
	
	public static Date getDate () {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		calendar = Calendar.getInstance ();
		try {
			Date date = simpleDateFormat.parse (simpleDateFormat.format (calendar.getTime ()));
			return date;
		}
		catch (ParseException pe) {
			return null;
		}
	}

	public static String getMonthAndDayStr (String dateStr) {

		Date date = dateStrToDate (dateStr);
		if (date == null) {
			return "01-01";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("MM-dd");
		String strRet = simpleDateFormat.format (date);
		return strRet;
	}

	public static Date getDateTime () {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		calendar = Calendar.getInstance ();
		try {
			Date date = simpleDateFormat.parse (simpleDateFormat.format (calendar.getTime ()));
			return date;
		}
		catch (ParseException pe) {
			return null;
		}
	}

	public static String getDateStr () {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		calendar = Calendar.getInstance ();
		return simpleDateFormat.format (calendar.getTime ());
	}

	public static String getDateTimeStr () {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		calendar = Calendar.getInstance ();
		return simpleDateFormat.format (calendar.getTime ());
	}

	// 在前面补零
	public static String addZero (String testStr, int strLength) {

		String str = ConvertUtil.convertNull (testStr).trim ();

		while (str.length () < strLength) {
			str = "0" + str;
		}
		return str;
	}

	public static String removeZero (String testStr) {

		String str = ConvertUtil.convertNull (testStr).trim ();
		if ("".equals (str)) {
			return "";
		}
		String tmpStr = str.substring (0, 1);
		while ("0".equals (tmpStr) && str.length () > 0) {
			str = str.substring (1, str.length ());
			tmpStr = str.substring (0, 1);
		}
		return str;
	}

	public static String decimalFormat (double testVal, int strLength) {

		return decimalFormat (String.valueOf (testVal), strLength);
	}

	public static String decimalFormat (String testVal, int strLength) {

		String strFormt = "0.";
		for (int i = 0; i < strLength; i ++) {
			strFormt += "0";
		}
		if (testVal == null || "".equals (testVal)) {
			return strFormt;
		}
		try {
			double dblVal = Double.valueOf (testVal);
			DecimalFormat df = new DecimalFormat (strFormt);
			strFormt = df.format (dblVal);
		}
		catch (Exception e) {
			System.out.println ("ConvertUtil[decimalFormat]:[" + testVal + "]转为实数出错！");
		}
		return strFormt;
	}

	public static String addZero (int testInt, int strLength) {

		String str = String.valueOf (testInt);
		while (str.length () < strLength) {
			str = "0" + str;
		}
		return str;
	}

	// 根据传入编号生成条形码,采用交叉二五码(ITF二五码)
	public static String itf25 (String code, String webContext) {

		String [][] barCode = { {"0", "0", "1", "1", "0"}, {"1", "0", "0", "0", "1"}, {"0", "1", "0", "0", "1"}, {"1", "1", "0", "0", "0"},
		        {"0", "0", "1", "0", "1"}, {"1", "0", "1", "0", "0"}, {"0", "1", "1", "0", "0"}, {"0", "0", "0", "1", "1"},
		        {"1", "0", "0", "1", "0"}, {"0", "1", "0", "1", "0"}};
		StringBuffer codeBuff = new StringBuffer ("<img src='" + webContext + "/images/start.gif' width='8' height='43'>");
		String str10 = "<img src='" + webContext + "/images/10.gif' width='2' height='43'>";
		String str20 = "<img src='" + webContext + "/images/20.gif' width='4' height='43'>";
		String str11 = "<img src='" + webContext + "/images/11.gif' width='2' height='43'>";
		String str21 = "<img src='" + webContext + "/images/21.gif' width='4' height='43'>";
		int len = code.length ();
		if (len % 2 == 1) {
			code = "0" + code;
		}
		else {
			len --;
		}
		int i = 0;
		while (i < len) {
			int one = 0;
			int two = 0;
			try {
				one = Integer.parseInt (code.substring (i, (i + 1)));
			}
			catch (Exception exp) {
				one = 0;
			}
			try {
				two = Integer.parseInt (code.substring ((i + 1), (i + 2)));
			}
			catch (Exception exp) {
				two = 0;
			}
			for (int m = 0; m < 5; m ++) {
				if (barCode[one][m].equals ("0")) {
					codeBuff.append (str11);
				}
				else {
					codeBuff.append (str21);
				}
				if (barCode[two][m].equals ("0")) {
					codeBuff.append (str10);
				}
				else {
					codeBuff.append (str20);
				}
			}
			i = i + 2;
		}
		codeBuff.append ("<img src='" + webContext + "/images/end.gif' width='8' height='43'>");
		return (codeBuff.toString ());
	}

	public static int getStringCount (String s) {

		int length = 0;
		for (int i = 0; i < s.length (); i ++) {
			int ascii = Character.codePointAt (s, i);
			if (ascii >= 0 && ascii <= 255)
				length ++;
			else
				length += 2;

		}
		return length;
	}

	public static void copy (File src, File dst, int bufferSize) {

		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				if (!dst.exists ()) {
					dst.createNewFile ();
				}
				in = new BufferedInputStream (new FileInputStream (src));
				out = new BufferedOutputStream (new FileOutputStream (dst));
				byte [] buffer = new byte [bufferSize];
				int len = -1;
				while ((len = in.read (buffer)) != -1) {
					out.write (buffer, 0, len);
				}
				out.flush ();
			}
			finally {
				if (null != in) {
					in.close ();
				}
				if (null != out) {
					out.close ();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace ();
		}
	}

	public static void copy (File src, OutputStream out, int bufferSize) {

		try {
			InputStream in = null;
			try {
				in = new BufferedInputStream (new FileInputStream (src));
				byte [] buffer = new byte [bufferSize];
				int len = -1;
				while ((len = in.read (buffer)) != -1) {
					out.write (buffer, 0, len);
				}
				out.flush ();
			}
			finally {
				if (null != in) {
					in.close ();
				}
				if (null != out) {
					out.close ();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace ();
		}
	}

	public static String convertUpperWord (String val) {

		if (!"".equals (val)) {
			while (val.indexOf ("_") != -1) {
				int m = val.indexOf ("_");
				if (val.length () >= m + 2) {
					String tmpVal = val.substring (m + 1, m + 2).toUpperCase ();
					val = val.substring (0, m) + tmpVal + val.substring (m + 2, val.length ());
				}
			}
		}
		return val;
	}

	public static String getListToStringArray (List valList, String separate1, String separate2) {

		String value = "";
		separate2 = ConvertUtil.convertNull (separate2);
		if (valList != null && valList.size () > 0) {
			for (Object obj : valList) {
				if (obj instanceof Long) {
					Long id = (Long) obj;
					if (id != null && id.longValue () > 0) {
						if ("".equals (value))
							value = "" + separate2 + id + separate2 + "";
						else
							value += separate1 + "" + separate2 + id + separate2 + "";
					}
				}
				else
					if (obj instanceof String) {
						String id = (String) obj;
						if (id != null && id.length () > 0) {
							if ("".equals (value))
								value = "" + separate2 + id + separate2 + "";
							else
								value += separate1 + "" + separate2 + id + separate2 + "";
						}
					}
			}
		}
		return value;
	}

	public static String getStringArray (String [] valList, String separate1, String separate2) {

		String value = "";
		separate2 = ConvertUtil.convertNull (separate2);
		if (valList != null && valList.length > 0) {
			for (String obj : valList) {
				String id = (String) obj;
				if (id != null && id.length () > 0) {
					if ("".equals (value))
						value = "" + separate2 + id + separate2 + "";
					else
						value += separate1 + "" + separate2 + id + separate2 + "";
				}
			}
		}
		return value;
	}

	public static List getStringToList (String val, String separate, Class type) {

		List retList = new ArrayList ();
		if (val != null && val.length () > 0) {
			String [] valArr = val.split (separate);
			if (valArr != null && valArr.length > 0) {
				for (String tmp : valArr) {
					if (type.getName ().toLowerCase ().equals ("java.lang.long")) {
						retList.add (ConvertUtil.convertLong (tmp, 0));
					}
					else {
						retList.add (tmp);
					}
				}
			}
		}
		return retList;
	}

	public static String [] getStringArray (Object objstr) {

		String [] ids = null;
		if (objstr instanceof String []) {
			ids = (String []) objstr;
		}
		else
			if (objstr instanceof String) {
				ids = new String [1];
				ids[0] = (String) objstr;
			}
			else {
				ids = new String [1];
				ids[0] = (String) objstr;
			}
		return ids;
	}

	public static String getStringObject (Object objstr) {

		String id = null;
		if (objstr instanceof String []) {
			id = ConvertUtil.getStringArray ((String []) objstr, ",", "");
		}
		else
			if (objstr instanceof String) {
				id = (String) objstr;
			}
			else {
				id = (String) objstr;
			}
		return id;
	}

	public static double toDouble (Double val, int count) {

		if (val == null)
			return 0;
		val = (new BigDecimal (val).setScale (count, BigDecimal.ROUND_HALF_UP)).doubleValue ();
		return val;
	}

	public static double toDouble (String vals, int count) {

		double val = ConvertUtil.convertDouble (vals, 0);
		if (val == 0)
			return 0;
		val = (new BigDecimal (val).setScale (count, BigDecimal.ROUND_HALF_UP)).doubleValue ();
		return val;
	}

	public static Double toDoubleObj (String vals, int count) {

		if (StringUtils.isEmpty (vals))
			return null;
		double val = ConvertUtil.convertDouble (vals, 0);
		if (val == 0)
			return 0D;
		val = (new BigDecimal (val).setScale (count, BigDecimal.ROUND_HALF_UP)).doubleValue ();
		return val;
	}

	public static long toLong (Long vals) {

		if (vals != null)
			return vals.longValue ();
		else
			return 0;
	}

	public static Date addMyDate (String str, int days) {

		try {
			DateFormat df = DateFormat.getDateInstance ();
			Calendar calendar = Calendar.getInstance ();
			calendar.setTime (df.parse (str));
			calendar.add (Calendar.DAY_OF_MONTH, days); // 加一天
			return calendar.getTime ();
			// SimpleDateFormat simpleDateFormat = new
			// SimpleDateFormat("yyyy-MM-dd");
			// Date date = simpleDateFormat.parse(dateStr);
		}
		catch (Exception e) {
		}
		return null;
	}

	// java.util.Date 转为java.sql.Date
	public static java.sql.Date dateToSqlDate (Date date1) {

		if (date1 == null)
			return null;
		else
			return new java.sql.Date (date1.getTime ());
	}

	public static int convertInt (String strInt) {

		int outInt = 0;
		if ((strInt == null) || (strInt.equals (""))) {

		}
		else {
			try {
				outInt = Integer.parseInt (strInt);
			}
			catch (Exception e) {
				outInt = 0;
			}
		}
		return outInt;
	}

	public static int convertInt (Integer intObj) {

		int outInt = 0;
		if (intObj == null) {

		}
		else {
			try {
				outInt = intObj.intValue ();
			}
			catch (Exception e) {
				outInt = 0;
			}
		}
		return outInt;
	}

	public static Long [] convertLongs (String strInt, String Delimiter, long defaultInt) {

		if ((strInt != null) && (!strInt.equals (""))) {
			String [] strIntArr = strInt.split (Delimiter);
			if (strIntArr != null) {
				Long [] outLongs = new Long [strIntArr.length];
				for (int k = 0; k < strIntArr.length; k ++) {
					outLongs[k] = convertLong (strIntArr[k], defaultInt);
				}
				return outLongs;
			}

		}
		return null;
	}

	public static String [] convertStrings (String strInt, String Delimiter, String defaultVal) {

		if ((strInt != null) && (!strInt.equals (""))) {
			String [] strIntArr = strInt.split (Delimiter);
			if (strIntArr != null) {
				String [] outLongs = new String [strIntArr.length];
				for (int k = 0; k < strIntArr.length; k ++) {
					outLongs[k] = convertNull (strIntArr[k], defaultVal);
				}
				return outLongs;
			}

		}
		return null;
	}

	public static Long [] convertLongs (String [] strIntArr, long defaultInt) {

		if ((strIntArr != null) && strIntArr.length > 0) {
			Long [] outLongs = new Long [strIntArr.length];
			for (int k = 0; k < strIntArr.length; k ++) {
				outLongs[k] = convertLong (strIntArr[k], defaultInt);
			}
			return outLongs;
		}
		return null;
	}

	/***************************************************************************************************************************************************************************************************
	 * 将字符串转化为数字
	 */
	public static double str2double (String str) {

		if (str == null || "".equals (str)) {
			str = 0 + "";
		}
		else {
			str = str.trim ();
		}
		double tmpd = 0;
		tmpd = Double.valueOf (str).doubleValue ();
		return tmpd;
	}

	public static boolean isNumeric (String str) {

		Pattern pattern = Pattern.compile ("[\\d.]+"); // 匹配一个马上的0-9和小数点
		return pattern.matcher (str).matches ();
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder (String sPath) {

		boolean flag = false;
		File file = new File (sPath);
		// 判断目录或文件是否存在
		if (!file.exists ()) { // 不存在返回 false
			return flag;
		}
		else {
			// 判断是否为文件
			if (file.isFile ()) { // 为文件时调用删除文件方法
				return deleteFile (sPath);
			}
			else { // 为目录时调用删除目录方法
				return deleteDirectory (sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile (String sPath) {

		boolean flag = false;
		File file = new File (sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile () && file.exists ()) {
			file.delete ();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory (String sPath) {

		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith (File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File (sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists () || !dirFile.isDirectory ()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File [] files = dirFile.listFiles ();
		for (int i = 0; i < files.length; i ++) {
			// 删除子文件
			if (files[i].isFile ()) {
				flag = deleteFile (files[i].getAbsolutePath ());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory (files[i].getAbsolutePath ());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete ()) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isMobileNO (String mobiles) {

		Pattern p = Pattern
		        .compile ("^((13[0-9])|(145)|(147)|(150)|(151)|(152)|(153)|(155)|(156)|(157)|(158)|(159)|(170)|(176)|(177)|(178)|(18[0-9]))\\d{8}$");

		Matcher m = p.matcher (mobiles);

		System.out.println (m.matches () + "---");

		return m.matches ();
	}

	public static String getIpAddr (HttpServletRequest request) {

		String ip = request.getHeader ("x-forwarded-for");

		if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {

			ip = request.getHeader ("Proxy-Client-IP");

		}

		if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {

			ip = request.getHeader ("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {

			ip = request.getRemoteAddr ();

		}

		return ip;

	}

	public static boolean isBuy () {

		Date dateBegin = ConvertUtil.datetimeStrToDate ("2015-04-30 18:00:00");

		Date dateEnd = ConvertUtil.datetimeStrToDate ("2015-05-04 00:00:01");

		Date dateCur = Calendar.getInstance ().getTime ();

		Boolean blnRes = (dateCur.before (dateBegin)) || (dateCur.after (dateEnd));
		return blnRes;

	}

	public static String getJSONFromPHPInterface (String json) {

		return null;
	}

	public static String getJSONFromJavaInterface (String json) {

		return null;
	}

	public static void main (String [] args) {

		/*Date dateBegin = ConvertUtil.datetimeStrToDate ("2015-04-30 18:00:00");

		Date dateEnd = ConvertUtil.datetimeStrToDate ("2015-05-04 00:00:00");

		Date dateCur = Calendar.getInstance ().getTime ();

		Calendar ca = Calendar.getInstance ();

		System.out.println ("111" + ca.getTimeInMillis () + "||1431154621");

		SimpleDateFormat df = new SimpleDateFormat ("yyyyMMddHHssmm");
		ca.setTimeInMillis (1431152436000l);
		System.out.println (Long.valueOf (df.format (ca.getTime ())));*/
		
		System.out.println(ConvertUtil.datetimeStrToDateNoBlank(Calendar.getInstance().getTime()));
	}
}
