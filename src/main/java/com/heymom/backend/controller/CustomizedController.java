package com.heymom.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.customized.QuestionDto;
import com.heymom.backend.entity.customized.Option;
import com.heymom.backend.entity.customized.Question;
import com.heymom.backend.service.CustomizedService;
import com.heymom.backend.service.UserService;

@Controller
@RequestMapping(value = "/backend/customized/")
public class CustomizedController {
	@Autowired
	private CustomizedService customizedService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_question_list")
	public ModelAndView listQuestions(
			@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "type") String sortProperty,
			@RequestParam(value = "type", defaultValue = "-1") int type,
			@RequestParam(defaultValue = "DESC") String sortDirection,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		Page<QuestionDto> list = null;
		if (type != -1) {
			list = customizedService.listAvaliableQuestions(currentPage,
					pageSize, sortProperty, sortDirection, null, null, type,
					null);
		} else {
			list = customizedService.listAvaliableQuestions(currentPage,
					pageSize, sortProperty, sortDirection, null, null, null,
					null);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("TestManage");
		mav.addObject("list", list);
		mav.addObject("currentPage", currentPage);
		mav.addObject("totalPages", list.getTotalPages());
		mav.addObject("type", type);
		mav.addObject("userName", userService.getCurrentUserByToken(userToken)
				.getMobile());
		return mav;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_question_list")
	public void addQuestion(
			HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken,
			HttpServletResponse response) {
		Integer type = Integer.valueOf(request.getParameter("type"));
		Integer babyGender = Integer.valueOf(request.getParameter("gender"));
		Integer babyAge = Integer.valueOf(request.getParameter("age"));
		String decription = request.getParameter("description");
		String[] option = request.getParameterValues("option[]");
		String[] score = request.getParameterValues("score[]");
		
		Question question = new Question();
		question.setAge(babyAge);
		question.setGender(babyGender);
		question.setDecription(decription);
		question.setType(type);
		question = customizedService.addQuestion(new QuestionDto(question));
		
		List<Option> options = new ArrayList<Option>();
		for (int i = 0; i < option.length; i++) {
			Option entity = new Option();
			entity.setDecription(option[i]);
			entity.setValue(Integer.valueOf(score[i]));
			options.add(customizedService.addOption(entity));
		}
		
		question.setOptions(options);
		for (Option entity : options) {
			entity.setQuestion(question);
			customizedService.addOption(entity);
		}
		try {
			response.sendRedirect("/backend/customized/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_question_list")
	public void deleteQuestion(
			@RequestParam Integer questionId,
			@CookieValue(value = "token", defaultValue = "token") String userToken,
			HttpServletResponse response) {
		customizedService.deleteQuestion(questionId);
		try {
			response.sendRedirect("/backend/customized/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "find", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_question_list")
	public QuestionDto findQuestion(
			@RequestParam Integer questionId,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		return new QuestionDto(customizedService.findById(questionId));
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_question_list")
	public void updateCoupon(
			HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken,
			HttpServletResponse response) {
		Integer id = Integer.valueOf(request.getParameter("id"));
		Integer type = Integer.valueOf(request.getParameter("type"));
		Integer babyGender = Integer
				.valueOf(request.getParameter("babyGender"));
		Integer babyAge = Integer.valueOf(request.getParameter("babyAge"));
		String decription = request.getParameter("decription");
		Question question = new Question();
		question.setId(id);
		question.setAge(babyAge);
		question.setGender(babyGender);
		question.setDecription(decription);
		question.setType(type);
		customizedService.addQuestion(new QuestionDto(question));
		try {
			response.sendRedirect("/backend/customized/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
