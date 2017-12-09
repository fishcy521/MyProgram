package com.rms.utils;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.rms.commons.global.RmsGlobal;

public class RmsUtils {

	public static String getText_CN(String key) {

		Locale locale = Locale.CHINA;
		String message = LocalizedTextUtil.findDefaultText(key, locale);
		return message;
	}

	public static String get(String key) {

		Locale locale = Locale.CHINA;
		String message = LocalizedTextUtil.findDefaultText(key, locale);
		return message;
	}

	public static String get(String key, String defaultVal) {

		Locale locale = Locale.CHINA;
		String message = LocalizedTextUtil.findDefaultText(key, locale);
		if (message == null || "".equals(message)) {
			return defaultVal;
		}
		return message;
	}

	public static String getDBType() {

		return "oracle";
	}

	/**
	 * 如果字串中str含有host和suffix那么去掉
	 * 
	 * @return
	 */
	public static String doWithUrl(String str, String host, String suffix) {

		str = StringUtils.contains(str, host) ? StringUtils.replace(str, host, "") : str;
		str = StringUtils.contains(str, suffix) ? StringUtils.replace(str, suffix, "") : str;
		return str;
	}
}
