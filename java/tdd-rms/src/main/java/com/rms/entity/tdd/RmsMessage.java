package com.rms.entity.tdd;

// Generated 2015-8-10 14:15:00 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RmsMessage generated by hbm2java
 */
@Entity
@Table (
    name = "rms_message",
    catalog = "rms")
public class RmsMessage implements java.io.Serializable {

	private Integer	msgId;

	private String	msgKey;

	private String	msgValue;

	private String	msgDesc;

	public RmsMessage() {

	}

	public RmsMessage(String msgKey, String msgValue, String msgDesc) {

		this.msgKey = msgKey;
		this.msgValue = msgValue;
		this.msgDesc = msgDesc;
	}

	@Id
	@GeneratedValue
	@Column (
	    name = "msg_id",
	    unique = true,
	    nullable = false)
	public Integer getMsgId() {

		return this.msgId;
	}

	public void setMsgId(Integer msgId) {

		this.msgId = msgId;
	}

	@Column (
	    name = "msg_key",
	    length = 10)
	public String getMsgKey() {

		return this.msgKey;
	}

	public void setMsgKey(String msgKey) {

		this.msgKey = msgKey;
	}

	@Column (
	    name = "msg_value",
	    length = 400)
	public String getMsgValue() {

		return this.msgValue;
	}

	public void setMsgValue(String msgValue) {

		this.msgValue = msgValue;
	}

	@Column (
	    name = "msg_desc",
	    length = 65535)
	public String getMsgDesc() {

		return this.msgDesc;
	}

	public void setMsgDesc(String msgDesc) {

		this.msgDesc = msgDesc;
	}

}