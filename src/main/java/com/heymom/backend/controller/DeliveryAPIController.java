package com.heymom.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.DeliveryDto;
import com.heymom.backend.service.DeliveryService;

@Controller
@RequestMapping("/api/delivery/")
public class DeliveryAPIController {
	@Autowired
	private DeliveryService deliveryService;

	@RequestMapping(value = "listpage", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Page<DeliveryDto>> listActivities(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "hotPriority") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection) {
		Page<DeliveryDto> page = deliveryService.listAvaliableDeliveries(currentPage, pageSize, sortProperty,
				sortDirection);
		return new APIResult<Page<DeliveryDto>>(page);
	}

	@RequestMapping(value = "list/{ids}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<List<DeliveryDto>> listByIds(@PathVariable String ids) {
		String[] idArray = ids.split(",");
		List<Integer> integerIds = new ArrayList<Integer>();
		for (String string : idArray) {
			integerIds.add(Integer.valueOf(string));
		}
		return new APIResult<List<DeliveryDto>>(deliveryService.listByIds(integerIds));
	}
}
