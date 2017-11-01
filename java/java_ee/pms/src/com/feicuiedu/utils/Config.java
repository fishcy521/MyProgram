package com.feicuiedu.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/27.
 */
public class Config {
	private final static String CONFIG_FILE = "config.properties";
	private static Properties prop = new Properties();
	
	public static String getValue(String key) {
		try {
			prop.load(new Config().getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(Config.getValue("mysql.username"));
	}
}
