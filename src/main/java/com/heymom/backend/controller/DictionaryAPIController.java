package com.heymom.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.DictionaryItemDto;
import com.heymom.backend.service.DictionaryService;

@Controller
@RequestMapping("/api/dictionary/")
public class DictionaryAPIController {
	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<List<DictionaryItemDto>> listAll() {
		return new APIResult<List<DictionaryItemDto>>(dictionaryService.listAll());
	}
}
