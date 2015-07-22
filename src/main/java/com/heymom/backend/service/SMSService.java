package com.heymom.backend.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ctc.smscloud.xml.webservice.utils.WebServiceXmlClientUtil;
import com.heymom.backend.common.HeymomException;
import com.heymom.backend.controller.ErrorHandler;
import com.heymom.backend.dto.SMSResponse;
import com.heymom.backend.utils.XmlUtils;

@Service
public class SMSService {
	@Value("${sms.account}")
	private String account;
	private Logger logger = Logger.getLogger(ErrorHandler.class);
	private int maxPhoneSize = 500;
	@Value("${sms.password}")
	private String password;

	public void sendSMS(List<String> phoneList, String content) {
		if (phoneList == null || phoneList.isEmpty() || content == null)
			throw new HeymomException(800001);
		if (phoneList.size() > maxPhoneSize)
			throw new HeymomException(800002);

		sendSMS(phoneList.toString().substring(1, phoneList.toString().length() - 1), content);
	}

	public void sendSMS(String mobile, String content) {
		if (mobile == null || content == null)
			throw new HeymomException(800001);
		WebServiceXmlClientUtil.setServerUrl("http://3tong.net/services/sms");
		String responseString = WebServiceXmlClientUtil.sendSms(account, password, "", mobile, content, "", "", "");
		int returnCode = XmlUtils.formXML(SMSResponse.class, responseString).getResult();

		if (0 != returnCode) {
			logger.error("account is " + account);
			logger.error("password is " + password);
			logger.error("mobile is " + mobile);
			logger.error("content is " + content);
			throw new HeymomException(800010 + returnCode);
		}
	}
}
