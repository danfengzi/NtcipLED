package com.szu.test.connectivity;

public interface UdpEventListener {

	abstract public void onDataReceive(byte[] buffer);
	abstract public void onError(int errorCode);
}
