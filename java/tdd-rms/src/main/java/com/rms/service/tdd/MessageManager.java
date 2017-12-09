package com.rms.service.tdd;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rms.dao.tdd.MessageDao;
import com.rms.entity.tdd.RmsMessage;
import com.rms.modules.orm.Page;

@Service
@Transactional (
    rollbackFor = Exception.class)
public class MessageManager {

	@Autowired
	private MessageDao	messageDao;

	@Transactional (
	    readOnly = true)
	public Page<RmsMessage> getInterfacePage(Map<String, Object> queryMap, Page<RmsMessage> page) {

		Criterion [] criterions = messageDao.getQueryCriterions(queryMap);
		if (criterions != null) {

			page = messageDao.findPage(page, criterions);
		}
		else {
			page = messageDao.getAll(page);
		}

		return page;
	}
	
	public RmsMessage getMessageById(Integer msgId) {
		
		return messageDao.get(msgId);
	}
	
	public void saveMessage(RmsMessage message) {
		
		messageDao.save(message);
	}
	
	public void deleteMessage(RmsMessage message) {
		messageDao.delete(message);
	}
}
