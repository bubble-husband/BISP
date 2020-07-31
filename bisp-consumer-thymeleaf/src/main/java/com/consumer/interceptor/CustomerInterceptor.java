package com.consumer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class CustomerInterceptor implements HandlerInterceptor {
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        Object user = request.getSession().getAttribute("id");        
	        System.out.println(user);
	        if(null == user){
	            //若为空则跳转到登录页
	        	//request.setAttribute("msg","没有权限请先登录");
	        	response.sendRedirect("/consumer/user/login");
	            return false;
	        }else{//已登陆
	            return true;
	        }
	    }
}
