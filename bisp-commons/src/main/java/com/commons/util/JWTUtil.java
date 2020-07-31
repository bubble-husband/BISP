package com.commons.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {

	private static final long EXPIRE_TIME = 60 * 60 * 1000;

	private static final String TOKEN_SECRET = "FLF_TOKEN$&@)(#_#+?abc}{asd776h0c123qp2020";

	/**
	 * 校验token是否正确
	 *
	 * @param token 密钥
	 * @return 是否正确
	 */
	public static boolean verify(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			System.out.println(jwt);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 获取登陆用户ID
	 * 
	 * @param token
	 * @return
	 */
	public static String getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userId").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
	
	

	// 生成Token
	public static String sign(String username, int userId) throws UnsupportedEncodingException {
		// 过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		// 私钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		// 设置头部信息
		Map<String, Object> header = new HashMap<String, Object>();
		// 附带 username、userid 信息 生成签名
		return JWT.create().withHeader(header).withClaim("username", username).withClaim("userId", userId)
				.withExpiresAt(date).sign(algorithm);

	}
}
