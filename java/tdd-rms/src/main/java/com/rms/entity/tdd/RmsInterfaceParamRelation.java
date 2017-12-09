package com.rms.entity.tdd;

// Generated 2015-6-25 11:50:53 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TddInterfacePramRelation generated by hbm2java
 */
@Entity
@Table(name = "rms_interface_param_relation", catalog = "rms")
public class RmsInterfaceParamRelation implements java.io.Serializable {
    private static final long serialVersionUID = -3903479344170299349L;
    private RmsInterfaceParamRelationId id;

    public RmsInterfaceParamRelation() {
    }

    public RmsInterfaceParamRelation(RmsInterfaceParamRelationId id) {
	this.id = id;
    }

    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "IId", column = @Column(name = "i_id", nullable = false)),
    @AttributeOverride(name = "ipId", column = @Column(name = "ip_id", nullable = false)) })
    public RmsInterfaceParamRelationId getId() {
	return this.id;
    }

    public void setId(RmsInterfaceParamRelationId id) {
	this.id = id;
    }

}