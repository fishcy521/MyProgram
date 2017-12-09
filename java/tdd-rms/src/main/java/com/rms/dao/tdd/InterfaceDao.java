package com.rms.dao.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.rms.commons.global.RmsQuerySqlGlobal;
import com.rms.entity.tdd.RmsInterface;
import com.rms.modules.orm.hibernate.HibernateDao;
import com.rms.modules.utils.ConvertUtil;

@Component
public class InterfaceDao extends RmsDao<RmsInterface, Integer> {

	public List<RmsInterface> findInterfaceWithCataId (Integer iCateId) {

		// DetachedCriteria criteria = this.createCriteria(criterions)
		// Criterion criterion = Restrictions.eq(propertyName, value);
		Criteria criteria = getSession ().createCriteria (RmsInterface.class);
		criteria.add (Restrictions.sqlRestriction (" i_id in (select i_id from rms_interface_category_relation where i_cate_id =? ) ",
		        iCateId, Hibernate.INTEGER));
		List<RmsInterface> list = criteria.list ();
		return list;
	}

	public List<RmsInterface> findInterfaceWithoutCataId (Integer iCateId) {

		Criteria criteria = getSession ().createCriteria (RmsInterface.class);

		criteria.add (Restrictions.sqlRestriction (" i_id not in (select i_id from rms_interface_category_relation where i_cate_id =? ) ",
		        iCateId, Hibernate.INTEGER));
		List<RmsInterface> list = criteria.list ();
		return list;
	}
	
	public List<RmsInterface> findInterfaceWithFuncId (Integer funcId) {

		// DetachedCriteria criteria = this.createCriteria(criterions)
		// Criterion criterion = Restrictions.eq(propertyName, value);
		Criteria criteria = getSession ().createCriteria (RmsInterface.class);
		criteria.add (Restrictions.sqlRestriction (" i_id in (select i_id from rms_function_interface_relation where func_id =? ) ",
				funcId, Hibernate.INTEGER));
		criteria.addOrder(Order.asc("IName"));
		List<RmsInterface> list = criteria.list ();
		return list;
	}

	public List<RmsInterface> findInterfaceWithoutFuncId (Integer funcId) {

		Criteria criteria = getSession ().createCriteria (RmsInterface.class);

		criteria.add (Restrictions.sqlRestriction (" i_id not in (select i_id from rms_function_interface_relation where func_id =? ) ",
				funcId, Hibernate.INTEGER));
		criteria.addOrder(Order.asc("IName"));
		List<RmsInterface> list = criteria.list ();
		return list;
	}
	
	/**
	 * 分页查询公用
	 * @param queryMap
	 * @return
	 */
	public Criterion [] getQueryCriterions (Map<String, Object> queryMap) {

		List<Criterion> criterionList = new ArrayList<Criterion> ();
		Criterion [] criterions = null;
		if (queryMap != null && !queryMap.isEmpty ()) {

			String paramIName = (String) queryMap.get ("paramIName");
			String paramIUrl = (String) queryMap.get ("paramIUrl");
			String paramICateId = (String) queryMap.get ("paramICateId");
			String paramFuncId = (String) queryMap.get ("paramFuncId");
			String paramHasParam = (String) queryMap.get ("paramHasParam");
			String paramStatus = (String) queryMap.get ("paramStatus");

			if (StringUtils.isNotEmpty (paramIName)) {

				Criterion criterionIName = Restrictions.or(Restrictions.or (Restrictions.like ("IName", paramIName, MatchMode.ANYWHERE),
				        Restrictions.like ("INameZh", paramIName, MatchMode.ANYWHERE)),Restrictions.like ("IDesc", paramIName, MatchMode.ANYWHERE));
				criterionList.add (criterionIName);
			}

			if (StringUtils.isNotEmpty (paramIUrl)) {

				Criterion criterionIUrl = Restrictions.like ("IUrl", paramIUrl, MatchMode.ANYWHERE);
				criterionList.add (criterionIUrl);
			}

			if (StringUtils.isNotEmpty (paramICateId)) {

				Criterion criterionICateId = Restrictions.sqlRestriction (
				        " i_id in (select i_id from rms_interface_category_relation where i_cate_id=? )",
				        ConvertUtil.convertInt (paramICateId), Hibernate.INTEGER);
				criterionList.add (criterionICateId);
			}

			if (StringUtils.isNotEmpty (paramFuncId)) {

				
				Criterion criterionFuncId = Restrictions.sqlRestriction (
				        " i_id in (select i_id from rms_function_interface_relation a  where func_id=? )",
				        ConvertUtil.convertInt (paramFuncId), Hibernate.INTEGER);
				criterionList.add (criterionFuncId);
				
				
			}

			if (StringUtils.isNotEmpty (paramHasParam)) {

				Criterion criterionHasParam = Restrictions.eq ("IHasParam", paramHasParam);
				criterionList.add (criterionHasParam);
			}

			if (StringUtils.isNotEmpty (paramStatus)) {
				StringBuilder strRestrict = new StringBuilder ();
				strRestrict.append (" i_id in (");
				strRestrict.append (RmsQuerySqlGlobal.getInstance ().getQuerySqlById ("interface_status"));
				if (StringUtils.equals ("y", paramStatus)) {
					strRestrict.append (" and tmp.interface_status = 'finished' ");
				}
				else {
					strRestrict.append (" and tmp.interface_status = 'unfinished' ");
				}
				strRestrict.append (" ) ");
				Criterion criterionStatus = Restrictions.sqlRestriction (strRestrict.toString ());
				criterionList.add (criterionStatus);
			}
			criterions = criterionList.toArray (new Criterion [criterionList.size ()]);
		}
		
		return criterions;
	}
}
