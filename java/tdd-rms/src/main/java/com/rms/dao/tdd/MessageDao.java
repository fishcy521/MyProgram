package com.rms.dao.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsMessage;

@Component
public class MessageDao extends RmsDao<RmsMessage, Integer> {

	/**
	 * 分页查询公用
	 * @param queryMap
	 * @return
	 */
	public Criterion [] getQueryCriterions (Map<String, Object> queryMap) {

		List<Criterion> criterionList = new ArrayList<Criterion> ();
		Criterion [] criterions = null;
		if (queryMap != null && !queryMap.isEmpty ()) {

			String paramKey = (String) queryMap.get ("paramKey");
			String paramValue = (String) queryMap.get ("paramValue");

			if (StringUtils.isNotEmpty (paramKey)) {
				
				Criterion criterionKey = Restrictions.eq("msgKey", paramKey);
				criterionList.add (criterionKey);
			}
			
			if (StringUtils.isNotEmpty (paramValue)) {

				Criterion criterionValue = Restrictions.or (Restrictions.like ("msgValue", paramValue, MatchMode.ANYWHERE),
				        Restrictions.like ("msgDesc", paramValue, MatchMode.ANYWHERE));
				criterionList.add (criterionValue);
			}

			
			criterions = criterionList.toArray (new Criterion [criterionList.size ()]);
		}
		
		return criterions;
	}
}
