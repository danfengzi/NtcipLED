package com.szu.test;

import android.content.Context;
import android.content.SharedPreferences;

public class Configuration {

	private static Configuration INSTANCE;
	private static Object INSTANCE_LOCK = new Object();
	Context mContext;
	SharedPreferences mSharedPreferences;

	public Configuration() {
		mContext = LedApplication.getInstance().getAppContext();
		mSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
	}

	public static Configuration getInstance() {

		if (INSTANCE == null)
			synchronized (INSTANCE_LOCK) {
				if (INSTANCE == null) {
					INSTANCE = new Configuration();
				}
			}

		return INSTANCE;
	}

	public String getScreenIdConfig() {
		return mSharedPreferences.getString(ContantType.KEY_SCREEN_ID, "");
	}

	public String getServerKeyConfig() {
		return mSharedPreferences.getString(ContantType.KEY_SERVER_KEY, ContantType.DEFAULT_SERVER_KEY);
	}

	public String getServerIpConfig() {
		return mSharedPreferences.getString(ContantType.KEY_SERVER_ADDRESS, "");
	}

	public int getServerPortConfig() {
		return mSharedPreferences.getInt(ContantType.KEY_SERVER_PORT, ContantType.DEFAULT_PORT);
	}

	public void saveStringConfig(String key, String value) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, value);
		editor.apply();

	}

	public void saveIntConfig(String key, int value) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(key, value);
		editor.apply();
	}
}
