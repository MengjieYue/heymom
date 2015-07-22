package com.heymom.backend.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.service.SMSService;

@Controller
@RequestMapping(value = "/backend/center/")
public class CenterController {
	@Autowired
	private SMSService smsService;

	@RequestMapping(value = "message", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public ModelAndView SMSView(
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		return new ModelAndView("message");
	}

	@RequestMapping(value = "sendMessage", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public void smsSend(
			HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		String receiver = request.getParameter("receivers");
		String[] receivers = null;
		if(receiver.contains("，")){
			receivers = receiver.split("，");
		}else{
			receivers = receiver.split(",");
		}
		String smsContent = request.getParameter("content");
		smsService.sendSMS(Arrays.asList(receivers), smsContent);
	}

}
