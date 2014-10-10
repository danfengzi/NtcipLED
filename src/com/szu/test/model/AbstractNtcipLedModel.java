package com.szu.test.model;

import java.io.Serializable;

public abstract class AbstractNtcipLedModel implements Serializable {

	private static final long serialVersionUID = -7804025900439152776L;
	abstract public int refresh(byte[] buffer);
	abstract public byte[] toBytes();
	abstract public String toString();
	abstract public void printf();
}
