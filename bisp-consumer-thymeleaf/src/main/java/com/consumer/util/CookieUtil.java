package com.consumer.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.util.JWTUtil;

public class CookieUtil {

	public static String GU(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String token = GetCookieToken.GT(cookies);
		String username = JWTUtil.getUsername(token);
		return username;
	}

	public static Cookie get(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			return cookieMap.get(name);
		} else {
			return null;
		}
	}

	/**
	 * 设置一个cookie
	 * 
	 * @param response HttpServletResponse
	 * @param name     cookie的名称
	 * @param value    cookie的内容
	 * @param maxAge   cookie的持续时间
	 */
	public static void set(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 将cookie封装成Map
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
