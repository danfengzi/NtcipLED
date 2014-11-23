package com.szu.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	public static final int RESULT_SAVE_SUCCESS = 1000;

	private static final String PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	SharedPreferences mSharedPreferences;

	TextView screenIdTextView, keyTextView, ipAddressTextView, portTextView, screenKeyTextView;
	Button saveButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_settings);
		init();
	}

	protected void init() {
		screenIdTextView = (TextView) findViewById(R.id.screen_id);
		keyTextView = (TextView) findViewById(R.id.key);
		ipAddressTextView = (TextView) findViewById(R.id.server_ip_address);
		portTextView = (TextView) findViewById(R.id.server_port);
		screenKeyTextView = (TextView) findViewById(R.id.screen_key);
		
		saveButton = (Button) findViewById(R.id.setting_save);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(saveSettings()) {
					Toast.makeText(getApplicationContext(), "设置保存成功", Toast.LENGTH_SHORT).show();
					SettingsActivity.this.setResult(RESULT_SAVE_SUCCESS);
					SettingsActivity.this.finish();
				}
			}
		});
		mSharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		resumeSetting();
	}

	protected void resumeSetting() {
//		String screenIdString = mSharedPreferences.getString(ContantType.KEY_SCREEN_ID, ContantType.DEFAULT_SCREEN_ID);
//		String keyString = mSharedPreferences.getString(ContantType.KEY_SERVER_KEY, ContantType.DEFAULT_SERVER_KEY);
//		String addresString = mSharedPreferences.getString(ContantType.KEY_SERVER_ADDRESS, ContantType.DEFAULT_SERVER_ADDRESS);
//		int port = mSharedPreferences.getInt(ContantType.KEY_SERVER_PORT, ContantType.DEFAULT_SERVER_PORT);
//		String screenKeyString = mSharedPreferences.getString(ContantType.KEY_SCREEN_KEY, ContantType.DEFAULT_SCREEN_KEY);
		String screenIdString = Configuration.getInstance().getScreenIdConfig();
		String keyString = Configuration.getInstance().getServerKeyConfig();
		String addresString = Configuration.getInstance().getServerIpConfig();
		int port = Configuration.getInstance().getServerPortConfig();
		String screenKeyString = Configuration.getInstance().getScreenKeyConfig();
		
		screenIdTextView.setText(screenIdString);
		keyTextView.setText(keyString);
		ipAddressTextView.setText(addresString);
		portTextView.setText(String.valueOf(port));
		screenKeyTextView.setText(screenKeyString);
	}

	protected boolean saveSettings() {
		String screenIdString = screenIdTextView.getText().toString();
		String keyString = keyTextView.getText().toString();
		String addresString = ipAddressTextView.getText().toString();

		if (!validateIpAddress(addresString)) {
			ipAddressTextView.setError("请输入合法IP地址");
			return false;
		}

		int port = Integer.parseInt(portTextView.getText().toString());

		if ((port < 0) || (port > 65535)) {
			portTextView.setError("请输入合法端口号");
			return false;
		}

		String screenKey = screenKeyTextView.getText().toString();
		
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(ContantType.KEY_SCREEN_ID, screenIdString);
		editor.putString(ContantType.KEY_SERVER_KEY, keyString);
		editor.putString(ContantType.KEY_SERVER_ADDRESS, addresString);
		editor.putInt(ContantType.KEY_SERVER_PORT, port);
		editor.putString(ContantType.KEY_SCREEN_KEY, screenKey);
		editor.apply();
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public boolean validateIpAddress(final String ip) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
}
