package com.szu.test.model;

import android.util.Log;

import com.szu.test.utils.BytesUtil;

public class CNTCIPPacketHeader extends AbstractNtcipLedModel {
	private final String TAG = getClass().getSimpleName();
	private static final long serialVersionUID = 8278827213161437510L;
	private short packetType; // 消息类型
	private short headRsv; // 保留字段
	private int packetSize; // 消息长度
	private int packetId; // 详细序列号

	public CNTCIPPacketHeader(){
		
	}
	
	public CNTCIPPacketHeader(short packetType, short headRsv, int packetSize, int packetId) {
		this.packetType = packetType;
		this.headRsv = headRsv;
		this.packetSize = packetSize;
		this.packetId = packetId;

	}

	/**
	 * @return the packetType
	 */
	public short getPacketType() {
		return packetType;
	}

	/**
	 * @param packetType the packetType to set
	 */
	public void setPacketType(short packetType) {
		this.packetType = packetType;
	}

	/**
	 * @return the headRsv
	 */
	public short getHeadRsv() {
		return headRsv;
	}

	/**
	 * @param headRsv the headRsv to set
	 */
	public void setHeadRsv(short headRsv) {
		this.headRsv = headRsv;
	}

	/**
	 * @return the packetSize
	 */
	public int getPacketSize() {
		return packetSize;
	}

	/**
	 * @param packetSize the packetSize to set
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	/**
	 * @return the packetId
	 */
	public int getPacketId() {
		return packetId;
	}

	/**
	 * @param packetId the packetId to set
	 */
	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}

	@Override
	public int refresh(byte[] buffer) {
		// TODO Auto-generated method stub
		setPacketType(BytesUtil.getShort(BytesUtil.readBytes(buffer, 0, 2)));
		setHeadRsv(BytesUtil.getShort(BytesUtil.readBytes(buffer, 2, 2)));
		setPacketSize(BytesUtil.getInt(BytesUtil.readBytes(buffer, 4, 4)));
		setPacketId(BytesUtil.getInt(BytesUtil.readBytes(buffer, 8, 4)));
		return 0;
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[12];
		System.arraycopy(BytesUtil.short2Byte_BigEndian(getPacketType()), 0, buffer, 0, 2);
		System.arraycopy(BytesUtil.short2Byte_BigEndian(getHeadRsv()), 0, buffer, 2, 2);
		System.arraycopy(BytesUtil.int2Byte_BigEndian(getPacketSize()), 0, buffer, 4, 4);
		System.arraycopy(BytesUtil.int2Byte_BigEndian(getPacketId()), 0, buffer, 8, 4);
		return buffer;
	}

	@Override
	public String toString() {
		return String.format("PackekType = %s, HeadRsv = %s, PacketSize = %s, PacketId = %s", getPacketType(),
				getHeadRsv(), getPacketSize(), getPacketId());
	}

	@Override
	public void printf() {
		// TODO Auto-generated method stub
		Log.d(TAG, this.toString());
	}

}
