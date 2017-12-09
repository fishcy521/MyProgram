/**
 * 
 */
package com.rms.utils;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 *
 */
public class ApplicationProperties {
	
	private static ApplicationProperties _instance = new ApplicationProperties();
	
	private Properties properties = new Properties();
	
	public static ApplicationProperties instance(){
		return _instance;
	}
	
	private ApplicationProperties() {
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			URL url = classLoader.getResource("application.properties");
			if (url != null) {
				InputStream is = url.openStream();
				properties.load(is);
				is.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key){
		return _instance.properties.getProperty(key);
	}
}
