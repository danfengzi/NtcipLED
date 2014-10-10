package com.szu.test;

public class ContantType {

	public static final int MSG_RECEIVED = 0x01;

	public static final int IV_LED_REG_REQ = 0x11; // 屏幕注册消息请求
	public static final int IV_LED_REG_ACK = 0x12; // 屏幕注册消息响应
	public static final int IV_LED_MSGDIS_REQ = 0x13; // 屏幕显示消息请求
	public static final int IV_LED_MSGDIS_ACK = 0x14; // 屏幕显示消息响应
	public static final int IV_LED_FAULTNOTIFY_REQ = 0x15; // 屏幕故障消息通知
	public static final int IV_LED_FAULTNOTIFY_ACK = 0x16;// 屏幕故障消息响应
	public static final int IV_LED_CONTROL_REQ = 0x17;// 屏幕控制信令请求
	public static final int IV_LED_CONTROL_ACK = 0x18; // 屏幕控制信令响应

	public static final int TEXT_COLOR_RED = 1;
	public static final int TEXT_COLOR_GREEN = 2;
	public static final int TEXT_COLOR_BLUE = 3;
}
