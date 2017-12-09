/**
 * 
 */
package com.rms.dao.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* @describe 
*  定义对bean的一些转化封装操作。所有的class参数均为标准javabean
* @author chenxb
* @date 2010-3-9 下午05:56:09
*/
public class PropertyBeanUtil {
	
	private PropertyBeanUtil(){}
	
	private static Log _log = LogFactory.getLog(PropertyBeanUtil.class);
	
	private static PropertyBeanUtil beanutil = null ;
	
	static{
		ConvertUtils.register(new RegisteredConverter(), java.util.Date.class) ;//注册。支持对java.util.Date的转换
	}
	
	public static PropertyBeanUtil newInstance(){
		if(beanutil==null) beanutil = new PropertyBeanUtil() ;
		return beanutil ;
	}
	/**
	 * 
	 * @describe 
	 * 
	 * @param mapBean
	 * @param bean 如果needCreate=true,bean需为Class类型。
	 * @param needCreate 是否需要创建bean。
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @author chenxb
	 * @date 2010-3-12
	 */
	public Object mapListToBean(Map<String,Object> mapBean ,Object bean ,boolean needCreate) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		Object instance = null;
		if(needCreate){
			instance = ((Class)bean).newInstance() ;
		}else{
			instance = bean ;
		}
		return this.mapListToBean(mapBean, instance );
	}
	/**
	 * 
	 * @describe 
	 * 根据request中能取到值的key向bean中填充值，如果不对应则不会进行填充。
	 * @param request
	 * @param bean  如果needCreate=true,bean需为Class类型。
	 * @param needCreate 是否需要创建bean。
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @author chenxb
	 * @date 2010-3-12
	 */
	public Object populateBeanWithRequest(HttpServletRequest request,Object bean ,boolean needCreate) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		Object instance = null;
		if(needCreate){
			instance = ((Class)bean).newInstance() ;
		}else{
			instance = bean ;
		}
		return this.populateBeanWithRequest(request, instance ) ;
	}
	/**
	 * 
	 * @describe 
	 * 适用于以下情况：
	 * SQLQuery q = session.createSQLQuery(sql);
	 * q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP) ;
	 * List list = q.list() ;
	 * @param mapList  bean属性的key到valueObject的对照。Object为bean属性的原始类型。
	 * @param bean
	 * @return 	 返回bean类型的list
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @author chenxb
	 * @date 2010-3-9
	 */
	public List mapListToBeanList(List<Map<String,Object>> mapList , Class bean ) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		List beanList = new ArrayList() ;
		Object intance = null ;
		Iterator it = mapList.iterator() ;
		while(it.hasNext()){
			intance = this.mapListToBean((Map<String,Object>)it.next(), bean,true) ;
			beanList.add(intance) ;
		}
		return beanList ;
	}
	/*
	 * ...
	 */
	private Object populateBeanWithRequest(HttpServletRequest request,Object intance) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String,Object> newMap = new HashMap<String,Object>() ;
		Map parameterMap = request.getParameterMap() ;
		String paramName = null ;
		String[] paramValues = null ;
		Class retType = null ;
		Object converted = null ; 
		Iterator it = parameterMap.keySet().iterator() ;
		while(it.hasNext()){
			paramName = (String)it.next() ;
			paramValues = (String[])parameterMap.get(paramName) ;
			if(paramValues==null||paramValues.length!=1||paramValues[0].equals("")||paramValues[0]==null||paramValues[0].equals("null")) continue;
			try{
				retType = PropertyUtils.getPropertyType(intance, paramName);
				if(retType==null) continue;//没有paramName的setter方法，不进行set
				converted = ConvertUtils.convert(paramValues[0], retType) ;
				newMap.put(paramName, converted);
			}catch(Exception e){
				e.printStackTrace() ;
				_log.error(e) ;
			}
		}
		return this.mapListToBean(newMap, intance) ;
	}
	/*
	 * 
	 */
	private Object mapListToBean(Map<String,Object> mapBean , Object intance ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String key = null ;
		Object value = null ;
		Iterator mapIt = mapBean.keySet().iterator() ;
		while(mapIt.hasNext()){
			key = (String)mapIt.next() ;
			value = mapBean.get(key) ;
			if(value==null) continue ;
			if(key.equals("ROWNUM_")) continue;
			key = this.convertDBColumnNameToBeanPropertyName(key);//添加对数据库字段名，即未起别名字段的转换
			String typeName = value.getClass().getName() ; 
			if(typeName.equals("java.math.BigDecimal")
				|| typeName.equals("java.sql.Timestamp")
				|| typeName.equals("java.lang.Character") ){//对value类型做判断是否需要特别转化 //TODO
				this.handleSpecialSetter(intance, key, value) ;
			}else{
				PropertyUtils.setSimpleProperty(intance, key, value) ;
			}
		}
		return intance ;
	}
	/*
	 * 对set时出现异常的情况做细化处理。//TODO 待完善。
	 */
	private void handleSpecialSetter(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object real = value ;
		String typeName = value.getClass().getName() ;
		Class retType = PropertyUtils.getPropertyType(bean, name);
		if(retType==null && !name.equals("rownum") && !name.equals("rn")){
			throw new IllegalAccessException("invalid property name '" + name + "' !") ;
		}
		else if(retType == null && (name.equals("rownum") || name.equals("rn"))){
			return;
		}
		String retTypeName = retType.getName() ; //取得类名：例如  long或者java
		
		if(typeName.equals("java.math.BigDecimal")){//BigDecimal
			if(retTypeName.equals("java.lang.Long")||retTypeName.equals("long")){
				real = ((java.math.BigDecimal)value).longValue() ;
			}else if(retTypeName.equals("java.lang.Integer")||retTypeName.equals("int")){
				real = ((java.math.BigDecimal)value).intValue() ;
			}else if(retTypeName.equals("java.lang.Double")||retTypeName.equals("double")){
				real = ((java.math.BigDecimal)value).doubleValue() ;
			}else if(retTypeName.equals("java.lang.Float")||retTypeName.equals("float")){
				real = ((java.math.BigDecimal)value).floatValue() ;
			}
			else if(retTypeName.equals("java.lang.String")){
				real = value.toString();
			}
			//real = ConvertUtils.convert(value.toString(), retType) ; //精度问题
		}
		else if(typeName.equals("java.sql.Timestamp"))
		{
			if(retTypeName.equals("java.sql.Date")){
				real =  new java.sql.Date( ((java.sql.Timestamp)value).getTime() );
			}else if(retTypeName.equals("java.util.Date")){
				real = (java.util.Date)value;
			}
		}
		else if(typeName.equals("java.lang.Character"))
		{
			real = ((java.lang.Character)value).toString();
		}
		PropertyUtils.setSimpleProperty(bean, name, real ) ;
	}
	
	/**
	 * @describe
	 * 对没有起别名的select字段做转化
	 * 以方便只存储外键的情况下的查询关联表其它字段
	 * @param name
	 * @return
	 * @author sunli
	 * @date 2012-8-4
	 */
	private String convertDBColumnNameToBeanPropertyName(String name){
		//判断是否是没有起别名的数据库字段名
		if(name!=null && (name.indexOf("_")!=-1 || name.equals(name.toUpperCase()))){
			name = name.toLowerCase();
			
			//去掉最前面的下划线
			if(name.indexOf("_")!=-1 && name.indexOf("_")==0){
				name = name.substring(1, name.length());
			}
			
			//去掉最后面的下划线
			if(name.indexOf("_")!=-1 && name.indexOf("_")==name.length()-1){
				name = name.substring(0, name.length()-1);
			}
			
			while(name.indexOf("_")!=-1){
				int index = name.indexOf("_");
				name = name.substring(0,index) + name.substring(index+1, index+2).toUpperCase() + name.substring(index+2, name.length());
			}
		}
		
		return name;
	}
}
