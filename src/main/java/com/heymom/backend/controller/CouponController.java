package com.heymom.backend.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.heymom.backend.dto.activity.ActivityProviderDto;
import com.heymom.backend.dto.incentive.CouponDto;
import com.heymom.backend.service.ActivityProviderService;
import com.heymom.backend.service.CouponService;
import com.heymom.backend.service.UserService;

@Controller
@RequestMapping(value = "/backend/coupon/")
public class CouponController {
	@Autowired
	private CouponService couponService;
	@Autowired
	private ActivityProviderService activityProviderService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_coupon_list")
	public ModelAndView listCoupons(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		Page<CouponDto> list = couponService
				.listAllAvaliableCoupons(currentPage, pageSize, sortProperty, sortDirection);
		long now = new Date().getTime();
		List<String> status = new ArrayList<String>();
		for (int i = 0; i < list.getContent().size(); i++) {
			long beginDate = list.getContent().get(i).getBeginDate().getTime();
			long expireDate = list.getContent().get(i).getExpireDate().getTime();
			if (beginDate > now) {
				status.add("未开始");
			} else if (expireDate < now) {
				status.add("已结束");
			} else {
				status.add("进行中");
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("CouponManage");
		mav.addObject("list", list);
		mav.addObject("status", status);
		mav.addObject("currentPage", currentPage);
		mav.addObject("totalPages", list.getTotalPages());
		mav.addObject("userName", userService.getCurrentUserByToken(userToken).getMobile());
		return mav;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_coupon_list")
	public void addCoupon(HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		String name = request.getParameter("name");
		Date beginDate = null;
		Date expireDate = null;
		try {
			beginDate = DateFormat.getDateInstance().parse(request.getParameter("beginDate"));
			expireDate = DateFormat.getDateInstance().parse(request.getParameter("expireDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String image = request.getParameter("image");
		Integer maxCount = Integer.valueOf(request.getParameter("maxCount"));
		String description = request.getParameter("description");
		Integer providerId = Integer.valueOf(request.getParameter("providerId"));
		Integer type = Integer.valueOf(request.getParameter("type"));
		CouponDto couponDto = new CouponDto();
		couponDto.setName(name);
		couponDto.setBeginDate(beginDate);
		couponDto.setExpireDate(expireDate);
		couponDto.setImageUrl(image);
		couponDto.setMaxCount(maxCount);
		couponDto.setType(type);
		couponDto.setDescription(description);
		couponDto.setProvider(new ActivityProviderDto(activityProviderService.findById(providerId)));
		couponService.createCoupon(couponDto);
		try {
			response.sendRedirect("/backend/coupon/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_coupon_list")
	public void deleteCoupon(@RequestParam Integer couponId,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		couponService.deleteCoupon(couponId);
		try {
			response.sendRedirect("/backend/coupon/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "find", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_coupon_list")
	public void findCoupon(@RequestParam Integer couponId,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		couponService.findById(couponId);
		// TODO
		try {
			response.sendRedirect("/backend/coupon/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "searchOne", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_coupon_list")
	public CouponDto searchOne(@RequestParam Integer couponId,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {
		return new CouponDto(couponService.findById(couponId));
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_coupon_list")
	public void updateCoupon(HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		String name = request.getParameter("name");
		Integer couponId = Integer.valueOf(request.getParameter("couponId"));
		CouponDto couponDto = new CouponDto(couponService.findById(couponId));
		Date beginDate = null;
		Date expireDate = null;
		try {
			beginDate = DateFormat.getDateInstance().parse(request.getParameter("beginDate"));
			expireDate = DateFormat.getDateInstance().parse(request.getParameter("expireDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String image = request.getParameter("image");
		Integer maxCount = Integer.valueOf(request.getParameter("maxCount"));
		String description = request.getParameter("description");
		Integer providerId = Integer.valueOf(request.getParameter("providerId"));
		couponDto.setName(name);
		couponDto.setBeginDate(beginDate);
		couponDto.setExpireDate(expireDate);
		couponDto.setImageUrl(image);
		couponDto.setMaxCount(maxCount);
		couponDto.setDescription(description);
		couponDto.setProvider(new ActivityProviderDto(activityProviderService.findById(providerId)));
		couponService.updateCoupon(couponDto);
		try {
			response.sendRedirect("/backend/coupon/list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
