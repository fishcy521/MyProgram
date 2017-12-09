package com.rms.dao.tdd;

import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsInterfaceCategory;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class InterfaceCategoryDao extends HibernateDao<RmsInterfaceCategory, Integer> {

}
