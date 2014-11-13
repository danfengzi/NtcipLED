package com.szu.test.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

import com.szu.test.Configuration;
import com.szu.test.utils.BytesUtil;
import com.szu.test.utils.MD5Util;

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
//		this.ledId = ledId;
		System.arraycopy(ledId, 0, this.ledId, 0, ledId.length);
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
//		this.msgDig = msgDig;
		System.arraycopy(msgDig, 0, this.msgDig, 0, msgDig.length);
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
		
		//校验
		if(MD5Util.isIllegal(buffer, msgDig)) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[112];
		System.arraycopy(getPacketHeader().toBytes(), 0, buffer, 0, 12);
		System.arraycopy(getLedId(), 0, buffer, 12, getLedId().length);
		System.arraycopy(BytesUtil.getBytes(getReqResult()), 0, buffer, 12 + getLedId().length, 4);
		System.arraycopy(getMsgDig(), 0, buffer, 16 + getLedId().length, getMsgDig().length);
		
		//将md5填充到摘要字段
		byte[] tempMsgDig = new byte[32];
		System.arraycopy(MD5Util.Md5(buffer), 0, tempMsgDig, 0, 16);
		System.arraycopy(tempMsgDig, 0, buffer, 16 + getLedId().length, 32);
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
