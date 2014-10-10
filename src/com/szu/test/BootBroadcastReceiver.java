package com.szu.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "BootBroadcastReceiver,开机自启动", Toast.LENGTH_SHORT).show();
		Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
		context.startActivity(appIntent);
	}

}
