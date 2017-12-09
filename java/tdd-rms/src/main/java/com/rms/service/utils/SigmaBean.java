package com.rms.service.utils;

import java.util.List;
import java.util.Map;

/*******************************************************************************************************************************************************************************************************
 * { action : 'save', fieldsName : [..], insertedRecords : [..], updatedRecords
 * : [..], deletedRecords : [..], recordType : "object"/"array", parameters : {}
 * }
 * 
 * 
 */
public class SigmaBean<T> {
    private List<String> fieldsName;

    private String recordType;

    private Map<String, Object> parameters;

    private String action;

    private List<T> insertedRecords;

    private List<T> updatedRecords;

    private List<T> deletedRecords;

    private List<Map> columnInfo;

    private Map pageInfo;

    public SigmaBean() {

    }

    // public SigmaBean(String jsonString){
    // sigmaBean=JsonBinder.buildNormalBinder().fromJson(jsonString,sigmaBean.getClass());
    //
    // }
    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    public List<T> getDeletedRecords() {
	return deletedRecords;
    }

    public void setDeletedRecords(List<T> deletedRecords) {
	this.deletedRecords = deletedRecords;
    }

    public List<String> getFieldsName() {
	return fieldsName;
    }

    public void setFieldsName(List<String> fieldsName) {
	this.fieldsName = fieldsName;
    }

    public List<T> getInsertedRecords() {
	return insertedRecords;
    }

    public void setInsertedRecords(List<T> insertedRecords) {
	this.insertedRecords = insertedRecords;
    }

    public Map<String, Object> getParameters() {
	return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
	this.parameters = parameters;
    }

    public String getRecordType() {
	return recordType;
    }

    public void setRecordType(String recordType) {
	this.recordType = recordType;
    }

    public List<T> getUpdatedRecords() {
	return updatedRecords;
    }

    public void setUpdatedRecords(List<T> updatedRecords) {
	this.updatedRecords = updatedRecords;
    }

    public Map getPageInfo() {
	return pageInfo;
    }

    public void setPageInfo(Map pageInfo) {
	this.pageInfo = pageInfo;
    }

    public List<Map> getColumnInfo() {
	return columnInfo;
    }

    public void setColumnInfo(List<Map> columnInfo) {
	this.columnInfo = columnInfo;
    }

}
