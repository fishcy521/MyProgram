package com.rms.web.tdd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.rms.commons.global.RmsGlobal;
import com.rms.entity.tdd.RmsInterface;
import com.rms.modules.orm.Page;
import com.rms.modules.utils.web.struts2.Struts2Utils;
import com.rms.service.tdd.FunctionManager;
import com.rms.tddenum.TddEnum;
import com.rms.web.CrudActionSupport;

@Namespace ("/tdd")
// @Results({ @Result(name = InterfaceAction.RELOAD, location =
// "interface.action", type = "redirect") })
public class UploadAction extends CrudActionSupport<Map<String, Object>> {

	@Autowired
	private FunctionManager	    funcManger;

	// 页面数据集
	private Page<RmsInterface>	page	= new Page<RmsInterface>(10);

	// 页面参数集合
	private Map<String, Object>	pageMap	= new HashMap<String, Object>();

	private Map<String, Object>	entity;

	@Override
	public Map<String, Object> getModel() {

		// TODO Auto-generated method stub
		return entity;
	}

	private File	uploadFile;

	private String	uploadFileFileName;

	private String	uploadFileContentType;

	public String getUploadFileFileName() {

		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {

		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadFileContentType() {

		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {

		this.uploadFileContentType = uploadFileContentType;
	}

	@Override
	public String list() throws Exception {

		return SUCCESS;
	}

	public File getUploadFile() {

		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {

		this.uploadFile = uploadFile;
	}

	@Override
	public String input() throws Exception {

		return "input";
	}

	@Override
	public String save() throws Exception {

		return null;
	}

	@Override
	public String delete() throws Exception {

		return null;
	}

	public Map<String, Object> getEntity() {

		return entity;
	}

	public void setEntity(Map<String, Object> entity) {

		this.entity = entity;
	}

	@Override
	protected void prepareModel() throws Exception {

		entity = new HashMap<String, Object>();
	}

	public String gridData() throws Exception {

		return null;
	}

	public String viewJson() throws Exception {

		prepareModel();

		return null;
	}

	public String uploadFile() {

		String exception = "上传成功";
		Map<TddEnum, Object> jsonMap = new HashMap<TddEnum, Object>();
		Calendar ca = Calendar.getInstance();

		StringBuilder sb = new StringBuilder();

		if (uploadFile != null) {

			String fileFormName = this.getUploadFileFileName();
			// String newFileName = ConvertUtil.datetimeStrToDateNoBlank(ca.getTime())+File.separator+fileFormName;
			String newFileName = fileFormName;
			/*
			 * sb.append("--" + RmsGlobal.BOUNDARY + "\r\n"); sb.append("Content-Disposition: form-data; type=\"8\"\r\n");
			 */

			/**
			 * 上传文件的头
			 */
			sb.append("--" + RmsGlobal.BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + fileFormName + "\"; filename=\"" + newFileName + "\"" + "\r\n");
			sb.append("Content-Type: image/jpeg" + "\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
			sb.append("\r\n");

			try {
				byte [] headerInfo = sb.toString().getBytes("UTF-8");
				byte [] endInfo = ("\r\n--" + RmsGlobal.BOUNDARY + "--\r\n").getBytes("UTF-8");

				System.out.println(sb.toString());

				/*
				 * URL url = new URL(RmsGlobal.FILE_UPLOAD_URL); Socket socket = new Socket(url.getHost(), url.getPort()); OutputStream os =
				 * socket.getOutputStream(); PrintStream ps = new PrintStream(os, true, "UTF-8"); // 写出请求头 ps.println("POST " +
				 * RmsGlobal.FILE_UPLOAD_URL + " HTTP/1.1"); ps.println("Content-Type: multipart/form-data; boundary=" +
				 * RmsGlobal.BOUNDARY); ps.println("Content-Length: " + String.valueOf(headerInfo.length + uploadFile.length() +
				 * endInfo.length)); InputStream in = new FileInputStream(uploadFile); // 写出数据 os.write(headerInfo); byte[] buf = new
				 * byte[1024]; int len; while ((len = in.read(buf)) != -1) os.write(buf, 0, len); os.write(endInfo); in.close(); os.close();
				 * ps.close();
				 */

				URL url = new URL(RmsGlobal.FILE_UPLOAD_URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + RmsGlobal.BOUNDARY);
				conn.setRequestProperty("Content-Length", String.valueOf(headerInfo.length + uploadFile.length() + endInfo.length));
				conn.setDoOutput(true);

				OutputStream out = conn.getOutputStream();
				InputStream in = new FileInputStream(uploadFile);
				out.write(headerInfo);

				byte [] buf = new byte [1024];
				int len;
				while ((len = in.read(buf)) != -1)
					out.write(buf, 0, len);

				out.write(endInfo);
				in.close();
				out.close();
				if (conn.getResponseCode() == 200) {
					System.out.println("上传成功");
				}
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (IOException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}

			jsonMap.put(TddEnum.RTNTYPE, TddEnum.RTNTYPEY.toString());
		}
		else {
			exception = "上传文件为null";
			jsonMap.put(TddEnum.RTNTYPE, TddEnum.RTNTYPEN.toString());
		}
		String url = "";

		/*
		 * Map<String, String> paramMap = new HashMap<String, String>(); String json = JsoupUtil.getJsonStrByPost(url, paramMap);
		 */

		jsonMap.put(TddEnum.RTNMSG, exception);
		Struts2Utils.renderJson(jsonMap);
		return null;
	}

	public String uploadImage() {

		String exception = "上传成功";
		Map<TddEnum, Object> jsonMap = new HashMap<TddEnum, Object>();
		Calendar ca = Calendar.getInstance();

		StringBuilder sb = new StringBuilder();

		if (uploadFile != null) {

			String fileFormName = this.getUploadFileFileName();
			// String newFileName = ConvertUtil.datetimeStrToDateNoBlank(ca.getTime())+File.separator+fileFormName;
			String newFileName = fileFormName;

			try {
				

				sb.append("--" + RmsGlobal.BOUNDARY + "\r\n");
				sb.append("Content-Disposition: form-data; ");
				sb.append("name=\"testName\"");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("sky");
				sb.append("\r\n");
				
				/**
				 * 上传文件的头
				 */
				sb.append("--" + RmsGlobal.BOUNDARY + "\r\n");
				sb.append("Content-Disposition: form-data;");
				sb.append("name=\"" + fileFormName + "\";");
				sb.append("filename=\"" + newFileName + "\"");
				sb.append("\r\n");
				sb.append("Content-Type: image/jpeg" + "\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
				sb.append("\r\n");
				// sb.append("\r\n--" + RmsGlobal.BOUNDARY + "--\r\n");

				byte [] headerInfo = sb.toString().getBytes("UTF-8");
				byte [] endInfo = ("\r\n--" + RmsGlobal.BOUNDARY + "--\r\n").getBytes("UTF-8");

				URL url = new URL(RmsGlobal.IMAGE_UPLOAD_URL+"?key={size:[{width:150,height:200},{width:250,height:300}]}");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");

				conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + RmsGlobal.BOUNDARY);
				conn.setRequestProperty("Content-Length", String.valueOf(headerInfo.length + uploadFile.length() + endInfo.length));
				
				conn.setDoOutput(true);
				conn.setDoInput(true);
				
				System.out.println(sb.toString());

				OutputStream out = conn.getOutputStream();
				InputStream in = new FileInputStream(uploadFile);
				out.write(headerInfo);

				byte [] buf = new byte [1024];
				int len;
				while ((len = in.read(buf)) != -1){
					
					out.write(buf, 0, len);
				}

				out.write(endInfo);
				in.close();
				out.close();
				if (conn.getResponseCode() == 200) {
					System.out.println(">>>>>>>>>上传成功");
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(  
							conn.getInputStream()));  
		            String line = "";
		            StringBuilder responseResult = new StringBuilder();
		            while ((line = bufferedReader.readLine()) != null) {  
		                responseResult.append(exception+",").append(line);  
		            }  
		            
		            System.out.println("responseResult="+responseResult);
				}
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (IOException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}

			jsonMap.put(TddEnum.RTNTYPE, TddEnum.RTNTYPEY.toString());
		}
		else {
			exception = "上传文件为null";
			jsonMap.put(TddEnum.RTNTYPE, TddEnum.RTNTYPEN.toString());
		}
		String url = "";

		/*
		 * Map<String, String> paramMap = new HashMap<String, String>(); String json = JsoupUtil.getJsonStrByPost(url, paramMap);
		 */

		jsonMap.put(TddEnum.RTNMSG, exception);
		Struts2Utils.renderJson(jsonMap);
		return null;
	}
	
	public String uploadVideo() {

		String exception = "上传成功";
		Map<TddEnum, Object> jsonMap = new HashMap<TddEnum, Object>();
		Calendar ca = Calendar.getInstance();

		StringBuilder sb = new StringBuilder();

		if (uploadFile != null) {

			String fileFormName = this.getUploadFileFileName();
			// String newFileName = ConvertUtil.datetimeStrToDateNoBlank(ca.getTime())+File.separator+fileFormName;
			String newFileName = fileFormName;

			try {
				

				
				/**
				 * 上传文件的头
				 */
				sb.append("--" + RmsGlobal.BOUNDARY + "\r\n");
				sb.append("Content-Disposition: form-data;");
				sb.append("name=\"" + fileFormName + "\";");
				sb.append("filename=\"" + newFileName + "\"");
				sb.append("\r\n");
				sb.append("Content-Type: video/mpeg4" + "\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
				sb.append("\r\n");
				// sb.append("\r\n--" + RmsGlobal.BOUNDARY + "--\r\n");

				byte [] headerInfo = sb.toString().getBytes("UTF-8");
				byte [] endInfo = ("\r\n--" + RmsGlobal.BOUNDARY + "--\r\n").getBytes("UTF-8");

				URL url = new URL(RmsGlobal.VIDEO_UPLOAD_URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");

				conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + RmsGlobal.BOUNDARY);
				conn.setRequestProperty("Content-Length", String.valueOf(headerInfo.length + uploadFile.length() + endInfo.length));
				
				conn.setDoOutput(true);
				conn.setDoInput(true);
				
				System.out.println(sb.toString());

				OutputStream out = conn.getOutputStream();
				InputStream in = new FileInputStream(uploadFile);
				out.write(headerInfo);

				byte [] buf = new byte [1024];
				int len;
				while ((len = in.read(buf)) != -1){
					
					out.write(buf, 0, len);
				}

				out.write(endInfo);
				in.close();
				out.close();
				if (conn.getResponseCode() == 200) {
					System.out.println(">>>>>>>>>上传成功");
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(  
							conn.getInputStream()));  
		            String line = "";
		            StringBuilder responseResult = new StringBuilder();
		            while ((line = bufferedReader.readLine()) != null) {  
		                responseResult.append(exception+",").append(line);  
		            }  
		            
		            System.out.println("responseResult="+responseResult);
				}
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}
			catch (IOException e) {
				e.printStackTrace();
				exception = "上传文件为null" + e.getMessage();
			}

			jsonMap.put(TddEnum.RTNTYPE, TddEnum.RTNTYPEY.toString());
		}
		else {
			exception = "上传文件为null";
			jsonMap.put(TddEnum.RTNTYPE, TddEnum.RTNTYPEN.toString());
		}
		String url = "";

		/*
		 * Map<String, String> paramMap = new HashMap<String, String>(); String json = JsoupUtil.getJsonStrByPost(url, paramMap);
		 */

		jsonMap.put(TddEnum.RTNMSG, exception);
		Struts2Utils.renderJson(jsonMap);
		return null;
	}
}
