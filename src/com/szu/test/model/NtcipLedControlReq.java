package com.szu.test.model;

import android.util.Log;

import com.szu.test.utils.BytesUtil;

public class NtcipLedControlReq extends AbstractNtcipLedModel {

	private static final long serialVersionUID = 6160458932069942275L;
	private CNTCIPPacketHeader packetHeader; // 消息头
	private byte[] ledId = new byte[64]; // 屏幕id
	private int controlCode; // 控制指令
	private byte[] msgDig = new byte[32]; // 摘要
	private final String TAG = getClass().getSimpleName();

	public NtcipLedControlReq() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the packetHeader
	 */
	public CNTCIPPacketHeader getPacketHeader() {
		return packetHeader;
	}

	/**
	 * @param packetHeader the packetHeader to set
	 */
	public void setPacketHeader(CNTCIPPacketHeader packetHeader) {
		this.packetHeader = packetHeader;
	}

	/**
	 * @return the ledId
	 */
	public byte[] getLedId() {
		return ledId;
	}

	public String getLedIdString() {
		return new StringBuilder().append(getLedId()).toString();
	}

	/**
	 * @param ledId the ledId to set
	 */
	public void setLedId(byte[] ledId) {
		this.ledId = ledId;
	}

	/**
	 * @return the controlCode
	 */
	public int getControlCode() {
		return controlCode;
	}

	/**
	 * @param controlCode the controlCode to set
	 */
	public void setControlCode(int controlCode) {
		this.controlCode = controlCode;
	}

	/**
	 * @return the msgDig
	 */
	public byte[] getMsgDig() {
		return msgDig;
	}

	public String getMsgDigString() {
		return new StringBuilder().append(getMsgDig()).toString();
	}

	/**
	 * @param msgDig the msgDig to set
	 */
	public void setMsgDig(byte[] msgDig) {
		this.msgDig = msgDig;
	}

	@Override
	public int refresh(byte[] buffer) {
		// TODO Auto-generated method stub
		CNTCIPPacketHeader header = new CNTCIPPacketHeader();
		header.refresh(BytesUtil.readBytes(buffer, 0, 12));
		setPacketHeader(header);
		setLedId(BytesUtil.readBytes(buffer, 12, ledId.length));
		setControlCode(BytesUtil.getInt(BytesUtil.readBytes(buffer, 12 + ledId.length, 4)));
		setMsgDig(BytesUtil.readBytes(buffer, 16 + ledId.length, msgDig.length));
		return 0;
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[112];
		System.arraycopy(getPacketHeader().toBytes(), 0, buffer, 0, 12);
		System.arraycopy(getLedId(), 0, buffer, 12, getLedId().length);
		System.arraycopy(BytesUtil.getBytes(getControlCode()), 0, buffer, 12 + getLedId().length, 4);
		System.arraycopy(getMsgDig(), 0, buffer, 16 + getLedId().length, getMsgDig().length);
		return buffer;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("--CNTCIPPacketHeader: %s\n--LEDID:%s\n--ControlCode:%d\n--MsgDig:%s", getPacketHeader()
				.toString(), getLedIdString(), getControlCode(), getMsgDigString());
	}

	@Override
	public void printf() {
		// TODO Auto-generated method stub
		Log.d(TAG, this.toString());
	}
}
