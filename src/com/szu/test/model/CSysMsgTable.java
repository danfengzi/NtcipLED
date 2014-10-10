package com.szu.test.model;

import android.util.Log;

import com.szu.test.utils.BytesUtil;

public class CSysMsgTable extends AbstractNtcipLedModel {

	private final String TAG = getClass().getSimpleName();

	private static final long serialVersionUID = -1785911064587969354L;

	private int sysMsgID;
	private byte[] msgCont = new byte[256];
	private byte[] msgTime = new byte[32];

	public CSysMsgTable() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the sysMsgID
	 */
	public int getSysMsgID() {
		return sysMsgID;
	}

	/**
	 * @param sysMsgID the sysMsgID to set
	 */
	public void setSysMsgID(int sysMsgID) {
		this.sysMsgID = sysMsgID;
	}

	/**
	 * @return the msgCont
	 */
	public byte[] getMsgCont() {
		return msgCont;
	}

	public String getMsgContString() {
		return new StringBuilder().append(getMsgCont()).toString();
	}

	/**
	 * @param msgCont the msgCont to set
	 */
	public void setMsgCont(byte[] msgCont) {
		this.msgCont = msgCont;
	}

	/**
	 * @return the msgTime
	 */
	public byte[] getMsgTime() {
		return msgTime;
	}

	/**
	 * @return the msgTime
	 */
	public String getMsgTimeString() {
		return new StringBuilder().append(getMsgTime()).toString();
	}

	/**
	 * @param msgTime the msgTime to set
	 */
	public void setMsgTime(byte[] msgTime) {
		this.msgTime = msgTime;
	}

	@Override
	public int refresh(byte[] buffer) {
		// TODO Auto-generated method stub
		Log.d(TAG, "buffer lenght = " + buffer.length);
		setSysMsgID(BytesUtil.getInt(BytesUtil.readBytes(buffer, 0, 4)));
		setMsgCont(BytesUtil.readBytes(buffer, 4, 256));
		setMsgTime(BytesUtil.readBytes(buffer, 260, 32));
		return 0;
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printf() {
		// TODO Auto-generated method stub

	}
}
