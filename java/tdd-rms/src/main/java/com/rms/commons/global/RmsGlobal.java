package com.rms.commons.global;

import com.rms.utils.ApplicationProperties;

public class RmsGlobal {

	// 其他公共
	public final static int MY_BUFFER_SIZE = 5 * 1024;// 缓冲区 4k

	public final static String UPLOAD_DIR = "//UploadFiles//";

	// 产品类型属性名称显示的前缀和后缀 html样式
	public final static String PREFIX_PMSITEM_ATTR = "<b><font color=blue>";

	public final static String SUFFIX_PMSITEM_ATTR = "</font></b>";

	public final static boolean RELOAD_QUERY_XML_SQLSTRING = false;

	public final static Integer JSOUP_TIME_OUT = 30000;
	
	public final static String PHP_INTERFACE_USERNAME = "TDuoDuoDataApiName";
	
	public final static String PHP_INTERFACE_PASSWORD = "TDuoDuoDataApi";
	
	public final static String CONTENT_SPLIT = "####";
	
	public final static String DEFAULT_PAGE_SIZE = ApplicationProperties.get("default_page_size");
	
	public final static String DEFAULT_PAGE_SIZE_LIST = ApplicationProperties.get("default_page_size_list");
	
	public final static String NULL_KEY = ApplicationProperties.get("null_key");
	
	public final static String JSON_ERROR = ApplicationProperties.get("json_error");
	
	public final static String INTERFACE_HOST = ApplicationProperties.get("interface_host");
	
	public final static String INTERFACE_SUFFIX = ApplicationProperties.get("interface_suffix");
	
	public final static String BOUNDARY = ApplicationProperties.get("boundary");
	
	public final static String FILE_UPLOAD_URL = ApplicationProperties.get("file_upload_url");
	public final static String IMAGE_UPLOAD_URL = ApplicationProperties.get("image_upload_url");
	public final static String VIDEO_UPLOAD_URL = ApplicationProperties.get("video_upload_url");
	
	
	
}
