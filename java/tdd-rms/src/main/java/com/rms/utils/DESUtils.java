package com.rms.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {
	
	public static final String AES_MICHI = "99cAk5D5E2EEccyz";
	//public static final String AES_MICHI_MIN = "99cAk5D5";
	public static final String AES_MICHI_MIN = "tdddes01";
	public static final String ERROR_CODE="j8XyDEF9YGXeumCyzRgt6vR8XFepOM6PBVuhnFdBlRJaTuV42bYn2KI/lZhp7zV18KWYxkahsXuO";
	
	private byte[] desKey;

	public DESUtils(String desKey) {
		this.desKey = desKey.getBytes();
	}

	public byte[] desEncrypt(byte[] plainText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	public byte[] desDecrypt(byte[] encryptText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	public String encrypt(String input) throws Exception {
		return base64Encode(desEncrypt(input.getBytes()));
	}

	public String decrypt(String input) throws Exception {
		byte[] result = base64Decode(input);
		return new String(desDecrypt(result));
	}

	public static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		BASE64Encoder b = new sun.misc.BASE64Encoder();
		return b.encode(s);
	}

	public static byte[] base64Decode(String s) throws IOException {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(s);
		return b;
	}

	public static void main(String[] args) throws Exception {
		String key = DESUtils.AES_MICHI_MIN;
		String input = "{'mobile':'15069119959','message':'欢迎注册桃多多会员，您的手机验证码为'.$code.'。请您在十分钟之内输入；如非本人或授权操作请忽略[桃多多]'}";
		DESUtils crypt = new DESUtils(key);
		String tt = crypt.encrypt(crypt.encrypt(crypt.encrypt(input)));
		System.out.println("Encode:"
				+ crypt.encrypt(crypt.encrypt(crypt.encrypt(input))));
		System.out.println("Decode:"
				+ crypt.decrypt(crypt.decrypt(crypt.decrypt(tt))));
		
		String tt1 = crypt.encrypt(input);
		System.out.println("Encode:"
				+ crypt.encrypt(input));
		System.out.println("Decode:"
				+ crypt.decrypt(tt1));
	}
}