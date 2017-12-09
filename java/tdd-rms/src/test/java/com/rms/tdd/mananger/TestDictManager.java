package com.rms.tdd.mananger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rms.dao.tdd.DictDao;
import com.rms.dao.tdd.DictDetailDao;
import com.rms.entity.tdd.RmsDict;
import com.rms.entity.tdd.RmsDictDetail;
import com.rms.service.tdd.DictManager;
import com.rms.tdd.TestParent;

public class TestDictManager extends TestParent {

	@Autowired
	private DictManager	dictManager;
	
	@Autowired
	private DictDetailDao dictDetailDao;
	
	@Autowired
	private DictDao dictDao;

	@Test
	public void testGetDictDetailByDictName() {

		List<RmsDictDetail> lstResult = dictManager.getDictDetailByDictName("param_type");
	}
	
	@Test
	public void testCreateDict () {
		
		RmsDict dict = new RmsDict();
		dict.setDictName("testDict");
		dict.setDictDesc("haha");
	
		Set<RmsDictDetail> setDetail = new HashSet<RmsDictDetail>();
		
		RmsDictDetail dictDetail = new RmsDictDetail();
		dictDetail.setDetailDesc("detail haha1");
		dictDetail.setDetailName("test key1");
		dictDetail.setDetailOrder(1);
		dictDetail.setDetailValue("test value1");
		dictDetail.setRmsDict(dict);
		setDetail.add(dictDetail);
		
		dictDetail = new RmsDictDetail();
		dictDetail.setDetailDesc("detail haha2");
		dictDetail.setDetailName("test key2");
		dictDetail.setDetailOrder(2);
		dictDetail.setDetailValue("test value2");
		dictDetail.setRmsDict(dict);
		
		setDetail.add(dictDetail);
		dict.setRmsDictDetails(setDetail);
		dictManager.saveDict(dict);
		//dictManager.saveDictDetail(dictDetail);
	}
}
