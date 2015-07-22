package com.heymom.backend.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.service.UserService;

@Controller
@RequestMapping(value = "/backend/index")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginPage(@RequestParam() String mobile, @RequestParam() String password,
			HttpServletResponse response) {
		try {
			String token = userService.login(mobile, password);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("main");
			mav.addObject("User", mobile);
			/* mav.addObject("Token", token); */
			Cookie cookie = new Cookie("token", token);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
			try {
				response.sendRedirect("/backend/choose/main");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mav;
		} catch (HeymomException e) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("index");
			mav.addObject("User", mobile);
			mav.addObject("Password", password);
			switch (e.getErrorCode()) {
			case 100005:
				mav.addObject("message", "NotExist");
				break;
			}
			return mav;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLoginPage() {

		return new ModelAndView("index");
	}
}
