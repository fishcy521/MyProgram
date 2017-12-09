package com.rms.commons.global;

public class QuerySqlBean {

	private String id;

	private String name;

	private String value;

	@Override
	public String toString() {
		return this.id + ":" + this.name + ":" + this.value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
