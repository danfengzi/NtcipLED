package com.szu.test.connectivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UpdHelper2 {
	public static final UpdHelper2 Instance = new UpdHelper2();

	private UpdHelper2() {
	}

	public static interface onRevUDPDataController {
		public void onRevUDPData(byte[] data, int offset, int length);
	}

	private int localSvrPort = 13131;
	private int remotePort = 10201;

	private DatagramSocket client = null;
	private DatagramSocket server = null;
	private UdpEventListener listener;
	private String remoteIP;
	private volatile boolean isReading = true;

	public boolean deinit() {
		if (client != null) {
			client.close();
		}

		if (server != null) {
			server.close();
		}
		client = null;
		server = null;
		this.listener = null;
		return true;
	}

	public boolean reInit(UdpEventListener listener, int localSvrPort, int remotePort, String remoteIP) {
		deinit();
		return init(listener, localSvrPort, remotePort, remoteIP);
	}

	public boolean init(UdpEventListener listener, int localSvrPort, int remotePort, String remoteIP) {
		if (client != null) {
			return false;
		}

		if (null == this.listener || localSvrPort < 1024 || remotePort < 1024 || localSvrPort > 65535
				|| remotePort > 65535 || null == remoteIP || remoteIP.length() > "192.168.168.168".length()) {
			return false;
		}
		this.listener = listener;
		this.localSvrPort = localSvrPort;
		this.remotePort = remotePort;
		this.remoteIP = new String(remoteIP);

		try {
			client = new DatagramSocket(this.localSvrPort - 1);
			server = new DatagramSocket(this.localSvrPort);
			isReading = true;
		} catch (SocketException e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}

			if (server != null) {
				server.close();
			}
			client = null;
			server = null;

			return false;
		}
		return true;
	}

	public boolean sendToSvr(byte[] data) {
		if (client != null) {
			DatagramPacket pack;
			try {
				pack = new DatagramPacket(data, data.length, InetAddress.getByName(remoteIP), remotePort);
				client.send(pack);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return false;
		}
		return true;
	}

	public boolean stopReadFromRemote() {
		isReading = false;
		return true;
	}

	public boolean beginReadFromRemote() {
		try {
			while (isReading) {
				byte[] message = new byte[100 * 1024];
				DatagramPacket packet = new DatagramPacket(message, message.length);
				server.receive(packet);
				listener.onDataReceive(packet.getData());
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			// controller.onRevUDPData(null, 0, 0);
			return false;
		}
	}
}
