package com.rms.entity.tdd;

// Generated 2015-8-26 14:10:45 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TvPraiseRecord generated by hbm2java
 */
@Entity
@Table (
    name = "tv_praise_record",
    catalog = "rms")
public class TvPraiseRecord implements java.io.Serializable {

	private Integer	tprId;

	private int	    tprUserId;

	private Date	tprAddtime;

	private int	    tprTvId;

	private byte	tprType;

	public TvPraiseRecord() {

	}

	public TvPraiseRecord(int tprUserId, Date tprAddtime, int tprTvId, byte tprType) {

		this.tprUserId = tprUserId;
		this.tprAddtime = tprAddtime;
		this.tprTvId = tprTvId;
		this.tprType = tprType;
	}

	@Id
	@GeneratedValue (
	    strategy = IDENTITY)
	@Column (
	    name = "tpr_id",
	    unique = true,
	    nullable = false)
	public Integer getTprId() {

		return this.tprId;
	}

	public void setTprId(Integer tprId) {

		this.tprId = tprId;
	}

	@Column (
	    name = "tpr_user_id",
	    nullable = false)
	public int getTprUserId() {

		return this.tprUserId;
	}

	public void setTprUserId(int tprUserId) {

		this.tprUserId = tprUserId;
	}

	@Temporal (TemporalType.TIMESTAMP)
	@Column (
	    name = "tpr_addtime",
	    nullable = false,
	    length = 19)
	public Date getTprAddtime() {

		return this.tprAddtime;
	}

	public void setTprAddtime(Date tprAddtime) {

		this.tprAddtime = tprAddtime;
	}

	@Column (
	    name = "tpr_tv_id",
	    nullable = false)
	public int getTprTvId() {

		return this.tprTvId;
	}

	public void setTprTvId(int tprTvId) {

		this.tprTvId = tprTvId;
	}

	@Column (
	    name = "tpr_type",
	    nullable = false)
	public byte getTprType() {

		return this.tprType;
	}

	public void setTprType(byte tprType) {

		this.tprType = tprType;
	}

}