package com.rms.tddenum;

public enum TddEnum {
	interfaceSelect ("y"), interfaceUnSelect ("n"), RTNTYPEY ("Y"), RTNTYPEN ("N"), RTNTYPE ("rtnType"), RTNMSG ("rtnMsg"), RTNID ("rtnId");

	private String	strResult;

	private TddEnum (String strResult) {

		this.strResult = strResult;
	}

	@Override
	public String toString () {

		return strResult;

	}
}
