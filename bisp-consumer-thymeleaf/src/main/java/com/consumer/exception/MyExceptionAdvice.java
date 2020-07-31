package com.consumer.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.commons.enums.ResultEnum;
import com.commons.exception.MyException;
import com.commons.util.ResultBody;

//自定义全局异常捕获类
@ControllerAdvice
public class MyExceptionAdvice {

	/**
	 * 处理自定义的业务异常
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = MyException.class) // 处理自定义异常
	// @ResponseBody
	public String defaultException(HttpServletRequest request, MyException e) {
		// 传入错误并捕获
		e.printStackTrace();
		String code = e.getCode();
		String message = e.getMsg();
		System.out.println(message + code);
		ResultBody body = ResultBody.error(e.getCode(), e.getMsg());
		Map<String, Object> map = new HashMap<>();
		map.put("code", body.getCode());
		map.put("msg", body.getMessage());
		map.put("author", "BSIP");
		request.setAttribute("javax.servlet.error.status_code", 500);
		request.setAttribute("ext", map);
		return "forward:/error";

	}

	/**
	 * 处理空指针的异常
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = NullPointerException.class)
	public String exceptionHandler(HttpServletRequest req, NullPointerException e) {
		ResultBody body = ResultBody.error(ResultEnum.BODY_NOT_MATCH);
		Map<String, Object> map = new HashMap<>();
		map.put("code", body.getCode());
		map.put("msg", body.getMessage());
		req.setAttribute("javax.servlet.error.status_code", body.getCode());
		req.setAttribute("ext", map);
		return "foward:/error";
	}

	/**
	 * 处理其他异常
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	/*
	 * @ExceptionHandler(value =Exception.class)//处理其他异常
	 * 
	 * @ResponseBody public ResultBody exceptionHandler(HttpServletRequest req,
	 * Exception e){ return ResultBody.error(ResultEnum.INTERNAL_SERVER_ERROR); }
	 */

	@ExceptionHandler(value = Exception.class) // 处理其他异常
	public String exceptionHandler(HttpServletRequest req, Exception e) {
		ResultBody body = ResultBody.error(ResultEnum.INTERNAL_SERVER_ERROR);
		Map<String, Object> map = new HashMap<>();
		map.put("code", body.getCode());
		map.put("msg", body.getMessage());
		req.setAttribute("javax.servlet.error.status_code", body.getCode());
		req.setAttribute("ext", map);
		return "foward:/error";
	}

}
