package com.szu.test;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class LedApplication extends Application {
	private final String TAG = getClass().getSimpleName();
	private static LedApplication instance;

	public static LedApplication getInstance() {
		return instance;
	}

	public Context getAppContext() {
		return getApplicationContext();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "onCreate");
		}
	}
}
