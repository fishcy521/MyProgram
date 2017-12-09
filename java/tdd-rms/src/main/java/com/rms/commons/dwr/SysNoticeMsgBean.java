package com.rms.commons.dwr;

public class SysNoticeMsgBean {

	private String hfmpguid = "";
	private String title = "";// ����
	private String author = "";// ����
	private String content = "";// ����
	private String publishDate = "";// ��������
	private String infoType = "";// ����
	private String infoGrade = "";// ����
	private String order = "";// ˳��
	private String remark = "";

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHfmpguid() {
		return hfmpguid;
	}

	public void setHfmpguid(String hfmpguid) {
		this.hfmpguid = hfmpguid;
	}

	public String getInfoGrade() {
		return infoGrade;
	}

	public void setInfoGrade(String infoGrade) {
		this.infoGrade = infoGrade;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
