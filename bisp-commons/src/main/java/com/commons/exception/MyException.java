package com.commons.exception;

import com.commons.util.BaseErrorInfoInterface;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyException extends RuntimeException {
	/**自定义异常
	 * 
	 */
	private static final long serialVersionUID = -8007480235589213634L;
	private String code;
	private String msg;
	public MyException() {
		super();
	}
	public MyException(BaseErrorInfoInterface errorInfoInterface) {
		super(errorInfoInterface.getResultCode());
		this.code=errorInfoInterface.getResultCode();
		this.msg=errorInfoInterface.getResultMsg();
	}
	public MyException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
		super(errorInfoInterface.getResultCode(), cause);
		this.code=errorInfoInterface.getResultCode();
		this.msg=errorInfoInterface.getResultMsg();
	}
	public MyException(String msg) {
		super(msg);
		this.msg =msg;
	}
	public MyException(String code,String msg) {
		super(code);
		this.code=code;
		this.msg =msg;
	}
	public MyException(String code,String msg,Throwable cause) {
		super(code,cause);
		this.code=code;
		this.msg =msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public synchronized Throwable fillInStackTrace() {
		// TODO 自动生成的方法存根
		return this;
	}
	
	
	
	
}
