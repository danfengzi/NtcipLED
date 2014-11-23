package com.szu.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.TypedValue;
import com.szu.test.connectivity.NtcipEventListener;

public class MainActivity extends Activity implements OnClickListener {
	private final String TAG = "MainActivity";
	NtcipConntroller conntroller;
	Context mContext;
	private TextView tv_hintShow;
	private Button btn_SysErr;
	private Button btn_ScreenErr;
	private Button btn_Settings;
	private Button btn_Register;

	HandlerThread workThread;
	EventHandler mHandler;

	Runnable udpRunnable;
	NtcipEventListener mEventListener;
	
	boolean isSendRegister = true;

	public class EventHandler extends Handler {
		public EventHandler(Looper looper) {
			// TODO Auto-generated constructor stub
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ContantType.MSG_RECEIVED:
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();

		tv_hintShow = (TextView) findViewById(R.id.text_show);
		btn_SysErr = (Button) findViewById(R.id.sys_error);
		btn_SysErr.setOnClickListener(this);
		btn_ScreenErr = (Button) findViewById(R.id.screen_error);
		btn_ScreenErr.setOnClickListener(this);
		btn_Settings = (Button) findViewById(R.id.settings);
		btn_Settings.setOnClickListener(this);
		btn_Register = (Button) findViewById(R.id.register);
		btn_Register.setOnClickListener(this);

		initCompotents();

		mHandler.post(udpRunnable);
		
		isSendRegister = true;
		
		new Thread(new Runnable(){
			public void run(){
				while(isSendRegister){
					try{
						Thread.sleep(1000 * 45);	
					}catch(Exception e){
						e.printStackTrace();
					}
					if(conntroller != null){
						conntroller.registerRequest();
					}
				}
			}
		}).start();
	}

	protected void initCompotents() {

		workThread = new HandlerThread(TAG);
		workThread.start();
		mHandler = new EventHandler(workThread.getLooper());
		mEventListener = new NtcipEventListener() {

			@Override
			public void onScreenUpdate(final String hint, final int textColor, final int textSize, final Drawable drawable) {
				tv_hintShow.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						tv_hintShow.setTextColor(textColor);
						tv_hintShow.setText(hint);							
						Integer iSize = new Integer(textSize);	
						tv_hintShow.setTextSize(TypedValue.COMPLEX_UNIT_PT,iSize.floatValue());
						
						if (drawable != null) {
							drawable.setBounds(0, 0, 128,128);
						}
						tv_hintShow.setCompoundDrawables(drawable, null, null, null);
					}
				});
			}

			@Override
			public void onResultShow(final String result) {
				// TODO Auto-generated method stub
				MainActivity.this.runOnUiThread(new Runnable(){
					public void run(){
						Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
					}
				});
			}
		};

		udpRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				conntroller = new NtcipConntroller(getApplicationContext(), mEventListener);
				conntroller.startUp();
			}
		};
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (conntroller != null) {
			conntroller.onServerConfigureChanged();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isSendRegister = false;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sys_error:
			// TODO:响应系统错误按钮点击事件
			if(conntroller != null){
				conntroller.sendSysErrorRequest();
			}
			break;
		case R.id.screen_error:
			// TODO:响应系统错误按钮点击事件
			if(conntroller != null){
				conntroller.sendScreenErrorRequest();
			}
			break;
		case R.id.settings:
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			MainActivity.this.startActivityForResult(intent, Activity.RESULT_FIRST_USER);
			break;
		case R.id.register:
			if(conntroller != null){
				conntroller.registerRequest();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case SettingsActivity.RESULT_SAVE_SUCCESS:
			if(conntroller != null){
				conntroller.registerRequest();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(mContext, SettingsActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
}
