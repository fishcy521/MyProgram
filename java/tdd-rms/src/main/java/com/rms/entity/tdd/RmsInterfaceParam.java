package com.rms.entity.tdd;

// Generated 2015-6-25 11:50:53 by Hibernate Tools 3.4.0.CR1

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * TddInterfaceParam generated by hbm2java
 */
@Entity
@Table(name = "rms_interface_param", catalog = "rms")
public class RmsInterfaceParam implements java.io.Serializable {

    private static final long serialVersionUID = -7678610527045432389L;
    private Integer ipId;
    private String ipName;
    private String ipValue;
    private String ipDesc;
    private String ipRequired;
    private String ipType;
    
    private String selectIpRequired;
    private String selectIpType;
    
    private RmsInterface rmsInterface;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="i_id",nullable=true )
    public RmsInterface getRmsInterface() {
    
    	return rmsInterface;
    }

	
    public void setRmsInterface(RmsInterface rmsInterface) {
    
    	this.rmsInterface = rmsInterface;
    }

	private Set<RmsInterface> rmsInterfaceSet;

    @ManyToMany
    @JoinTable(name = "rms_interface_param_relation", joinColumns = { @JoinColumn(name = "ip_id") }, inverseJoinColumns = { @JoinColumn(name = "i_id") })
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonBackReference
    public Set<RmsInterface> getRmsInterfaceSet() {
	return rmsInterfaceSet;
    }

    public void setRmsInterfaceSet(Set<RmsInterface> rmsInterfaceSet) {
	this.rmsInterfaceSet = rmsInterfaceSet;
    }

    public RmsInterfaceParam() {
    }
    public RmsInterfaceParam(String ipName, String ipValue, String ipDesc) {
	this.ipName = ipName;
	this.ipValue = ipValue;
	this.ipDesc = ipDesc;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ip_id", unique = true, nullable = false)
    public Integer getIpId() {
	return this.ipId;
    }

    public void setIpId(Integer ipId) {
	this.ipId = ipId;
    }

    @Column(name = "ip_name", length = 500)
    public String getIpName() {
	return this.ipName;
    }

    public void setIpName(String ipName) {
	this.ipName = ipName;
    }

    @Column(name = "ip_value", length = 500)
    public String getIpValue() {
	return this.ipValue;
    }

    public void setIpValue(String ipValue) {
	this.ipValue = ipValue;
    }

    @Column(name = "ip_desc", length = 800)
    public String getIpDesc() {
	return this.ipDesc;
    }

    public void setIpDesc(String ipDesc) {
	this.ipDesc = ipDesc;
    }

    @Column(name = "ip_required")
    public String getIpRequired() {
        return ipRequired;
    }

    public void setIpRequired(String ipRequired) {
        this.ipRequired = ipRequired;
    }

    @Column(name = "ip_type" )
    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    @Transient
    public String getSelectIpRequired() {
        return selectIpRequired;
    }

    public void setSelectIpRequired(String selectIpRequired) {
        this.selectIpRequired = selectIpRequired;
    }

    @Transient
    public String getSelectIpType() {
        return selectIpType;
    }

    public void setSelectIpType(String selectIpType) {
        this.selectIpType = selectIpType;
    }
}