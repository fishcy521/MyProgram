package com.rms.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public abstract class SimpleActionSupport extends ActionSupport {

	private static final long serialVersionUID = -1653204626115064950L;

	/** 进行增删改操作后,以redirect方式重新打开action默认页的result名. */
	public static final String RELOAD = "reload";

	public static final String HEAD = "head";

	protected Logger logger = LoggerFactory.getLogger(getClass());


}
