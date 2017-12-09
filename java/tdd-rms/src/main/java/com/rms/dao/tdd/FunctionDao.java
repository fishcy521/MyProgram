package com.rms.dao.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsFunction;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class FunctionDao extends RmsDao<RmsFunction, Integer> {

	public Criterion [] getQueryCriterions (Map<String, Object> queryMap) {

		List<Criterion> criterionList = new ArrayList<Criterion> ();
		Criterion [] criterions = null;
		if (queryMap != null && !queryMap.isEmpty ()) {

			String paramFuncName = (String) queryMap.get ("paramFuncName");
			String paramFuncNameZh = (String) queryMap.get ("paramFuncNameZh");
			String paramFuncDesc = (String) queryMap.get ("paramFuncDesc");

			if (StringUtils.isNotEmpty (paramFuncName)) {

				Criterion criterionFuncName = Restrictions.like ("funcName", paramFuncName, MatchMode.ANYWHERE);
				criterionList.add (criterionFuncName);
			}

			if (StringUtils.isNotEmpty (paramFuncNameZh)) {

				Criterion criterionFuncNameZh = Restrictions.like ("funcNameZh", paramFuncNameZh, MatchMode.ANYWHERE);
				criterionList.add (criterionFuncNameZh);
			}
			
			if (StringUtils.isNotEmpty (paramFuncDesc)) {

				Criterion criterionFuncDesc = Restrictions.like ("funcDesc", paramFuncDesc, MatchMode.ANYWHERE);
				criterionList.add (criterionFuncDesc);
			}
			criterions = criterionList.toArray (new Criterion [criterionList.size ()]);
		}
		
		return criterions;
	}
}
