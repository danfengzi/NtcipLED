package com.szu.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class MD5Util {

	public static byte[] Md5(byte[] from) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(from);
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// result = buf.toString();
			result = buf.toString().substring(8, 24);
			System.out.println("result: " + result);// 16位的加密
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block e.printStackTrace();
			Log.e("", e.toString());
		}
		return BytesUtil.getBytes(result);
	}
}
