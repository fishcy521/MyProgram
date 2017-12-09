package com.rms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encrypt {

    public static String md5(String text) {
	MessageDigest msgDigest = null;
	try {
	    msgDigest = MessageDigest.getInstance("MD5");
	} catch (NoSuchAlgorithmException e) {
	    throw new IllegalStateException("System doesn't support MD5 algorithm.");
	}
	try {
	    msgDigest.update(text.getBytes("GBK"));
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
	byte[] bytes = msgDigest.digest();
	byte tb;
	char low;
	char high;
	char tmpChar;
	StringBuilder md5Str = new StringBuilder(32);
	for (int i = 0; i < bytes.length; i++) {
	    tb = bytes[i];
	    tmpChar = (char) ((tb >>> 4) & 0x000f);
	    if (tmpChar >= 10) {
		high = (char) (('a' + tmpChar) - 10);
	    } else {
		high = (char) ('0' + tmpChar);
	    }
	    md5Str.append(high);
	    tmpChar = (char) (tb & 0x000f);
	    if (tmpChar >= 10) {
		low = (char) (('a' + tmpChar) - 10);
	    } else {
		low = (char) ('0' + tmpChar);
	    }
	    md5Str.append(low);
	}
	return md5Str.toString();
    }
}
