package com.consumer.exception;


import java.util.Map;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
@Component
public class MyErrorAttributes extends DefaultErrorAttributes{
	//返回的map就是页面和json能获取的所有字段
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
		map.put("author", "BISP");
		Map<String, Object> ext =(Map<String, Object>) webRequest.getAttribute("ext", 0);
		map.put("ext", ext);
		return map;
				
	}

}
