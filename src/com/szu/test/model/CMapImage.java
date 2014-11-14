package com.szu.test.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.szu.test.utils.BytesUtil;

public class CMapImage extends AbstractNtcipLedModel {

	/** 
	* serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = -6750392000494610838L;

	private int imageSize; // 图片长度
	private byte[] image; // 图片

	public CMapImage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the imageSize
	 */
	public int getImageSize() {
		return imageSize;
	}

	/**
	 * @param imageSize the imageSize to set
	 */
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	public Drawable getImageDrawable() {
		if (image.length != 0) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, imageSize);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
			return bitmapDrawable;
		} else {
			return null;
		}

	}

	@Override
	public int refresh(byte[] buffer) {
		// TODO Auto-generated method stub
		setImageSize(BytesUtil.byte2int_BigEndian(buffer,0));
		setImage(BytesUtil.readBytes(buffer, 4, getImageSize()));
		return 0;
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[getImageSize() + 4];
		System.arraycopy(BytesUtil.getBytes(getImageSize()), 0, buffer, 0, 4);
		System.arraycopy(getImage(), 0, buffer, 4, getImageSize());
		return buffer;
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
