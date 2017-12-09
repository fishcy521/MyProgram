package com.rms.dao.tdd;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.rms.modules.orm.hibernate.HibernateDao;

public abstract class RmsDao<T, PK extends Serializable> extends HibernateDao<T, PK> {

	/**
	 * 分页查询公用
	 * @param queryMap
	 * @return
	 */
	public abstract Criterion [] getQueryCriterions (Map<String, Object> queryMap);
}
