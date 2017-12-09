/**
 * 
 */
package com.rms.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.rms.dao.util.PropertyBeanUtil;

/**
 *
 */
public class RmsDaoSupport extends HibernateDaoSupport {
	
	public RmsDaoSupport() {
		super();
	}

	@Resource(name="sessionFactory")
	public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
		setSessionFactory(hibernateSessionFactory);
	}

	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, final Class clazz) {
		return findBySql(sql, new Object[] {}, clazz);
	}

	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, final Object[] params,
			final Class clazz) {
		return findBySql(sql, null, params, clazz);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findBySql(final String sql, final String[] paramNames,
			final Object[] params, final Class clazz) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					SQLQuery q = session.createSQLQuery(sql);
					setParameterValues(q, paramNames, params);
					q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
					List resList = q.list();
					return PropertyBeanUtil.newInstance().mapListToBeanList(
							resList, clazz);
				} catch (Exception e) {
					throw new HibernateException(e);
				}
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public List findBySql(String sql,int begin,int pageSize,Class clazz){
		return findBySql(sql, null,begin, pageSize, clazz);
	}
	
	@SuppressWarnings("rawtypes")
	public List findBySql(String sql,Object [] params,int begin,int pageSize,Class clazz){
		return findBySql(sql, null, params, begin, pageSize, clazz);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findBySql(final String sql, final String[] paramNames,
			final Object[] params,final int begin,final int pageSize, final Class clazz) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					SQLQuery q = session.createSQLQuery(sql);
					setParameterValues(q, paramNames, params);
					q.setFirstResult(begin);
					q.setMaxResults(pageSize);
					q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
					List resList = q.list();
					return PropertyBeanUtil.newInstance().mapListToBeanList(
							resList, clazz);
				} catch (Exception e) {
					throw new HibernateException(e);
				}
			}
		});
	}
	
	public Integer getIntegerBySql(final String sql) {
		return getIntegerBySql(sql, new Object[] {});
	}

	public Integer getIntegerBySql(final String sql, final Object[] params) {
		return getIntegerBySql(sql, null, params);
	}
	
	public Integer getIntegerBySql(final String sql,final String[] paramNames,final Object[] params){
		Object result = getObjectBySql(sql,paramNames, params);
		return result == null ? null : Integer.valueOf(result.toString());
	}
	
	public Double getDoubleBySql(String sql){
		return getDoubleBySql(sql,null);
	}
	
	public Double getDoubleBySql(String sql,Object[] params){
		return getDoubleBySql(sql, null, params);
	}

	public Double getDoubleBySql(String sql,String [] paramNames, Object[] params) {
		Object result = getObjectBySql(sql,paramNames, params);
		return result == null ? null : Double.valueOf(result.toString());
	}
	
	public Object getObjectBySql(final String sql,final Object[] params){
		return getObjectBySql(sql, null, params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getObjectBySql(final String sql,final String[] paramNames, final Object[] params) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					SQLQuery sqlQuery = session.createSQLQuery(sql);
					setParameterValues(sqlQuery,paramNames, params);
					Object value = null;
					Iterator itr = sqlQuery.list().iterator();
					if (itr.hasNext()) {
						value = itr.next();
					}
					return value;
				} catch (Exception e) {
					throw new HibernateException(e);
				}
			}
		});
	}
	
	protected void setParameterValues(Query query, String[] paramNames,
			Object[] params) {
		if (!ObjectUtils.isEmpty(paramNames)) {
			Assert.isTrue(!ObjectUtils.isEmpty(params));
			Assert.isTrue(paramNames.length == params.length);
		}
		for (int i = 0; i < params.length; i++) {
			if (!ObjectUtils.isEmpty(paramNames)) {
				setParameterValue(query, i, paramNames[i], params[i]);
			} else {
				setParameterValue(query, i, null, params[i]);
			}
		}
	}

	protected void setParameterValue(Query query, int index, String paramName,
			Object param) {
		if (param instanceof String) {
			if (StringUtils.hasText(paramName)) {
				query.setString(paramName, (String) param);
			} else {
				query.setString(index, (String) param);
			}
		} else if (param instanceof Integer) {
			if (StringUtils.hasText(paramName)) {
				query.setInteger(paramName, (Integer) param);
			} else {
				query.setInteger(index, (Integer) param);
			}
		} else if (param instanceof Date) {
			if (StringUtils.hasText(paramName)) {
				query.setDate(paramName, (Date) param);
			} else {
				query.setDate(index, (Date) param);
			}
		} else if (param instanceof java.sql.Date) {
			if (StringUtils.hasText(paramName)) {
				query.setDate(paramName, (Date) param);
			} else {
				query.setDate(index, (Date) param);
			}
		} else if (param instanceof Timestamp) {
			if (StringUtils.hasText(paramName)) {
				query.setTimestamp(paramName, (Timestamp) param);
			} else {
				query.setTimestamp(index, (Timestamp) param);
			}
		} else if (param instanceof Double) {
			if (StringUtils.hasText(paramName)) {
				query.setDouble(paramName, (Double) param);
			} else {
				query.setDouble(index, (Double) param);
			}
		} else if (param instanceof Float) {
			if (StringUtils.hasText(paramName)) {
				query.setFloat(paramName, (Float) param);
			} else {
				query.setFloat(index, (Float) param);
			}
		} else if (param instanceof Long) {
			if (StringUtils.hasText(paramName)) {
				query.setLong(paramName, (Long) param);
			} else {
				query.setLong(index, (Long) param);
			}
		} else {
			throw new IllegalArgumentException(
					"Error parameter type,unsupport "
							+ param.getClass().getName());
		}
	}
	
	
	protected void setLong(CallableStatement cs,String pName,Long value) throws Exception{
		if(value == null){
			cs.setNull(pName,Types.NUMERIC);
		}
		else{
			cs.setLong(pName,value);
		}
	}
	
	protected void setDouble(CallableStatement cs,String pName,Double value) throws Exception{
		if(value == null){
			cs.setNull(pName,Types.DOUBLE);
		}
		else{
			cs.setDouble(pName,value);
		}
	}
	
	protected void setDate(CallableStatement cs,String pName,Date value) throws Exception{
		if(value == null){
			cs.setNull(pName,Types.TIMESTAMP);
		}
		else{
			cs.setDate(pName,new java.sql.Date(value.getTime()));
		}
	}
	
	protected void setString(CallableStatement cs,String pName,String value) throws Exception{
		if(value == null){
			cs.setNull(pName,Types.VARCHAR);
		}
		else{
			cs.setString(pName,value);
		}
	}
}
