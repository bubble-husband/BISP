package com.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

import com.tencentcloudapi.sms.v20190711.SmsClient;

import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

@RestController
public class SendSms {
	@GetMapping(value = "/user/getCode/{phone}")
	public String send(@PathVariable("phone") String phone) {
		try {

			String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
			System.out.println(code);
			Credential cred = new Credential("AKIDPbYGiui78uQgNoeuc6KJ8oHZdV7UFS6b",
					"Xt6E9pJG2f7OQDk6GBqIZfW8VovueEYd");
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			SmsClient client = new SmsClient(cred, "ap-shanghai", clientProfile);

			String params = "{\"PhoneNumberSet\":[\"+86" + phone
					+ "\"],\"TemplateID\":\"634274\",\"Sign\":\"橘子校园查题\",\"TemplateParamSet\":[\"" + code
					+ "\"],\"SmsSdkAppid\":\"1400305781\"}";
			SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);

			SendSmsResponse resp = client.SendSms(req);
			System.out.println(SendSmsRequest.toJsonString(resp));
			return code;
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
			return null;
		}

	}
}
