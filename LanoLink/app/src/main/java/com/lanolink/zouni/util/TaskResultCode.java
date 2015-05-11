package com.lanolink.zouni.util;

/**
 *
 */
public enum TaskResultCode {

	OK, // 正常
	NO_CONNECTION, // 无网络连接
	TIME_OUT, // 连接异常
	HTTP_ERROR, // 请求异常
	DATA_ERROR, // 数据拼装错误
	PARSE_ERROR, // 数据解析错误
	UN_KNOWN
}
