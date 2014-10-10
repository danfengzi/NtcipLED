package com.szu.test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.szu.test.connectivity.NtcipEventListener;

public class MainActivity extends Activity implements OnClickListener, NtcipEventListener {

	NtcipConntroller conntroller;

	private TextView tv_hintShow;
	private Button btn_SysErr;
	private Button btn_ScreenErr;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ContantType.MSG_RECEIVED:

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_hintShow = (TextView) findViewById(R.id.text_show);
		btn_SysErr = (Button) findViewById(R.id.sys_error);
		btn_SysErr.setOnClickListener(this);
		btn_ScreenErr = (Button) findViewById(R.id.screen_error);
		btn_ScreenErr.setOnClickListener(this);

		conntroller = new NtcipConntroller(getApplicationContext(), this);
		conntroller.startUp();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sys_error:
			// TODO:响应系统错误按钮点击事件
			conntroller.sendSysErrorRequest();
			break;
		case R.id.screen_error:
			// TODO:响应系统错误按钮点击事件
			conntroller.sendScreenErrorRequest();
			break;
		default:
			break;
		}
	}

	@Override
	public void onScreenUpdate(final String hint, final int textColor, final int textSize, final Drawable drawable) {
		// TODO Auto-generated method stub
		MainActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				tv_hintShow.setTextColor(textColor);
				tv_hintShow.setText(hint);
				if (drawable != null) {
					tv_hintShow.setCompoundDrawables(drawable, null, null, null);
				}
			}
		});
	}
}
