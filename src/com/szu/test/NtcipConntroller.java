package com.szu.test;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.szu.test.connectivity.NtcipEventListener;
import com.szu.test.connectivity.UdpEventListener;
import com.szu.test.connectivity.UdpHelper;
import com.szu.test.model.CNTCIPPacketHeader;
import com.szu.test.model.NtcipLedAck;
import com.szu.test.model.NtcipLedControlReq;
import com.szu.test.model.NtcipLedFaultNotifyReq;
import com.szu.test.model.NtcipLedMsgdisReq;
import com.szu.test.model.NtcipLedRegReq;
import com.szu.test.utils.BytesUtil;
import com.szu.test.utils.D;

public class NtcipConntroller implements UdpEventListener {
	private final String TAG = getClass().getSimpleName();
	UdpHelper udpHelper;
	Context mContext;
	WifiManager wifiManager;
	NtcipEventListener listener;

	SendingDataTask sendThread;
	private DataSendHandler mSendHandler;
	private HandlerThread mReceiverWorker;

	private static final class DataSendHandler extends Handler {

		public DataSendHandler(Looper looper) {
			super(looper);
		}
	}

	public NtcipConntroller(Context context, NtcipEventListener listener) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.listener = listener;
		init();
	}

	protected void init() {
		sendThread = new SendingDataTask();
		mReceiverWorker = new HandlerThread("ReceiverWorker");
		mReceiverWorker.start();
		if (mReceiverWorker.getLooper() != null) {
			mSendHandler = new DataSendHandler(mReceiverWorker.getLooper());
		}
	}

	public void startUp() {
		wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		udpHelper = new UdpHelper(mContext, wifiManager, this);
		udpHelper.prepare();
		udpHelper.startListen();
	}

	public void onServerConfigureChanged(){
		if (udpHelper != null) {
			udpHelper.reLoadConfig();
		}
	}
	@Override
	public void onDataReceive(byte[] buffer) {
		// TODO Auto-generated method stub
		handleIncommingData(buffer);
	}

	@Override
	public void onError(int errorCode) {
		// TODO Auto-generated method stub

	}

	public void handleIncommingData(byte[] buffer) {
		CNTCIPPacketHeader header = new CNTCIPPacketHeader();
		header.refresh(buffer);
		switch (header.getPacketType()) {
		case ContantType.IV_LED_MSGDIS_REQ:
			// 收到IV_LED_MSGDIS_REQ请求
			handleLedMsgdisRequest(buffer);
			break;
		case ContantType.IV_LED_CONTROL_REQ:
			// 收到IV_LED_CONTROL_REQ请求
			handleLedControlRequest(buffer);
			break;
		case ContantType.IV_LED_FAULTNOTIFY_ACK:
			// 收到IV_LED_FAULTNOTIFY_REQ确认
			handleLedFaultNotityAck(buffer);
			break;
		case ContantType.IV_LED_REG_ACK:
			// 收到IV_LED_REG_ACK确认
			handleLedRegAck(buffer);
			break;
		default:
			D.e(TAG, "unkown message type");
			break;
		}
	}

	private void handleLedRegAck(byte[] buffer) {
		// TODO: on NTCIP_LED_REG_ACK received
		NtcipLedAck response = new NtcipLedAck();
		if(response.refresh(buffer) == 1) {
			showResult("注册校验成功");
		} else {
			showResult("注册校验失败");
		}
		response.printf();
	}

	private void handleLedFaultNotityAck(byte[] buffer) {
		// TODO: on NTCIP_LED_FAULTNOTIFY_ACK received
		NtcipLedAck responseAck = new NtcipLedAck();
		if(responseAck.refresh(buffer) == 1) {
			showResult("错误上报校验成功");
		} else {
			showResult("错误上报校验失败");
		}
		responseAck.printf();
	}

	private void handleLedControlRequest(byte[] buffer) {
		// TODO Auto-generated method stub
		NtcipLedControlReq request = new NtcipLedControlReq();
		if(request.refresh(buffer) == 1) {
			int controlCode = request.getControlCode();
			onCtrollerCodeReceived(controlCode);
			showResult("控制指令校验成功--指令：" + controlCode);
			// TODO: 回Ack给服务器
			responseAck(ContantType.IV_LED_CONTROL_ACK, 1);
		} else {
			showResult("控制指令校验失败");
			responseAck(ContantType.IV_LED_CONTROL_ACK, 0);
		}
	}

	private void onCtrollerCodeReceived(int controlCode) {
		// TODO 执行控指令

	}

	private void handleLedMsgdisRequest(byte[] buffer) {
		// TODO Auto-generated method stub
		NtcipLedMsgdisReq request = new NtcipLedMsgdisReq();
		if(request.refresh(buffer) == 1) {
			showResult("显示指令校验成功");
			String textString = request.getTxtMString();
			int textColor = getTextColor(request.getTxtColor());
			int textSize = request.getTxtSize();
			Drawable drawable = request.getMapImage().getImageDrawable();
	
			if ((listener != null) && (drawable != null)) {
				// 回调给界面显示
				listener.onScreenUpdate(textString, textColor, textSize, drawable);
			}
			// TODO: 回Ack给服务器
			responseAck(ContantType.IV_LED_MSGDIS_ACK, 1);
		} else {
			showResult("显示指令校验失败");
			// TODO: 回Ack给服务器
			responseAck(ContantType.IV_LED_MSGDIS_ACK, 0);
		}
		request.printf();
	}
	
	// TODO: 回Ack给服务器
	private void responseAck(short type, int result){
		CNTCIPPacketHeader header = new CNTCIPPacketHeader(type, (short) 0, 100, 100);
		NtcipLedAck ntcipLedAck = new NtcipLedAck();
		ntcipLedAck.setPacketHeader(header);
		ntcipLedAck.setLedId(BytesUtil.getBytes(Configuration.getInstance().getScreenIdConfig()));
		ntcipLedAck.setReqResult(result);
		ntcipLedAck.setMsgDig(BytesUtil.getBytes(Configuration.getInstance().getServerKeyConfig()));
		sendThread.setToSendBuffer(ntcipLedAck.toBytes());
		mSendHandler.post(sendThread);
	}
	
	private void showResult(String result){
		if (listener != null) {
			// 回调给界面显示
			listener.onResultShow(result);
		}
	}

	private int getTextColor(short txtColor) {
		// TODO Auto-generated method stub
		// 默认红色
		int color = Color.RED;
		switch (txtColor) {
		case ContantType.TEXT_COLOR_RED:
			color = Color.RED;
			break;
		case ContantType.TEXT_COLOR_GREEN:
			color = Color.GREEN;
			break;
		case ContantType.TEXT_COLOR_BLUE:
			color = Color.BLUE;
			break;

		default:
			break;
		}
		return color;
	}

	public void sendSysErrorRequest() {
		// TODO:发送系统故障请求
		CNTCIPPacketHeader header = new CNTCIPPacketHeader(ContantType.IV_LED_REG_REQ, (short) 0, 100, 100);
		NtcipLedFaultNotifyReq request = new NtcipLedFaultNotifyReq();
		request.setPacketHeader(header);
		request.setLedId(BytesUtil.getBytes(Configuration.getInstance().getScreenIdConfig()));
		request.setFaultCode(2);
		request.setMsgDig(BytesUtil.getBytes(Configuration.getInstance().getScreenKeyConfig()));
		request.printf();
		sendThread.setToSendBuffer(request.toBytes());
		mSendHandler.post(sendThread);
	}

	public void sendScreenErrorRequest() {
		// TODO：发送屏幕故障请求
		CNTCIPPacketHeader header = new CNTCIPPacketHeader(ContantType.IV_LED_REG_REQ, (short) 0, 100, 100);
		NtcipLedFaultNotifyReq request = new NtcipLedFaultNotifyReq();
		request.setPacketHeader(header);
		request.setLedId(BytesUtil.getBytes(Configuration.getInstance().getScreenIdConfig()));
		request.setFaultCode(1);
		request.setMsgDig(BytesUtil.getBytes(Configuration.getInstance().getScreenKeyConfig()));
		request.printf();
		sendThread.setToSendBuffer(request.toBytes());
		mSendHandler.post(sendThread);
	}

	public void registerRequest() {
		CNTCIPPacketHeader header = new CNTCIPPacketHeader(ContantType.IV_LED_REG_REQ, (short) 0, 112, 100);
		NtcipLedRegReq request = new NtcipLedRegReq(header, BytesUtil.getBytes(Configuration.getInstance().getScreenIdConfig()), 1);
		request.printf();
		sendThread.setToSendBuffer(request.toBytes());
		mSendHandler.post(sendThread);
	}

	public void send(byte[] buffer) {
		if (udpHelper != null) {
			udpHelper.send(buffer);
		} else {
			D.e(TAG, "udpHelper == null");
		}
	}

	public class SendingDataTask implements Runnable {
		private LinkedList<byte[]> queueBuffer;

		public SendingDataTask() {
			queueBuffer = new LinkedList<byte[]>();
		}

		public void setToSendBuffer(byte[] buffer) {
			synchronized (queueBuffer) {
				queueBuffer.addLast(buffer);
				Log.d(TAG, "queue add, size = " + queueBuffer.size());
			}
		}

		public byte[] getFirstToSendBuffer() {
			byte[] buffer = null;
			synchronized (queueBuffer) {
				if (queueBuffer.size() > 0) {
					buffer = queueBuffer.removeFirst();
				}
			}
			Log.d(TAG, "remove queue: size = " + queueBuffer.size());
			return buffer;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (queueBuffer.size() > 0) {
				Log.d(TAG, "WritingDataTask running----->");
				send(getFirstToSendBuffer());
			}

		}

	}
}
