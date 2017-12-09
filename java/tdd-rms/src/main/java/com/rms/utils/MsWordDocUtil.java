package com.rms.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public final class MsWordDocUtil {

	public static void main(String[] args) {
		String destFile = "D:\\11.doc";
		// #####################根据自定义内容导出Word文档#################################################
		StringBuffer fileCon = new StringBuffer();
		fileCon.append("张大炮");
		fileCon.append("\r\n");

		MsWordDocUtil.exportDoc(destFile, fileCon.toString());

		// ##################根据Word模板导出单个Word文档###################################################
		Map<String, String> map = new HashMap<String, String>();

		map.put("name", "Zues");
		map.put("sex", "男");
		map.put("idCard", "200010");
		map.put("year1", "2000");
		map.put("month1", "07");
		map.put("year2", "2008");
		map.put("month2", "07");
		map.put("gap", "2");
		map.put("zhuanye", "计算机科学与技术");
		map.put("type", "研究生");
		map.put("bianhao", "2011020301");
		map.put("nowy", "2011");
		map.put("nowm", "01");
		map.put("nowd", "20220301");
		// 注意biyezheng_moban.doc文档位置,此例中为应用根目录
		HWPFDocument document = MsWordDocUtil.replaceDoc("biyezheng_moban.doc", map);
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		try {
			document.write(ostream);
			// 输出word文件
			OutputStream outs = new FileOutputStream(destFile);
			outs.write(ostream.toByteArray());
			outs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param destFile
	 * @param fileContent
	 */
	public static void exportDoc(String destFile, String fileContent) {
		try {
			// doc content
			ByteArrayInputStream bais = new ByteArrayInputStream(fileContent.getBytes());
			POIFSFileSystem fs = new POIFSFileSystem();
			DirectoryEntry directory = fs.getRoot();
			directory.createDocument("WordDocument", bais);
			FileOutputStream ostream = new FileOutputStream(destFile);
			fs.writeFilesystem(ostream);
			bais.close();
			ostream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取word模板并替换变量
	 * 
	 * @param srcPath
	 * @param map
	 * @return
	 */
	public static HWPFDocument replaceDoc(String srcPath, Map<String, String> map) {
		try {
			// 读取word模板
			FileInputStream fis = new FileInputStream(new File(srcPath));
			HWPFDocument doc = new HWPFDocument(fis);
			// 读取word文本内容
			Range bodyRange = doc.getRange();
			// 替换文本内容
			for (Map.Entry<String, String> entry : map.entrySet()) {
				bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
			}
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void test() throws Exception {
		// 读取word模板
		String fileDir = "../../../../../doc/";
		FileInputStream in = new FileInputStream(new File(fileDir + "/laokboke.doc"));
		HWPFDocument hdt = new HWPFDocument(in);
		Fields fields = hdt.getFields();
		Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();
		while (it.hasNext()) {
			System.out.println(it.next().getType());
		}

		Map<String, String> map = new HashMap<String, String>();
		// 读取word文本内容
		Range range = hdt.getRange();
		System.out.println(range.text());
		// 替换文本内容
		for (Map.Entry<String, String> entry : map.entrySet()) {
			range.replaceText(entry.getKey(), entry.getValue());
		}
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		String fileName = "" + System.currentTimeMillis();
		fileName += ".doc";
		FileOutputStream out = new FileOutputStream(fileDir + "/" + fileName, true);
		hdt.write(ostream);
		// 输出字节流
		out.write(ostream.toByteArray());
		out.close();
		ostream.close();

	}

	public void test2() throws Exception {
		// 读取word模板文件
		String fileDir = "../../../../../doc/";
		FileInputStream in = new FileInputStream(new File(fileDir + "/laokboke.doc"));
		HWPFDocument hdt = new HWPFDocument(in);
		Fields fields = hdt.getFields();
		Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();
		while (it.hasNext()) {
			System.out.println(it.next().getType());
		}
		// 替换读取到的word模板内容的指定字段
		Range range = hdt.getRange();
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "老k博客");
		map.put("blog_name", "老k博客");
		map.put("domain_name", "laokboke.net");
		map.put("description", "是一个专注于wordpress、java、gis、建站、网站推广、seo的IT博客。");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			range.replaceText(entry.getKey(), entry.getValue());
		}

		// 输出word内容文件流，提供下载
		// response.reset();
		// response.setContentType("application/x-msdownload");
		// response.addHeader("Content-Disposition", "attachment; filename=\"laokboke.doc\"");
		// ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		// ServletOutputStream servletOS = response.getOutputStream();
		// hdt.write(ostream);
		// servletOS.write(ostream.toByteArray());
		// servletOS.flush();
		// servletOS.close();

	}
}