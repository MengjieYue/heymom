package com.heymom.backend.controller;

import java.io.IOException;

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

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.activity.ActivityProviderDto;
import com.heymom.backend.dto.activity.LocationDto;
import com.heymom.backend.service.ActivityProviderService;
import com.heymom.backend.service.LocationService;
import com.heymom.backend.service.UserService;

@Controller
@RequestMapping(value = "/backend/provider/")
public class ActivityProviderController {
	@Autowired
	private ActivityProviderService activityProviderService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@LoginRequired(authority = "backend_provider_list")
	public ModelAndView listProviders(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		Page<ActivityProviderDto> list = activityProviderService.listAllActivityProviders(currentPage, pageSize,
				sortProperty, sortDirection, null, null, null, null, null);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ProviderManage");
		mav.addObject("list", list);
		mav.addObject("currentPage", currentPage);
		mav.addObject("totalPages", list.getTotalPages());
		mav.addObject("userName", userService.getCurrentUserByToken(userToken).getMobile());
		return mav;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_provider_list")
	public void addActivityProviders(HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		String name = request.getParameter("name");
		Integer type = Integer.valueOf(request.getParameter("type"));
		String contactPhone = request.getParameter("contactPhone");
		Integer cityId = Integer.valueOf(request.getParameter("city"));
		ActivityProviderDto activityProviderDto = new ActivityProviderDto();
		activityProviderDto.setName(name);
		activityProviderDto.setType(type);
		activityProviderDto.setContactPhone(contactPhone);
		activityProviderDto.setCity(new LocationDto(locationService.findById(cityId)));
		if(activityProviderService.findByName(name.trim())){
			throw new HeymomException(500002);
		}else{
			activityProviderService.createActivityProvider(activityProviderDto);
			try {
				response.sendRedirect("/backend/provider/list");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_provider_list")
	public void deleteActivityProvider(@RequestParam Integer activityProviderId,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		activityProviderService.deleteActivityProvider(activityProviderId);
		try {
			response.sendRedirect("/backend/provider/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "find", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_provider_list")
	public void findActivityProvider(@RequestParam Integer activityProviderId,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		activityProviderService.findById(activityProviderId);
		try {
			response.sendRedirect("/backend/provider/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "searchOne", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_provider_list")
	public ActivityProviderDto searchOne(@RequestParam Integer providerId,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		activityProviderService.findById(providerId);
		return new ActivityProviderDto(activityProviderService.findById(providerId));
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_provider_list")
	public void updateActivityProvider(HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		Integer activityProviderId = Integer.valueOf(request.getParameter("providerId"));
		ActivityProviderDto activityProviderDto = new ActivityProviderDto(
				activityProviderService.findById(activityProviderId));
		String name = request.getParameter("name");
		String contactPhone = request.getParameter("contactPhone");
		Integer type = Integer.valueOf(request.getParameter("type"));
		activityProviderDto.setName(name);
		activityProviderDto.setType(type);
		activityProviderDto.setContactPhone(contactPhone);
		activityProviderService.updateActivityProvider(activityProviderDto);
		try {
			response.sendRedirect("/backend/provider/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
