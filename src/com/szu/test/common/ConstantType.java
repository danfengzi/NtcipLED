package com.szu.test.common;

public class ConstantType {
	//test
	// 消息类型
	public final static int IV_LED_REG_REQ = 11; // 屏幕注册消息请求
	public final static int IV_LED_REG_ACK = 12; // 屏幕注册消息响应
	public final static int IV_LED_MESDIS_REQ = 13; // 屏幕显示消息请求
	public final static int IV_LED_MSGDIS_ACK = 14; // 屏幕显示消息响应
	public final static int IV_LED_FAULTNOTIFY_REQ = 15; // 屏幕故障消息通知
	public final static int IV_LED_FAULTNOTIRY_ACK = 16; // 屏幕故障消息响应
	public final static int IV_LED_CONTROL_REQ = 17; // 屏幕控制信令请求
	public final static int IV_LED_CONTROL_ACK = 18; // 屏幕控制信令响应

	// 注册结果
	public final static int REQ_RESULT_SUCC = 1; // 成功
	public final static int REQ_RESULT_FAILED = 0; // 失败

	// 故障代码
	public final static int FAULT_CODE_SCREEN = 1; // 屏幕故障
	public final static int FAULT_CODE_SYSTEM = 2; // 系统故障

	// 响应结果
	public final static int RESULT_SUCC = 1; // 成功响应
	public final static int RESULT_FAILED = 0; // 响应失败
}
