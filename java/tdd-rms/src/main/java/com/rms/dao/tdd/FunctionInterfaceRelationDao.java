package com.rms.dao.tdd;

import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsFunctionInterfaceRelation;
import com.rms.entity.tdd.RmsFunctionInterfaceRelationId;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class FunctionInterfaceRelationDao extends HibernateDao<RmsFunctionInterfaceRelation, RmsFunctionInterfaceRelationId> {

	public void deleteRelationByFuncId (Integer funcId) {

		this.batchExecute ("delete from RmsFunctionInterfaceRelation where id.funcId = ? ", funcId);
	}

	public void deleteRelationByInterfaceId (Integer interfaceId) {

		this.batchExecute ("delete from RmsFunctionInterfaceRelation where id.IId = ? ", interfaceId);
	}
}
