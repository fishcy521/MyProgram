package com.rms.entity.tdd;

// Generated 2015-7-15 14:05:58 by Hibernate Tools 3.4.0.CR1

import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * RmsDict generated by hbm2java
 */
@Entity
@Table (
    name = "rms_dict",
    catalog = "rms")
public class RmsDict implements java.io.Serializable {

	private static final long	serialVersionUID	= 7860425005729419476L;

	private int	               dictId;

	private String	           dictName;

	private String	           dictDesc;

	private String	           dictType;

	private Set<RmsDictDetail>	rmsDictDetails;

	public RmsDict() {

	}

	@Id 
	@GeneratedValue
	@Column (
	    name = "dict_id",
	    unique = true,
	    nullable = false)
	public int getDictId() {

		return this.dictId;
	}

	public void setDictId(int dictId) {

		this.dictId = dictId;
	}

	@Column (
	    name = "dict_name",
	    nullable = false,
	    length = 200)
	public String getDictName() {

		return this.dictName;
	}

	public void setDictName(String dictName) {

		this.dictName = dictName;
	}

	@Column (
	    name = "dict_desc",
	    length = 500)
	public String getDictDesc() {

		return this.dictDesc;
	}

	public void setDictDesc(String dictDesc) {

		this.dictDesc = dictDesc;
	}

	@Column (
	    name = "dict_type",
	    length = 200)
	public String getDictType() {

		return this.dictType;
	}

	public void setDictType(String dictType) {

		this.dictType = dictType;
	}

	@OneToMany (
	    fetch = FetchType.LAZY,
	    cascade = CascadeType.ALL)
	@JoinColumn (
	    name = "dict_id")
	public Set<RmsDictDetail> getRmsDictDetails() {

		return this.rmsDictDetails;
	}

	public void setRmsDictDetails(Set<RmsDictDetail> rmsDictDetails) {

		this.rmsDictDetails = rmsDictDetails;
	}

}