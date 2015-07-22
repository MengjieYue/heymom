package com.heymom.backend.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.heymom.backend.dto.activity.ActivityDto;
import com.heymom.backend.dto.activity.ActivityProviderDto;
import com.heymom.backend.dto.activity.LocationDto;
import com.heymom.backend.service.ActivityProviderService;
import com.heymom.backend.service.ActivityService;
import com.heymom.backend.service.LocationService;
import com.heymom.backend.service.UserService;

@Controller
@RequestMapping(value = "/backend/activity/")
public class ActivityController {
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityProviderService activityProviderService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public ModelAndView listActivities(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection, @RequestParam(defaultValue = "-1") String city,
			@RequestParam(defaultValue = "-1") String age, @RequestParam(defaultValue = "-1") String type,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		Integer minAge = null;
		Integer maxAge = null;
		String[] cityArray = null;
		String[] ageArray = null;
		String[] typeArray = null;
		List<Integer> cityIds = null;
		List<Integer> types = null;
		if (!city.equalsIgnoreCase("-1")) {
			cityArray = city.split(",");
			cityIds = new ArrayList<Integer>();
			for (int i = 0; i < cityArray.length; i++) {
				cityIds.add(Integer.parseInt(cityArray[i]));
			}
		}
		if (age.equalsIgnoreCase("-1")) {
			minAge = null;
			maxAge = null;
		} else {
			ageArray = age.split("-");
			if (ageArray.length == 2) {
				minAge = Integer.valueOf(ageArray[0]);
				maxAge = Integer.valueOf(ageArray[1]);
			} else if(ageArray.length == 1){
				minAge = Integer.valueOf(ageArray[0]);
				maxAge = null;
			}
		}
		if (!type.equalsIgnoreCase("-1")) {
			typeArray = type.split(",");
			types = new ArrayList<Integer>();
			for (int i = 0; i < typeArray.length; i++) {
				types.add(Integer.parseInt(typeArray[i]));
			}
		}
		Page<ActivityDto> list = activityService.listAvaliableActivities(currentPage, pageSize, sortProperty,
				sortDirection, cityIds, minAge, maxAge, types, null);
		long now = new Date().getTime();
		List<String> status = new ArrayList<String>();
		for (int i = 0; i < list.getContent().size(); i++) {
			long startTime = list.getContent().get(i).getStartTime().getTime();
			long endTime = list.getContent().get(i).getEndTime().getTime();
			if (startTime > now) {
				status.add("未开始");
			} else if (endTime < now) {
				status.add("已结束");
			} else {
				status.add("进行中");
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ActivityManage");
		mav.addObject("list", list);
		mav.addObject("status", status);
		mav.addObject("currentPage", currentPage);
		mav.addObject("totalPages", list.getTotalPages());
		mav.addObject("userName", userService.getCurrentUserByToken(userToken).getMobile());
		mav.addObject("city", city);
		mav.addObject("type", type);
		mav.addObject("age", age);
		return mav;
	}

	@RequestMapping(value = "listByPage", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public Map<String, Object> listActivitiesByPage(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {

		Map<String, Object> result = new HashMap<String, Object>();

		Page<ActivityDto> list = activityService.listAvaliableActivities(currentPage, pageSize, sortProperty,
				sortDirection, null, null, null, null, null);
		long now = new Date().getTime();
		List<String> status = new ArrayList<String>();
		for (int i = 0; i < list.getContent().size(); i++) {
			long startTime = list.getContent().get(i).getStartTime().getTime();
			long endTime = list.getContent().get(i).getEndTime().getTime();
			if (startTime > now) {
				status.add("未开始");
			} else if (endTime < now) {
				status.add("已结束");
			} else {
				status.add("进行中");
			}
		}
		result.put("list", list);
		result.put("status", status);
		result.put("currentPage", currentPage);
		result.put("totalPages", list.getTotalPages());
		return result;
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public ModelAndView searchActivity(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection, @RequestParam() String name,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		Page<ActivityDto> list = activityService.listAvaliableActivities(currentPage, pageSize, sortProperty,
				sortDirection, null, null, null, null, name);
		long now = new Date().getTime();
		List<String> status = new ArrayList<String>();
		for (int i = 0; i < list.getContent().size(); i++) {
			long startTime = list.getContent().get(i).getStartTime().getTime();
			long endTime = list.getContent().get(i).getEndTime().getTime();
			if (startTime > now) {
				status.add("未开始");
			} else if (endTime < now) {
				status.add("已结束");
			} else {
				status.add("进行中");
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ActivityManage");
		mav.addObject("list", list);
		mav.addObject("status", status);
		mav.addObject("currentPage", currentPage);
		mav.addObject("totalPages", list.getTotalPages());
		return mav;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public void addActivity(HttpServletRequest request, HttpServletResponse response,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		String name = request.getParameter("name");
		Integer type = Integer.valueOf(request.getParameter("type"));
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = DateFormat.getDateInstance().parse(request.getParameter("startTime"));
			endTime = DateFormat.getDateInstance().parse(request.getParameter("endTime"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer attendee_min_age = Integer.valueOf(request.getParameter("attendee_min_age"));
		Integer attendee_max_age = Integer.valueOf(request.getParameter("attendee_max_age"));
		Double longitude = new Double(request.getParameter("longitude"));
		Double latitude = new Double(request.getParameter("latitude"));
		Integer districtId = Integer.valueOf(request.getParameter("district"));
		String address = request.getParameter("address");
		String image = request.getParameter("image");
		Float price = new Float(request.getParameter("price"));
		Integer providerId = Integer.valueOf(request.getParameter("provider"));
		String contactPhone = request.getParameter("contactPhone");
		Integer maxAttedneeCount = Integer.valueOf(request.getParameter("maxAttedneeCount"));
		Integer initialAttedneeCount = Integer.valueOf(request.getParameter("initialAttendeeCount"));
		List<Integer> amenities = new ArrayList<Integer>();
		String[] amenity = request.getParameterValues("amenities");
		if (amenity != null && amenity.length > 0) {
			for (int i = 0; i < amenity.length; i++) {
				amenities.add(Integer.valueOf(amenity[i]));
			}
		}
		String description = request.getParameter("description");
		ActivityProviderDto activityProviderDto = new ActivityProviderDto(activityProviderService.findById(providerId));
		LocationDto locationDto = new LocationDto(locationService.findById(1));
		ActivityDto activityDto = new ActivityDto();
		activityDto.setName(name);
		activityDto.setType(type);
		activityDto.setStartTime(startTime);
		activityDto.setEndTime(endTime);
		activityDto.setAttendeeMinAge(attendee_min_age);
		activityDto.setAttendeeMaxAge(attendee_max_age);
		activityDto.setLongitude(longitude);
		activityDto.setLatitude(latitude);
		activityDto.setAddress(address);
		activityDto.setImage(image);
		activityDto.setPrice(price);
		activityDto.setContactPhone(contactPhone);
		activityDto.setMaxAttedneeCount(maxAttedneeCount);
		activityDto.setInitialAttendeeCount(initialAttedneeCount);
		activityDto.setAmenities(amenities);
		activityDto.setDescription(description);
		activityDto.setProvider(activityProviderDto);
		activityDto.setDistrict(new LocationDto(locationService.findById(districtId)));
		activityDto.setAttendCount(0);
		activityDto.setFollowCount(0);
		activityDto.setCity(locationDto);
		activityService.createActivity(activityDto);
		try {
			response.sendRedirect("/backend/activity/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public void deleteActivity(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection, @RequestParam Integer id,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		activityService.deleteActivity(id);
		try {
			response.sendRedirect("/backend/activity/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "addNew", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_provider_list")
	public List<ActivityProviderDto> addNew(@CookieValue(value = "token", defaultValue = "token") String userToken) {
		return activityProviderService.listAllActivityProviders();
	}

	@RequestMapping(value = "searchOne", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public ActivityDto searchOne(@RequestParam Integer activityId,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		return activityService.findOneActivity(activityId);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public void update(HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		Integer activityId = Integer.valueOf(request.getParameter("id"));
		ActivityDto activityDto = new ActivityDto(activityService.findById(activityId));
		String name = request.getParameter("name");
		String image = request.getParameter("image");
		Integer type = Integer.valueOf(request.getParameter("type"));
		Integer attendee_min_age = Integer.valueOf(request.getParameter("attendee_min_age"));
		Integer attendee_max_age = Integer.valueOf(request.getParameter("attendee_max_age"));
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = DateFormat.getDateInstance().parse(request.getParameter("startTime"));
			endTime = DateFormat.getDateInstance().parse(request.getParameter("endTime"));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		Float price = new Float(request.getParameter("price"));
		Integer providerId = Integer.valueOf(request.getParameter("provider"));
		ActivityProviderDto activityProviderDto = new ActivityProviderDto(activityProviderService.findById(providerId));
		Integer maxAttedneeCount = Integer.valueOf(request.getParameter("maxAttedneeCount"));
		// Integer attend = Integer.valueOf(request.getParameter("attend"));
		// Integer follow = Integer.valueOf(request.getParameter("follow"));
		Integer initialAttedneeCount = Integer.valueOf(request.getParameter("initialAttendeeCount"));
		Double longitude = new Double(request.getParameter("longitude"));
		Double latitude = new Double(request.getParameter("latitude"));
		List<Integer> amenities = new ArrayList<Integer>();
		String[] amenity = request.getParameterValues("amenities");
		for (int i = 0; i < amenity.length; i++) {
			amenities.add(Integer.valueOf(amenity[i]));
		}
		Integer districtId = Integer.valueOf(request.getParameter("district"));
		activityDto.setDistrict(new LocationDto(locationService.findById(districtId)));
		String address = request.getParameter("address");
		String contactPhone = request.getParameter("contactPhone");
		String description = request.getParameter("description");
		activityDto.setName(name);
		activityDto.setType(type);
		activityDto.setStartTime(startTime);
		activityDto.setEndTime(endTime);
		activityDto.setAttendeeMinAge(attendee_min_age);
		activityDto.setAttendeeMaxAge(attendee_max_age);
		activityDto.setLongitude(longitude);
		activityDto.setLatitude(latitude);
		activityDto.setAddress(address);
		activityDto.setImage(image);
		activityDto.setPrice(price);
		activityDto.setContactPhone(contactPhone);
		activityDto.setMaxAttedneeCount(maxAttedneeCount);
		activityDto.setInitialAttendeeCount(initialAttedneeCount);
		activityDto.setAmenities(amenities);
		activityDto.setDescription(description);
		activityDto.setProvider(activityProviderDto);
		activityService.updateActivity(activityDto);
		try {
			response.sendRedirect("/backend/activity/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
