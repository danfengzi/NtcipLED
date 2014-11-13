package com.szu.test.connectivity;

import android.graphics.drawable.Drawable;

public interface NtcipEventListener {

	public void onScreenUpdate(String hint, int textColor, int textSize, Drawable drawable);
	public void onResultShow(String result);
}
