package com.heymom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.incentive.CouponDto;
import com.heymom.backend.service.CouponService;

@Controller
@RequestMapping("/api/coupon/")
public class CouponAPIController {
	@Autowired
	private CouponService couponService;

	@RequestMapping(value = "hasReceiveCoupon/{couponId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Boolean> hasReceiveCoupon(@PathVariable Integer couponId, @RequestHeader("token") String userToken) {
		return new APIResult<Boolean>(couponService.hasReceiveCoupon(couponId));
	}

	@RequestMapping(value = "listAllAvailableCoupons", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Page<CouponDto>> listAllAvailableCoupons(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "startTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection) {
		Page<CouponDto> page = couponService
				.listAllAvaliableCoupons(currentPage, pageSize, sortProperty, sortDirection);
		return new APIResult<Page<CouponDto>>(page);
	}

	@RequestMapping(value = "listAllNotBeginCoupons", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Page<CouponDto>> listAllNotBeginCoupons(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "startTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection) {
		Page<CouponDto> page = couponService.listAllNotBeginCoupons(currentPage, pageSize, sortProperty, sortDirection);
		return new APIResult<Page<CouponDto>>(page);
	}

	@RequestMapping(value = "listAllReceivedCoupons", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Page<CouponDto>> listAllReceivedCoupons(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "startTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection, @RequestHeader("token") String userToken) {
		Page<CouponDto> page = couponService.listAllReceivedCoupons(currentPage, pageSize, sortProperty, sortDirection);
		return new APIResult<Page<CouponDto>>(page);
	}
}
