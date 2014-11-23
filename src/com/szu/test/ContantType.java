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

	//黑色、红色、黄色、绿色、青色、蓝色、紫红色、白色、橙色、琥珀色
	public static final int TEXT_COLOR_BLACK = 0;
	public static final int TEXT_COLOR_RED = 1;
	public static final int TEXT_COLOR_YELLOW = 2;
	public static final int TEXT_COLOR_GREEN = 3;
	public static final int TEXT_COLOR_CYAN = 4;		//青色，蓝绿色
	public static final int TEXT_COLOR_BLUE = 5;
	public static final int TEXT_COLOR_AMARANTH = 6;	//紫红色
	public static final int TEXT_COLOR_WHITE = 7;
	public static final int TEXT_COLOR_ORANGE = 8;
	public static final int TEXT_COLOR_AMBER = 9;		//琥珀色
	
	
	public static final String KEY_SCREEN_ID = "screen_id";
	public static final String KEY_SERVER_KEY = "server_key";
	public static final String KEY_SERVER_ADDRESS = "server_address";
	public static final String KEY_SERVER_PORT = "server_port";
	public static final String KEY_SCREEN_KEY = "screen_key";
	public static final String DEFAULT_SERVER_KEY = "DZNTCIP";
	public static final String DEFAULT_SCREEN_KEY = "LED";
	public static final String DEFAULT_SERVER_ADDRESS = "192.168.1.100";
	public static final int DEFAULT_SERVER_PORT = 10201;
	public static final String DEFAULT_SCREEN_ID = "GD-SZ-SZU-TYROAD001";

	
}
