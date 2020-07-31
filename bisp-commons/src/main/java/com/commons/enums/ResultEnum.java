package com.commons.enums;

import com.commons.util.BaseErrorInfoInterface;

public enum ResultEnum implements BaseErrorInfoInterface  {
	// 自定义枚举类
	FAIL("100", "失败"),
	SUCCESS("200", "成功"),
	UNLOGIN("201", "未登录"), 
	EXCEPTION("300", "系统异常"), 
	BODY_NOT_MATCH("400","请求的数据格式不符!"),
	NOT_FOUND("404","未找到该资源!"), 
	INTERNAL_SERVER_ERROR("500", "服务器内部错误!"), 
	SERVER_BUSY("503", "服务器正忙，请稍后再试!");

	private String code;
	private String msg;
	
	 ResultEnum(String code, String msg) {
		this.code = code;
		this.msg= msg;
		// TODO 自动生成的构造函数存根
	}
	@Override
	public String getResultCode() {
		// TODO 自动生成的方法存根
		return code;
	}
	@Override
	public String getResultMsg() {
		// TODO 自动生成的方法存根
		return msg;
	}
	

	

}
