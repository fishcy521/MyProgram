package com.rms.dao.tdd;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsInterfaceParam;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class InterfaceParamDao extends HibernateDao<RmsInterfaceParam, Integer> {

	public List<RmsInterfaceParam> findParamsByInterface (Integer interfaceId) {

		Criterion criterion = Restrictions.sqlRestriction (" ip_id in (select ip_id from rms_interface_param_relation where i_id=? )",
		        interfaceId, Hibernate.INTEGER);
		List<RmsInterfaceParam> lstResult = this.find (criterion);
		
		return lstResult;
	}
}
