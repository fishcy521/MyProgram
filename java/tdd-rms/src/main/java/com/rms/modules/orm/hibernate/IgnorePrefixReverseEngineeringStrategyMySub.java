package com.rms.modules.orm.hibernate;

import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;

import com.rms.modules.orm.hibernate.IgnorePrefixReverseEngineeringStrategy;

public class IgnorePrefixReverseEngineeringStrategyMySub extends IgnorePrefixReverseEngineeringStrategy {

	public IgnorePrefixReverseEngineeringStrategyMySub(ReverseEngineeringStrategy delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	protected int getPrefixLength() {
		// TODO Auto-generated method stub
		return 1;
	}

}
