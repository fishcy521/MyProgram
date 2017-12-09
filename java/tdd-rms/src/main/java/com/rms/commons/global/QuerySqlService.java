package com.rms.commons.global;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.rms.modules.utils.ConvertUtil;

public class QuerySqlService extends DefaultHandler {
	private List<QuerySqlBean> sqlBeanList = null;

	private QuerySqlBean sqlBean = null;

	private String preTag = null;// 作用是记录解析时的上一个节点名称

	public List<QuerySqlBean> getQuerySqlList(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		QuerySqlService handler = new QuerySqlService();
		parser.parse(xmlStream, handler);
		return handler.getSqlBeanList();
	}

	@Override
	public void startDocument() throws SAXException {
		sqlBeanList = new ArrayList<QuerySqlBean>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("sql".equals(qName)) {
			sqlBean = new QuerySqlBean();
			sqlBean.setId(attributes.getValue(0));
		}
		preTag = qName;// 将正在解析的节点名称赋给preTag
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("sql".equals(qName)) {
			sqlBeanList.add(sqlBean);
			sqlBean = null;
		}
		preTag = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if ("name".equals(preTag)) {
				if ("".equals(ConvertUtil.convertNull(sqlBean.getName()))) {
					sqlBean.setName(content);
				} else {
					sqlBean.setName(sqlBean.getName() + content);
				}
			} else if ("value".equals(preTag)) {
				if ("".equals(ConvertUtil.convertNull(sqlBean.getValue()))) {
					sqlBean.setValue(content);
				} else {
					sqlBean.setValue(sqlBean.getValue() + content);
				}
			}
		}
	}

	public List<QuerySqlBean> getSqlBeanList() {
		return sqlBeanList;
	}

}
