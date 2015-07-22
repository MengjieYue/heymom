package com.heymom.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.BatchRequest;
import com.heymom.backend.service.BatchService;
import com.heymom.backend.service.UserService;

@Controller
@RequestMapping("/api/batch/")
public class BatchAPIController {
	@Autowired
	private BatchService batchService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<?> doBatch(@RequestBody List<BatchRequest> requests,
			@RequestHeader(value = "token", required = false) String userToken) {
		return batchService.doBatch(requests, userToken);
	}
}
