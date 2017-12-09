package com.rms.utils;

public class SigmaPageInfoBean {
	private Integer pageSize;

	private Integer pageNum;

	private Integer totalRowNum;

	private Integer totalPageNum;

	private Integer startRowNum;

	private Integer endRowNum;

	public SigmaPageInfoBean(Integer pageSize, Integer pageNum, Long totalRowNum, Long totalPageNum, Integer startRowNum, Integer endRowNum) {
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.totalPageNum = totalPageNum.intValue();
		this.totalRowNum = totalRowNum.intValue();
		this.startRowNum = startRowNum == null ? (this.totalPageNum - 1) * this.pageSize + 1 : startRowNum;
		this.endRowNum = endRowNum == null ? this.totalPageNum * this.pageSize : endRowNum;
		/*this.startRowNum = (this.totalPageNum - 1) * this.pageSize + 1;
		this.endRowNum = this.totalPageNum * this.pageSize;*/
	}

	public Integer getEndRowNum() {
		return endRowNum;
	}

	public void setEndRowNum(Integer endRowNum) {
		this.endRowNum = endRowNum;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(Integer startRowNum) {
		this.startRowNum = startRowNum;
	}

	public Integer getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(Integer totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public Integer getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(Integer totalRowNum) {
		this.totalRowNum = totalRowNum;
	}

}
