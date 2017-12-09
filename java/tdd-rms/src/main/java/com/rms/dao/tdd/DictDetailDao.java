package com.rms.dao.tdd;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsDictDetail;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class DictDetailDao extends HibernateDao<RmsDictDetail, Integer> {

	public List<RmsDictDetail> getDictDetailByDictName (String dictName) {
		
		List<RmsDictDetail> lstResult = this.find(" from RmsDictDetail where rmsDict.dictName =? order by detailOrder asc", dictName);
		return lstResult;
	}
}
