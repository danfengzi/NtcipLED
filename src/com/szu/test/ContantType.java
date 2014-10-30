package com.szu.test;

public class ContantType {

	public static final int MSG_RECEIVED = 0x01;
	
	//数据包类型代码11~18
	public static final short IV_LED_REG_REQ = 11; // 屏幕注册消息请求
	public static final short IV_LED_REG_ACK = 12; // 屏幕注册消息响应
	public static final short IV_LED_MSGDIS_REQ = 13; // 屏幕显示消息请求
	public static final short IV_LED_MSGDIS_ACK = 14; // 屏幕显示消息响应
	public static final short IV_LED_FAULTNOTIFY_REQ = 15; // 屏幕故障消息通知
	public static final short IV_LED_FAULTNOTIFY_ACK = 16;// 屏幕故障消息响应
	public static final short IV_LED_CONTROL_REQ = 17;// 屏幕控制信令请求
	public static final short IV_LED_CONTROL_ACK = 18; // 屏幕控制信令响应

	public static final int TEXT_COLOR_RED = 1;
	public static final int TEXT_COLOR_GREEN = 2;
	public static final int TEXT_COLOR_BLUE = 3;
	
	public static final String KEY_SCREEN_ID = "screen_id";
	public static final String KEY_KEY_STRING = "key";
	public static final String KEY_SERVER_ADDRESS = "server_address";
	public static final String KEY_SERVER_PORT = "server_port";
	public static final String DEFAULT_KEY = "DZNTCIP";
	public static final int DEFAULT_PORT = 23456;
}
