package com.rms.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import com.rms.commons.global.RmsGlobal;

/**
 * Jsoup常用工具类
 */
public final class JsoupUtil extends ConvertUtils {

	/**
	 * 使用Post方法提交数据值url
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String getJsonStrByPost(String url, Map<String, String> paramMap) {

		System.out.println("***********************post method 调用接口begin*********************");
		System.out.println("interface url :" + url);
		String json = null;
		try {

			if (paramMap == null) {
				paramMap = new HashMap<String, String>();
			}
			// paramMap.put("USER_NAME", php_user_name);
			// paramMap.put("PASSWORD", Md5Encrypt.md5(php_user_passwd));
			logMap(paramMap);
			json = Jsoup.connect(url).ignoreContentType(true).timeout(RmsGlobal.JSOUP_TIME_OUT).data(paramMap).method(Method.POST)
			        .execute().body();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("return json is :" + json);
		System.out.println("***********************post method 调用接口end*********************");
		return json;
	}

	/**
	 * 使用Get方法提交数据值url
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String getJsonStrByGet(String url, Map<String, String> paramMap) {

		System.out.println("***********************get method 调用接口begin*********************");
		System.out.println("php interface url :" + url);
		String json = null;
		try {

			if (paramMap == null) {
				paramMap = new HashMap<String, String>();
			}
			// paramMap.put("USER_NAME", php_user_name);
			// paramMap.put("PASSWORD", Md5Encrypt.md5(php_user_passwd));
			logMap(paramMap);
			json = Jsoup.connect(url).ignoreContentType(true).timeout(RmsGlobal.JSOUP_TIME_OUT).data(paramMap).method(Method.GET).execute()
			        .body();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("return json is :" + json);
		System.out.println("***********************get method 调用接口end*********************");
		return json;
	}

	/**
	 * 调用php 使用Post方法提交数据值url
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String getJsonStrByPostFromPhp(String url, Map<String, String> paramMap) {

		System.out.println("***********************php post method 调用接口begin*********************");
		System.out.println("php interface url :" + url);
		String json = null;
		try {

			if (paramMap == null) {
				paramMap = new HashMap<String, String>();
			}
			paramMap.put("USER_NAME", RmsGlobal.PHP_INTERFACE_USERNAME);
			paramMap.put("PASSWORD", Md5Encrypt.md5(RmsGlobal.PHP_INTERFACE_PASSWORD));
			logMap(paramMap);
			json = Jsoup.connect(url).ignoreContentType(true).timeout(RmsGlobal.JSOUP_TIME_OUT).data(paramMap).method(Method.POST)
			        .execute().body();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("php return json is :" + json);
		System.out.println("***********************php post method 调用接口end*********************");
		return json;
	}

	/**
	 * 调用php 使用Get方法提交数据值url
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String getJsonStrByGetFromPhp(String url, Map<String, String> paramMap) {

		System.out.println("***********************php get method 调用接口begin*********************");
		System.out.println("php interface url :" + url);
		String json = null;
		try {

			if (paramMap == null) {
				paramMap = new HashMap<String, String>();
			}
			paramMap.put("USER_NAME", RmsGlobal.PHP_INTERFACE_USERNAME);
			paramMap.put("PASSWORD", Md5Encrypt.md5(RmsGlobal.PHP_INTERFACE_PASSWORD));
			logMap(paramMap);
			json = Jsoup.connect(url).ignoreContentType(true).timeout(RmsGlobal.JSOUP_TIME_OUT).data(paramMap).method(Method.GET).execute()
			        .body();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("php return json is :" + json);
		System.out.println("***********************php get method 调用接口end*********************");
		return json;
	}

	public static String loginPhpByPost(String url, Map<String, String> paramMap) {

		System.out.println("***********************post method 调用接口begin*********************");
		System.out.println("interface url :" + url);
		String json = null;
		try {

			if (paramMap == null) {
				paramMap = new HashMap<String, String>();
			}
			// paramMap.put("USER_NAME", php_user_name);
			// paramMap.put("PASSWORD", Md5Encrypt.md5(php_user_passwd));
			logMap(paramMap);
			json = Jsoup.connect(url).ignoreContentType(true).timeout(RmsGlobal.JSOUP_TIME_OUT).data(paramMap).method(Method.POST)
			        .execute().body();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("return json is :" + json);
		System.out.println("***********************post method 调用接口end*********************");
		return json;
	}

	private static void logMap(Map<String, String> paramMap) {

		if (paramMap != null && !paramMap.isEmpty()) {
			StringBuilder sb = new StringBuilder("post param[");
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				// System.out.print();
				sb.append(entry.getKey() + "=" + entry.getValue() + ",");
			}

			if (StringUtils.endsWith(sb.toString(), ",")) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			System.out.println(sb);
		}
		else {
			System.out.println("no param need to post");
		}

	}

}
