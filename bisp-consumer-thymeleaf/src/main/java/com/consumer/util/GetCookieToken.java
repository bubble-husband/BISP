package com.consumer.util;

import javax.servlet.http.Cookie;

public class GetCookieToken {

	public static String GT(Cookie[] cookies) {
		String token = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					token = cookie.getValue();
					return token;
				}
			}
		}
		return token;
	}
}
