package com.fwg.asservice.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaHashConfig {
	public String hashSHA1(String strValue) {
		return sHAhashing("SHA-1", strValue);
	}

	public String hashSHA256(String strValue) {
		return sHAhashing("SHA-256", strValue);
	}

	public String hashSHA348(String strValue) {
		return sHAhashing("SHA-384", strValue);
	}

	public String hashSHA512(String strValue) {
		return sHAhashing("SHA-512", strValue);
	}

	public String sHAhashing(String distinguished, String strValue) {
		MessageDigest md;
		try {

			md = MessageDigest.getInstance(distinguished);
			md.update(strValue.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

//	public static void main(String[] args) {
//		System.out.println("hash:"+new ShaHashConfig().hashSHA1("password"));
//	}
}
