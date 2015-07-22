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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.DeliveryDto;
import com.heymom.backend.entity.Delivery;
import com.heymom.backend.service.DeliveryService;
import com.heymom.backend.service.UserService;

@Controller
@RequestMapping(value = "/backend/delivery/")
public class DeliveryController {
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_delivery_list")
	public ModelAndView listDeliveries(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "createTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection,
			@CookieValue(value = "token", defaultValue = "token") String userToken) {

		Page<DeliveryDto> list = deliveryService.listAvaliableDeliveries(currentPage, pageSize, sortProperty,
				sortDirection);
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("ResourceManage");
		mav.addObject("userName", userService.getCurrentUserByToken(userToken).getMobile());
		return mav;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_delivery_list")
	public void updateDelivery(HttpServletRequest request,
			@CookieValue(value = "token", defaultValue = "token") String userToken, HttpServletResponse response) {
		Integer deliveryId = Integer.valueOf(request.getParameter("deliveryId"));
		String content = request.getParameter("activity");
		Delivery delivery = null;
		try {
			delivery = deliveryService.findbyId(deliveryId);
			delivery.setContent(content);
			deliveryService.updateDelivery(new DeliveryDto(delivery));
			response.sendRedirect("/backend/delivery/list");
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
