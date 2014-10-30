package com.szu.test.connectivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.szu.test.ContantType;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.util.Log;

public class UdpHelper {
	private final String TAG = getClass().getSimpleName();

	// private final String SERVER_ADDRESS = "127.0.0.1";
	// private final int SERVER_PORT = 1234;

	Context mContext;
	UdpEventListener mEventListener;
	WifiManager manager;

	public Boolean IsThreadDisable = false;// 指示监听线程是否终止

	UdpListenerRunnable listenerRunnable;
	Thread recvThread;
	boolean isReceiveThreadRunning = false;

	DatagramSocket senderSocket = null;
	InetAddress serverAddress = null;
	static String ipAddress = null;
	static int serverPort = 0;

	public UdpHelper(Context context, WifiManager manager, UdpEventListener listener) {
		this.mContext = context;
		this.manager = manager;
		this.mEventListener = listener;
		isReceiveThreadRunning = false;

	}

	public void prepare() {
		WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		listenerRunnable = new UdpListenerRunnable(manager);
		recvThread = new Thread(listenerRunnable);
		prepareSender();
	}

	protected void loadConfig() {
		SharedPreferences mSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(),
				Context.MODE_PRIVATE);
		ipAddress = mSharedPreferences.getString(ContantType.KEY_SERVER_ADDRESS, "");
		serverPort = mSharedPreferences.getInt(ContantType.KEY_SERVER_PORT, ContantType.DEFAULT_PORT);
	}

	public void reLoadConfig() {
		loadConfig();
	}

	protected void prepareSender() {
		try {
			senderSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			Log.e(TAG, new StringBuilder("SocketException: ").append(e).toString());
		}

		try {
			serverAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			Log.e(TAG, new StringBuilder("UnknownHostException: ").append(e).toString());
			e.printStackTrace();
		}
	}

	public void startListen() {
		Log.d(TAG, "startListen");
		if (!isReceiveThreadRunning) {
			recvThread.start();
		}
	}

	public void send(byte[] buffer) {
		if ((senderSocket == null) || (serverAddress == null)) {
			Log.e(TAG, "(senderSocket == null) || (serverAddress == null)");
			mEventListener.onError(-1);
		}

		int msg_length = buffer.length;

		DatagramPacket p = new DatagramPacket(buffer, msg_length, serverAddress, serverPort);
		try {

			senderSocket.send(p);
			// senderSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, new StringBuilder("Error happened when send buffer: ").append(e).toString());
		}
	}

	public class UdpListenerRunnable implements Runnable {
		private WifiManager.MulticastLock lock;

		public UdpListenerRunnable(WifiManager manager) {
			this.lock = manager.createMulticastLock("UDPwifi");
		}

		@Override
		public void run() {
			// 接收的字节大小，客户端发送的数据不能超过这个大小
			byte[] message = new byte[100 * 1024];
			try {
				// 建立Socket连接
				DatagramSocket datagramSocket = new DatagramSocket(serverPort, serverAddress);
				datagramSocket.setBroadcast(true);
				DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
				try {
					while (!IsThreadDisable) {
						isReceiveThreadRunning = true;
						// 准备接收数据
						Log.d("UDP Demo", "准备接收数据");
						this.lock.acquire();

						datagramSocket.receive(datagramPacket);
						if (mEventListener != null) {
							mEventListener.onDataReceive(datagramPacket.getData());
						}
						this.lock.release();
					}
				} catch (IOException e) {// IOException
					e.printStackTrace();
				}
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

	}
}
