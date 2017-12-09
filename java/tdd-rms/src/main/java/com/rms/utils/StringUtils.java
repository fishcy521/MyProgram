package com.rms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 */
public class StringUtils {

	/**
	 * 判断字符串是否仅为字母
	 * @param str
	 * @return
	 */
	public static boolean isAlphabet(String str) {
		Pattern p = Pattern.compile("[a-zA-Z]*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 判断字符串是否包含字母
	 * @param str
	 * @return
	 */
	public static boolean isContainAlphabet(String str) {
		Pattern p = Pattern.compile("[a-zA-Z]*");
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.isAlphabet("aFcf333Pcc"));
	}
}
