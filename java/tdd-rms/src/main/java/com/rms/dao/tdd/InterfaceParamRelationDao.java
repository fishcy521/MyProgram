package com.rms.dao.tdd;

import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsInterfaceParamRelation;
import com.rms.entity.tdd.RmsInterfaceParamRelationId;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class InterfaceParamRelationDao extends HibernateDao<RmsInterfaceParamRelation, RmsInterfaceParamRelationId> {

	public void deleteRelationByInterfaceId (Integer interfaceId) {

		this.batchExecute ("delete from RmsInterfaceParamRelation where id.IId = ? ", interfaceId);
	}

	public void deleteRelationByParamId (Integer paramId) {

		this.batchExecute ("delete from RmsInterfaceCategoryRelation where id.ipId = ? ", paramId);
	}
}
