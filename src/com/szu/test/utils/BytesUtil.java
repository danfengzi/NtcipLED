/**
 * @file_name : BytesUtil.java
 * @package_name : com.util
 * @Data : 2013-10-25 下午4:44:16
 * @author : zhangliutao
 * @Description : TODO
 * 
 *              Copyright (c) 2013, DJI All Rights Reserved.
 */

package com.szu.test.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class BytesUtil {
	public static byte[] getBytes(short data) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		return bytes;
	}

	public static byte[] getBytes(char data) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (data);
		bytes[1] = (byte) (data >> 8);
		return bytes;
	}

	public static byte[] getBytes(int data) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		bytes[2] = (byte) ((data & 0xff0000) >> 16);
		bytes[3] = (byte) ((data & 0xff000000) >> 24);
		return bytes;
	}

	public static byte[] getBytes(long data) {
		byte[] bytes = new byte[8];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data >> 8) & 0xff);
		bytes[2] = (byte) ((data >> 16) & 0xff);
		bytes[3] = (byte) ((data >> 24) & 0xff);
		bytes[4] = (byte) ((data >> 32) & 0xff);
		bytes[5] = (byte) ((data >> 40) & 0xff);
		bytes[6] = (byte) ((data >> 48) & 0xff);
		bytes[7] = (byte) ((data >> 56) & 0xff);
		return bytes;
	}

	public static byte[] getBytes(float data) {
		int intBits = Float.floatToIntBits(data);
		return getBytes(intBits);
	}

	public static byte[] getBytes(double data) {
		long intBits = Double.doubleToLongBits(data);
		return getBytes(intBits);
	}

	public static byte[] getBytes(String data, String charsetName) {
		Charset charset = Charset.forName(charsetName);
		return data.getBytes(charset);
	}

	public static byte[] getBytes(String data) {
		return getBytes(data, "GBK");
	}

	public static byte[] getBytes(Serializable obj) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		out.writeObject(obj);
		out.flush();
		byte[] bytes = bout.toByteArray();
		bout.close();
		out.close();
		return bytes;
	}

	public static int sizeof(Serializable obj) throws IOException {
		return getBytes(obj).length;
	}

	public static Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		Object obj = oi.readObject();
		bi.close();
		oi.close();
		return obj;
	}

	public static Object getObject(ByteBuffer byteBuffer) throws ClassNotFoundException, IOException {
		InputStream input = new ByteArrayInputStream(byteBuffer.array());
		ObjectInputStream oi = new ObjectInputStream(input);
		Object obj = oi.readObject();
		input.close();
		oi.close();
		byteBuffer.clear();
		return obj;
	}

	public static short getShort(byte[] bytes) {
		return (short) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
	}

	public static char getChar(byte[] bytes) {
		return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
	}

	public static char getChar(byte b) {
		return (char) (0xff & b);
	}

	public static int getInt(byte[] bytes) {
		return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16))
				| (0xff000000 & (bytes[3] << 24));
	}

	public static int getInt(byte b) {
		return (0xff & b);
	}

	public static long getLong(byte[] bytes) {
		return (0xffL & (long) bytes[0]) | (0xff00L & ((long) bytes[1] << 8)) | (0xff0000L & ((long) bytes[2] << 16))
				| (0xff000000L & ((long) bytes[3] << 24)) | (0xff00000000L & ((long) bytes[4] << 32))
				| (0xff0000000000L & ((long) bytes[5] << 40)) | (0xff000000000000L & ((long) bytes[6] << 48))
				| (0xff00000000000000L & ((long) bytes[7] << 56));
	}

	public static float getFloat(byte[] bytes) {
		return Float.intBitsToFloat(getInt(bytes));
	}

	public static double getDouble(byte[] bytes) {
		long l = getLong(bytes);
		return Double.longBitsToDouble(l);
	}

	/**
	 * �ֽ����鵽���͵�ת��
	 * 
	 * @param b
	 * @return
	 * @throws DataTranslateException
	 */
	public static long byteToLong(byte[] b) {
		if (b.length != 8) {
			return -1;
		}
		return ((((long) b[0] & 0xff) << 56) | (((long) b[1] & 0xff) << 48) | (((long) b[2] & 0xff) << 40)
				| (((long) b[3] & 0xff) << 32) | (((long) b[4] & 0xff) << 24) | (((long) b[5] & 0xff) << 16)
				| (((long) b[6] & 0xff) << 8) | (((long) b[7] & 0xff) << 0));
	}

	/**
	 * �ֽ����鵽doubleת��
	 * 
	 * @param b
	 * @return
	 * @throws DataTranslateException
	 */
	public static double byteToDouble(byte[] b) {
		if (b.length != 8) {
			return -1;
		}
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;

		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;

		l |= ((long) b[7] << 56);

		return Double.longBitsToDouble(l);
	}

	public static String getString(byte[] bytes, String charsetName) {
		return new String(bytes, Charset.forName(charsetName));
	}

	public static String getString(byte[] bytes) {
		return getString(bytes, "GBK");
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String int2hex(int i) {
		String h = "";

		h = Integer.toHexString(i);
		if (h.length() == 1) {
			h = "0" + h;
		}
		return h;
	}

	public static String byte2hex(byte[] buffer) {
		String h = "";

		for (int i = 0; i < buffer.length; i++) {
			String temp = Integer.toHexString(buffer[i] & 0xFF);
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			h = h + " " + temp;
		}

		return h;
	}

	public static String byte2hexWithoutSpace(byte[] buffer) {
		String h = "";

		for (int i = 0; i < buffer.length; i++) {
			String temp = Integer.toHexString(buffer[i] & 0xFF);
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			h = h + temp;
		}

		return h;
	}

	public static String byte2hex(byte[] buffer, int len) {
		String h = "";

		for (int i = 0; i < len; i++) {
			String temp = Integer.toHexString(buffer[i] & 0xFF);
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			h = h + " " + temp;
		}

		return h;
	}

	public static String byte2hex(byte buffer) {
		String h = Integer.toHexString(buffer & 0xFF);
		if (h.length() == 1) {
			h = "0" + h;
		}
		return h;
	}

	/**
	 * 把byte数组转化�?进制字符�?
	 * 
	 * @param bArr
	 * @return
	 */
	public static String getBinaryStrFromByteArr(byte[] bArr) {
		String result = "";
		for (byte b : bArr) {
			result += getBinaryStrFromByte(b);
		}
		return result;
	}

	/**
	 * 把byte转化�?进制字符�?
	 * 
	 * @param b
	 * @return
	 */
	public static String getBinaryStrFromByte(byte b) {
		String result = "";
		byte a = b;
		for (int i = 0; i < 8; i++) {
			result = (a % 2) + result;
			a = (byte) (a >> 1);
		}
		return result;
	}

	public static byte[] readBytes(byte[] source, int from, int length) {
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = source[from + i];
		}
		return result;
	}

	public static byte[] arrayComb(byte[] prep, byte[] after) {
		byte[] result = new byte[prep.length + after.length];
		System.arraycopy(prep, 0, result, 0, prep.length);
		System.arraycopy(after, 0, result, prep.length, after.length);
		return result;
	}

	public static byte[] arrayApend(byte[] prep, byte after) {
		byte[] result = new byte[prep.length + 1];
		System.arraycopy(prep, 0, result, 0, prep.length);
		result[prep.length] = after;
		return result;
	}

	public static byte[] arraycopy(byte[] from, byte[] to) {
		System.arraycopy(from, 0, to, 0, from.length);
		return to;
	}

	/**
	* 16位 convert big-endian to little-endian 
	* 大字节序 -> 小字节序
	* */
	public static final short bigEndian2LittleEndian16(short x) {
		return (short) ((x & 0xFF) << 8 | 0xFF & (x >> 8));
	}

	/**
	* 32位  convert big to little-edian 
	* 大字节序 -> 小字节序
	* */
	public static final int bigEndian2LittleEndian32(int x) {
		return (x & 0xFF) << 24 | (0xFF & x >> 8) << 16 | (0xFF & x >> 16) << 8 | (0xFF & x >> 24);
	}

	/**
	* 小字节序
	* @param intValue
	* @return
	*/
	public static byte[] int2Byte_LittleEndian(int intValue) {// int--->byte(低--高)
		byte[] b = new byte[4];
		b[0] = (byte) (intValue & 0xff);
		b[1] = (byte) ((intValue >> 8) & 0xff);
		b[2] = (byte) ((intValue >> 16) & 0xff);
		b[3] = (byte) ((intValue >> 24) & 0xff);
		return b;
	}

	/**
	* 16位 小字节序
	* @param intValue
	* @return
	*/
	public static byte[] short2Byte_LittleEndian(short intValue) {// short--->byte(低--高)
		byte[] b = new byte[2];
		b[0] = (byte) (intValue & 0xff);
		b[1] = (byte) ((intValue >> 8) & 0xff);
		return b;
	}

	/**
	* 小字节序 byte[]--->int
	* @param b
	* @param pos 位置
	* @return
	*/
	public static int byte2int_LittleEndian(byte b[], int pos) {// byte(低---高)--->int
		int b0 = (b[pos] & 0xff);
		int b1 = (b[pos + 1] & 0xff) << 8;
		int b2 = (b[pos + 2] & 0xff) << 16;
		int b3 = (b[pos + 3] & 0xff) << 24;
		int result = (b0 | b1 | b2 | b3);
		return result;
	}

	/**
	* 64位 小字节序
	* @param value
	* @return
	*/
	public static byte[] long2Byte_LittleEndian(long value) {// long--->byte(低--高)
		byte[] b = new byte[8];
		b[0] = (byte) (value & 0xff);
		b[1] = (byte) ((value >> 8) & 0xff);
		b[2] = (byte) ((value >> 16) & 0xff);
		b[3] = (byte) ((value >> 24) & 0xff);
		b[4] = (byte) ((value >> 32) & 0xff);
		b[5] = (byte) ((value >> 40) & 0xff);
		b[6] = (byte) ((value >> 48) & 0xff);
		b[7] = (byte) ((value >> 56) & 0xff);
		return b;
	}

	/**
	* 小字节序 byte[]--->long
	* @param b
	* @param pos 位置
	* @return
	*/
	public static long byte2long_LittleEndian(byte b[], int pos) {// byte(低---高)
		long b0 = (b[pos] & 0xff);
		long b1 = (b[pos + 1] & 0xff) << 8;
		long b2 = (b[pos + 2] & 0xff) << 16;
		long b3 = (b[pos + 3] & 0xff) << 24;
		long b4 = (b[pos + 4] & 0xff) << 32;
		long b5 = (b[pos + 5] & 0xff) << 40;
		long b6 = (b[pos + 6] & 0xff) << 48;
		long b7 = (b[pos + 7] & 0xff) << 56;
		long result = (b0 | b1 | b2 | b3 | b4 | b5 | b6 | b7);
		return result;
	}

	/**
	* 小字节序 byte[]--->short
	* @param b
	* @param pos 位置
	* @return
	*/
	public static short byte2short_LittleEndian(byte b[], int pos) {// byte(低---高)
		short b0 = (short) (b[pos] & 0xff);
		short b1 = (short) ((b[pos + 1] & 0xff) << 8);
		return (short) (b0 | b1);
	}

	/**
	*16位  convert big-endian to little-endian
	* 大字节序 -> 小字节序
	* */
	public static final short littleEndian2BigEndian16(short x) {
		return (short) ((x & 0xFF) << 8 | 0xFF & (x >> 8));
	}

	/**
	* 32位 convert little-edian to big-edian
	* 小字节序 -> 大字节序
	* */
	public static final int littleEndian2BigEndian32(int x) {
		return bigEndian2LittleEndian32(x);
	}

	/**
	*32位  大字节序
	* @param intValue
	* @return
	*/
	public static byte[] int2Byte_BigEndian(int intValue) {// int--->byte(高--低)
		byte[] b = new byte[4];
		b[0] = (byte) (intValue >> 24 & 0xff);
		b[1] = (byte) ((intValue >> 16) & 0xff);
		b[2] = (byte) ((intValue >> 8) & 0xff);
		b[3] = (byte) ((intValue) & 0xff);
		return b;
	}

	/**
	*16位  大字节序
	* @param intValue
	* @return
	*/
	public static byte[] short2Byte_BigEndian(short intValue) {// short--->byte(高--低)
		byte[] b = new byte[2];
		b[0] = (byte) ((intValue >> 8) & 0xff);
		b[1] = (byte) ((intValue) & 0xff);
		return b;
	}

	/**
	 * 大字节序 byte[]---->int
	 * @param b
	 * @param pos
	 * @return
	 */
	public static int byte2int_BigEndian(byte b[], int pos) {// byte(高---低)--->int
		int b0 = (b[pos + 3] & 0xff);
		int b1 = (b[pos + 2] & 0xff) << 8;
		int b2 = (b[pos + 1] & 0xff) << 16;
		int b3 = (b[pos] & 0xff) << 24;
		int result = (b0 | b1 | b2 | b3);
		return result;
	}

	/**
	*64位  大字节序  long--->byte
	* @param value
	* @return
	*/
	public static byte[] long2Byte_BigEndian(long value) {// long--->byte(高--低)
		byte[] b = new byte[8];
		b[7] = (byte) (value & 0xff);
		b[6] = (byte) ((value >> 8) & 0xff);
		b[5] = (byte) ((value >> 16) & 0xff);
		b[4] = (byte) ((value >> 24) & 0xff);
		b[3] = (byte) ((value >> 32) & 0xff);
		b[2] = (byte) ((value >> 40) & 0xff);
		b[1] = (byte) ((value >> 48) & 0xff);
		b[0] = (byte) ((value >> 56) & 0xff);
		return b;
	}

	/**
	 * 大字节序 byte[]---->long
	 * @param b
	 * @param pos
	 * @return
	 */
	public static long byte2long_BigEndian(byte b[], int pos) {// byte(高---低)
		long b0 = (b[pos + 7] & 0xff);
		long b1 = (b[pos + 6] & 0xff) << 8;
		long b2 = (b[pos + 5] & 0xff) << 16;
		long b3 = (b[pos + 4] & 0xff) << 24;
		long b4 = (b[pos + 3] & 0xff) << 32;
		long b5 = (b[pos + 2] & 0xff) << 40;
		long b6 = (b[pos + 1] & 0xff) << 48;
		long b7 = (b[pos] & 0xff) << 56;
		long result = (b0 | b1 | b2 | b3 | b4 | b5 | b6 | b7);
		return result;
	}

	/**
	 * 大字节序 byte[]---->short
	 * @param b
	 * @param pos
	 * @return
	 */
	public static short byte2short_BigEndian(byte b[], int pos) {// byte(高---低)
		short b1 = (short) (b[pos + 1] & 0xff);
		short b0 = (short) ((b[pos] & 0xff) << 8);
		return (short) (b0 | b1);
	}

}
