package com.szu.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.szu.test.Configuration;

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
	
	public static boolean isIllegal(byte[] buffer, byte[] msgDig) {
		//校验
		byte[] key = new byte[32];
		System.arraycopy(key, 0, msgDig, 0, 16);
		
		byte[] serverKey = new byte[32];
		byte[] serverBytes = BytesUtil.getBytes(Configuration.getInstance().getServerKeyConfig());
		System.arraycopy(serverBytes, 0, serverKey, 0, serverBytes.length);
		System.arraycopy(serverKey, 0, buffer, buffer.length - 32, 32);
		byte[] result = MD5Util.Md5(buffer);

		String strKey = BytesUtil.getString(key, "GBK");
		String strResult = BytesUtil.getString(result, "GBK");
		if(strKey.equals(strResult)) {
			return true;
		} else {
			return false;
		}
	}
}
