package com.kensure.mdt.service;

import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.http.WSUtils;

/**
 * ws调用短信实现类
 */
@Service
public class SmsService extends JSBaseService {

	private final static String wsurl = "http://172.18.18.38/prmInterfaceHIS2008/prmInterfaceHIS2008.asmx?wsdl";
	private final static String wsmethod = "SentMessageToPat";
	/**
	 * 发送短信
	 */
	public void sendSms(String phone,String content) {
		try {
			String[] paramName = { "phone", "content" };
			Object[] data = new Object[] { phone, content };
			WSUtils.getWS(wsurl, wsmethod, "http://tempuri.org/", paramName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
