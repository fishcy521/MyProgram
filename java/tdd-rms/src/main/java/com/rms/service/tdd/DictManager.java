package com.rms.service.tdd;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rms.dao.tdd.DictDao;
import com.rms.dao.tdd.DictDetailDao;
import com.rms.entity.tdd.RmsDict;
import com.rms.entity.tdd.RmsDictDetail;

@Service
@Transactional (
    rollbackFor = Exception.class)
public class DictManager {

	@Autowired
	private DictDao	      dictDao;

	@Autowired
	private DictDetailDao	dictDetailDao;

	@Transactional (
	    readOnly = true)
	public List<RmsDictDetail> getDictDetailByDictName (String dictName) {

		List<RmsDictDetail> lstResult = dictDetailDao.getDictDetailByDictName (dictName);
		return lstResult;
	}
	
	@Transactional (
		    readOnly = true)
	public Map<Integer, String> getDictDetailValuesMap (String dictName) {

		List<RmsDictDetail> lstResult = this.getDictDetailByDictName (dictName);
		
		Map<Integer, String> resMap = new LinkedHashMap<Integer, String> ();
		for (RmsDictDetail rmsDictDetail : lstResult) {
			resMap.put (rmsDictDetail.getDetailId (), rmsDictDetail.getDetailValue ());
        }
		return resMap;
	}
	
	@Transactional (
		    readOnly = true)
	public Map<Integer, String> getDictDetailNamesMap (String dictName) {

		List<RmsDictDetail> lstResult = this.getDictDetailByDictName (dictName);
		
		Map<Integer, String> resMap = new LinkedHashMap<Integer, String> ();
		for (RmsDictDetail rmsDictDetail : lstResult) {
			resMap.put (rmsDictDetail.getDetailId (), rmsDictDetail.getDetailName ());
        }
		return resMap;
	}
	
	@Transactional (
		    readOnly = true)
	public Map<String, String> getDictDetailNamesValueMap (String dictName) {

		List<RmsDictDetail> lstResult = this.getDictDetailByDictName (dictName);
		
		Map<String, String> resMap = new LinkedHashMap<String, String> ();
		for (RmsDictDetail rmsDictDetail : lstResult) {
			resMap.put (rmsDictDetail.getDetailName (), rmsDictDetail.getDetailValue ());
        }
		return resMap;
	}
	
	public void saveDict(RmsDict dict) {
		dictDao.save(dict);
	}
	
	public void saveDictDetail(RmsDictDetail detail) {
		dictDetailDao.save(detail);
	}
}
