package com.szu.test.utils;

import com.szu.test.BuildConfig;

import android.util.Log;

public class D {

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		Log.e(tag, msg);

	}

	public static void i(String tag, String msg) {
		Log.i(tag, msg);

	}
}
