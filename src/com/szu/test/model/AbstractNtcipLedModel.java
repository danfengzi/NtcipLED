package com.szu.test.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public abstract class AbstractNtcipLedModel implements Serializable {
	public final String TAG = "AbstractNtcipLedModel";
//	public final String DIG_KEY = "DZNTCIP".trim();
	private static final long serialVersionUID = -7804025900439152776L;

	//返回1表示解析正常
	abstract public int refresh(byte[] buffer);

	abstract public byte[] toBytes();

	abstract public String toString();

	abstract public void printf();

}
