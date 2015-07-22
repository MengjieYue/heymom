package com.heymom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heymom.backend.service.UserService;

@Controller
@RequestMapping(value = "/backend/choose")

public class ChooseMenuController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView listActivities(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		ModelAndView mav = new ModelAndView("main");
		mav.addObject("userName", userService.getCurrentUserByToken(userToken).getMobile());
		return mav;
	}
}
