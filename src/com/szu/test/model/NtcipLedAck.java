package com.szu.test.model;

import android.util.Log;

import com.szu.test.utils.BytesUtil;

public class NtcipLedAck extends AbstractNtcipLedModel {
	private final String TAG = getClass().getSimpleName();
	private static final long serialVersionUID = -1683416262673927329L;
	private CNTCIPPacketHeader packetHeader; // 消息头
	private byte[] ledId = new byte[64]; // 屏幕编号
	private int reqResult; // 注册结果
	private byte[] msgDig = new byte[32]; // 摘要

	public NtcipLedAck() {
		// TODO Auto-generated constructor stub
		// packetHeader = new CNTCIPPacketHeader();
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
	 * @return the reqResult
	 */
	public int getReqResult() {
		return reqResult;
	}

	/**
	 * @param reqResult the reqResult to set
	 */
	public void setReqResult(int reqResult) {
		this.reqResult = reqResult;
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
		setReqResult(BytesUtil.getInt(BytesUtil.readBytes(buffer, 12 + ledId.length, 4)));
		setMsgDig(BytesUtil.readBytes(buffer, 16 + ledId.length, msgDig.length));
		return 0;
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[112];
		System.arraycopy(getPacketHeader().toBytes(), 0, buffer, 0, 12);
		System.arraycopy(getLedId(), 0, buffer, 12, getLedId().length);
		System.arraycopy(BytesUtil.getBytes(getReqResult()), 0, buffer, 12 + getLedId().length, 4);
		System.arraycopy(getMsgDig(), 0, buffer, 16 + getLedId().length, getMsgDig().length);
		return buffer;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("--CNTCIPPacketHeader: %s\n--LEDID:%s\n--REQRESULT:%d\n--MsgDig:%s", getPacketHeader()
				.toString(), getLedIdString(), getReqResult(), getMsgDigString());
	}

	@Override
	public void printf() {
		// TODO Auto-generated method stub
		Log.d(TAG, this.toString());
	}
}
