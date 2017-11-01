package com.feicuiedu.utils.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 加密和解密算法
 *
 * @author Administrator
 */
public class AES {
	
	private static final String PASSWORD = "wugengweiwu1209";
	// private IvParameterSpec ivParamSpec = new IvParameterSpec("1234567890123456".getBytes());
	
	/**
	 * 获取解密后的字符串
	 *
	 * @param content
	 * @return
	 */
	public static String RevertAESCode(String content) {
		byte[] decryptFrom = parseHexStr2Byte(content);
		byte[] decryptResult = decrypt(decryptFrom, PASSWORD);
		String decryptString = new String(decryptResult);
		return decryptString;
	}
	
	/**
	 * 将十六进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		else {
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; i++) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1),
											16);
				int low = Integer.parseInt(
						hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}
	}
	
	/**
	 * 解密
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	private static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			
			// 密钥补位
			int plus= 16-password.length();
			byte[] data = password.getBytes("utf-8");
			byte[] raw = new byte[16];
			byte[] plusbyte={ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
			for(int i=0;i<16;i++)
			{
				if (data.length > i)
					raw[i] = data[i];
				else
					raw[i] = plusbyte[plus];
			}
			
			kgen.init(128, new SecureRandom(raw));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/** 创建密码器 **/
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			/** 初始化密码器 **/
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
		}
		catch (Exception e) {
			System.out.println("出错了:" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取加密后的字符串
	 *
	 * @param content
	 * @return
	 */
	public static String GetAESCode(String content) {
		byte[] encryptResult = encrypt(content, PASSWORD);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		return encryptResultStr;
	}
	
	/**
	 * 加密
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	private static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			
			// 密钥补位
			int plus = 16 - password.length();
			byte[] data = password.getBytes("utf-8");
			byte[] raw = new byte[16];
			byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
			for (int i = 0; i < 16; i++) {
				if (data.length > i) {
					raw[i] = data[i];
				}
				else {
					raw[i] = plusbyte[plus];
				}
			}
			
			kgen.init(128, new SecureRandom(raw));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/** 创建密码器 **/
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			//Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
			byte[] byteContent = content.getBytes("utf-8");
			/** 初始化密码器 **/
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] result = cipher.doFinal(byteContent);
			return result;
		}
		catch (Exception e) {
			System.out.println("出错了:" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 将二进制转换成十六进制
	 *
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			//System.out.print(buf[i]);
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	/**
	 * 加密和解密
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		/** 数据初始化 **/
		String content = "M1606290092482";
		String password = "wugengweiwu1209";
		/** 加密 **/
		System.out.println("加密前：" + content);
		String encryptResultStr = GetAESCode(content, password);
		/*encryptResultStr = GetAESCode(encryptResultStr, password);
		encryptResultStr = GetAESCode(encryptResultStr, password);*/
		System.out.println("加密后：" + encryptResultStr);
		/** 解密 **/
		String decryptString = RevertAESCode(encryptResultStr, password);
		System.out.println("解密后：" + decryptString);
		
	}
	
	/**
	 * 获取加密后的字符串
	 *
	 * @param content
	 * @param passcode
	 * @return
	 */
	public static String GetAESCode(String content, String passcode) {
		byte[] encryptResult = encrypt(content, passcode);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		return encryptResultStr;
	}
	
	/**
	 * 获取解密后的字符串
	 *
	 * @param content
	 * @param passcode
	 * @return
	 */
	public static String RevertAESCode(String content, String passcode) {
		byte[] decryptFrom = parseHexStr2Byte(content);
		byte[] decryptResult = decrypt(decryptFrom, passcode);
		String decryptString = new String(decryptResult);
		return decryptString;
	}
}