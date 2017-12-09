package com.rms.tdd.mananger;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rms.entity.tdd.RmsDictDetail;
import com.rms.service.tdd.DictManager;
import com.rms.tdd.TestParent;

public class TestInterfaceManager extends TestParent {

	@Autowired
	private DictManager	dictManager;

	@Test
	public void testGetDictDetailByDictName() {

		List<RmsDictDetail> lstResult = dictManager.getDictDetailByDictName("param_type");
	}
}
