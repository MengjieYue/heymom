package com.heymom.backend.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.customized.CustomizedResultDto;
import com.heymom.backend.dto.customized.QuestionDto;
import com.heymom.backend.service.CustomizedService;

@Controller
@RequestMapping("/api/customized/")
public class CustomizedAPIController {
	@Autowired
	private CustomizedService customizedService;

	@RequestMapping(value = "createCustomizedResult", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<CustomizedResultDto> createCustomizedResult(@RequestBody Map<String, List<Integer>> parameters,
			@RequestHeader("token") String userToken) {
		List<Integer> answers = parameters.get("options");
		if (answers.size() < 14) {
			throw new HeymomException(300001);
		}
		return new APIResult<CustomizedResultDto>(customizedService.createCustomizedResult(answers));
	}

	@RequestMapping(value = "listQustions", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<List<QuestionDto>> listQustions(@RequestParam Integer gender,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
			@RequestHeader("token") String userToken) {
		if (gender == null || birthday == null) {
			throw new HeymomException(300002);
		}
		return new APIResult<List<QuestionDto>>(customizedService.listQustions(gender, birthday));
	}
}
