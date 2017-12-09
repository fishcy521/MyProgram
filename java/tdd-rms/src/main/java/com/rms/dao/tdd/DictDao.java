package com.rms.dao.tdd;

import org.springframework.stereotype.Component;

import com.rms.entity.tdd.RmsDict;
import com.rms.modules.orm.hibernate.HibernateDao;

@Component
public class DictDao extends HibernateDao<RmsDict, Integer> {

}
