package com.rms.web;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.rms.modules.utils.web.struts2.Struts2Utils;

/**
 * Struts2中典型CRUD Action的抽象基类.
 * 
 * 主要定义了对Preparable,ModelDriven接口的使用,以及CRUD函数和返回值的命名.
 * 
 * @param <T>
 *            CRUDAction所管理的对象类型.
 * 
 */
public abstract class CrudActionSupport<T> extends ActionSupport implements ModelDriven<T>, Preparable {

    private static final long serialVersionUID = -1653204626115064950L;

    /** 进行增删改操作后,以redirect方式重新打开action默认页的result名. */
    public static final String RELOAD = "reload";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Action函数, 默认的action函数, 默认调用list()函数.
     */
    @Override
    public String execute() throws Exception {
	return list();
    }

    // -- CRUD Action函数 --//
    /**
     * Action函数,显示Entity列表界面. 建议return SUCCESS.
     */
    public abstract String list() throws Exception;

    /**
     * Action函数,显示新增或修改Entity界面. 建议return INPUT.
     */
    @Override
    public abstract String input() throws Exception;

    /**
     * Action函数,新增或修改Entity. 建议return RELOAD.
     */
    public abstract String save() throws Exception;

    /**
     * Action函数,删除Entity. 建议return RELOAD.
     */
    public abstract String delete() throws Exception;

    // -- Preparable函数 --//
    /**
     * 实现空的prepare()函数,屏蔽了所有Action函数都会执行的公共的二次绑定.
     */
    public void prepare() throws Exception {
    }

    /**
     * 定义在input()前执行二次绑定.
     */
    public void prepareInput() throws Exception {
	prepareModel();
    }

    /**
     * 定义在save()前执行二次绑定.
     */
    public void prepareSave() throws Exception {
	prepareModel();
    }

    public void prepareDelete() throws Exception {
	prepareModel();
    }
    
    /**
     * 等同于prepare()的内部函数,供prepardMethodName()函数调用.
     */
    protected abstract void prepareModel() throws Exception;

    public Map<String, Object> getRequestParameterMaps() {
	ServletRequest request = Struts2Utils.getRequest();
	Enumeration paramNames = request.getParameterNames();
	Map<String, Object> params = new TreeMap<String, Object>();
	while (paramNames != null && paramNames.hasMoreElements()) {
	    String paramName = (String) paramNames.nextElement();
	    String[] values = request.getParameterValues(paramName);
	    if (values == null || values.length == 0) {
		// Do nothing, no values found at all.
	    } else if (values.length > 1) {
		params.put(paramName, values);// ConvertUtil.getStringArray2(values,
					      // ",", ""));
	    } else {
		params.put(paramName, values[0]);
	    }
	}
	return params;
    }

}
