package com.rms.dao.tdd;

import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsInterfaceCategoryRelation;
import com.rms.entity.tdd.RmsInterfaceCategoryRelationId;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class InterfaceCategoryRelationDao extends HibernateDao<RmsInterfaceCategoryRelation, RmsInterfaceCategoryRelationId> {

	public void deleteRelationByCateId (Integer cateId) {

		this.batchExecute ("delete from RmsInterfaceCategoryRelation where id.ICateId = ? ", cateId);
	}

	public void deleteRelationByInterfaceId (Integer interfaceId) {

		this.batchExecute ("delete from RmsInterfaceCategoryRelation where id.IId = ? ", interfaceId);
	}
}
