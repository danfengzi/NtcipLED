package com.szu.test.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

import com.szu.test.utils.BytesUtil;
import com.szu.test.utils.MD5Util;

public class NtcipLedRegReq extends AbstractNtcipLedModel {
	private final String TAG = getClass().getSimpleName();
	private static final long serialVersionUID = 2432220976903289759L;

	private CNTCIPPacketHeader packetHeader;// 消息头
	private byte[] ledId = new byte[64]; // 屏幕编号
	private int workStatus; // 注册结果
	private byte[] msgDig = new byte[32]; // 摘要

	public NtcipLedRegReq(CNTCIPPacketHeader packetHeader, byte[] ledId, int workStatus) {
		// TODO Auto-generated constructor stub
		this.packetHeader = packetHeader;
		this.ledId = ledId;
		this.workStatus = workStatus;
		this.msgDig = BytesUtil.getBytes(DIG_KEY);
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

	/**
	 * @param ledId the ledId to set
	 */
	public void setLedId(byte[] ledId) {
		this.ledId = ledId;
	}

	public String getLedIdString() {
		return new StringBuilder().append(getLedId()).toString();
	}

	/**
	 * @return the workStatus
	 */
	public int getWorkStatus() {
		return workStatus;
	}

	/**
	 * @param workStatus the workStatus to set
	 */
	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
	}

	/**
	 * @return the msgDig
	 */
	public byte[] getMsgDig() {
		return msgDig;
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
		setWorkStatus(BytesUtil.getInt(BytesUtil.readBytes(buffer, 12 + ledId.length, 4)));
		setMsgDig(BytesUtil.readBytes(buffer, 16 + ledId.length, msgDig.length));
		return 0;
	}

	public String getMsgDigString() {
		return new StringBuilder().append(getMsgDig()).toString();
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub

		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}

		byte[] buffer = new byte[112];
		System.arraycopy(getPacketHeader().toBytes(), 0, buffer, 0, 12);
		digest.update(getPacketHeader().toBytes());
		System.arraycopy(getLedId(), 0, buffer, 12, getLedId().length);
		digest.update(getLedId());
		System.arraycopy(BytesUtil.int2Byte_BigEndian(getWorkStatus()), 0, buffer, 12 + getLedId().length, 4);
		digest.update(BytesUtil.int2Byte_BigEndian(getWorkStatus()));
		digest.update(getMsgDig());
		System.arraycopy(MD5Util.Md5(digest.digest()), 0, buffer, 16 + getLedId().length, 16);
		return buffer;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("--CNTCIPPacketHeader: %s\n--LEDID:%s\n--WorkStatus:%d\n--MsgDig:%s", getPacketHeader()
				.toString(), getLedIdString(), getWorkStatus(), getMsgDigString());
	}

	@Override
	public void printf() {
		// TODO Auto-generated method stub
		Log.d(TAG, this.toString());
	}
}
