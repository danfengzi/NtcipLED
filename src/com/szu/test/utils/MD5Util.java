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
		System.arraycopy(msgDig, 0, key, 0, 16);
		
		//使用服务器密钥
		byte[] serverKey = new byte[32];
		byte[] serverBytes = BytesUtil.getBytes(Configuration.getInstance().getServerKeyConfig());
		System.arraycopy(serverBytes, 0, serverKey, 0, serverBytes.length);
		
		
		System.arraycopy(serverKey, 0, buffer, BytesUtil.byte2int_BigEndian(buffer, 4) - 32, 32);
		
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
