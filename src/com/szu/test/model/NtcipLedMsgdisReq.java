package com.szu.test.model;

import android.util.Log;

import com.szu.test.utils.BytesUtil;

public class NtcipLedMsgdisReq extends AbstractNtcipLedModel {

	private static final long serialVersionUID = -5713185006086214342L;
	private final String TAG = getClass().getSimpleName();
	private CNTCIPPacketHeader packetHeader; // 消息头
	private byte[] ledId = new byte[64]; // 屏幕编号
	private byte[] txtMsg = new byte[128]; // 显示文字
	private short txtSize; // 字体大小
	private short txtColor; // 字体颜色，1红色，2绿色，3蓝色
	private CMapImage mapImage; // 图片信息
	private byte[] msgDig = new byte[32]; // 摘要

	public NtcipLedMsgdisReq() {
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
	 * @return the txtMsg
	 */
	public byte[] getTxtMsg() {
		return txtMsg;
	}

	public String getTxtMString() {
		return new StringBuilder().append(getTxtMsg()).toString();
	}

	/**
	 * @param txtMsg the txtMsg to set
	 */
	public void setTxtMsg(byte[] txtMsg) {
		this.txtMsg = txtMsg;
	}

	/**
	 * @return the txtSize
	 */
	public short getTxtSize() {
		return txtSize;
	}

	/**
	 * @param txtSize the txtSize to set
	 */
	public void setTxtSize(short txtSize) {
		this.txtSize = txtSize;
	}

	/**
	 * @return the txtColor
	 */
	public short getTxtColor() {
		return txtColor;
	}

	/**
	 * @param txtColor the txtColor to set
	 */
	public void setTxtColor(short txtColor) {
		this.txtColor = txtColor;
	}

	/**
	 * @return the mapImage
	 */
	public CMapImage getMapImage() {
		return mapImage;
	}

	/**
	 * @param mapImage the mapImage to set
	 */
	public void setMapImage(CMapImage mapImage) {
		this.mapImage = mapImage;
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
		int lenght = buffer.length;
		Log.d(TAG, "buffer lenght = " + lenght);

		CNTCIPPacketHeader header = new CNTCIPPacketHeader();
		header.refresh(BytesUtil.readBytes(buffer, 0, 12));
		setPacketHeader(header);
		setLedId(BytesUtil.readBytes(buffer, 12, 64));
		setTxtMsg(BytesUtil.readBytes(buffer, 76, 128));
		setTxtSize(BytesUtil.getShort(BytesUtil.readBytes(buffer, 204, 2)));
		setTxtColor(BytesUtil.getShort(BytesUtil.readBytes(buffer, 206, 2)));

		int imageSize = BytesUtil.getInt(BytesUtil.readBytes(buffer, 208, 4));
		CMapImage image = new CMapImage();
		image.refresh(BytesUtil.readBytes(buffer, 208, imageSize + 4));
		setMapImage(image);

		setMsgDig(BytesUtil.readBytes(buffer, 208 + imageSize + 4, 32));
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
		String temp = String.format(
				"--CNTCIPPacketHeader: %s\n--LEDID:%s\n--TxtMsg:%s\n--TxtSize:%d\n--TxtColor:%d\n--MsgDig:%s",
				getPacketHeader().toString(), getLedIdString(), getTxtMsg(), getTxtSize(), getTxtColor(),
				getMsgDigString()).toString();
		return temp;
	}

	@Override
	public void printf() {
		// TODO Auto-generated method stub
		Log.d(TAG, this.toString());
	}

}
