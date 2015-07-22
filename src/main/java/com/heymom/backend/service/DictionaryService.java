package com.heymom.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heymom.backend.dao.DictionaryItemDao;
import com.heymom.backend.dto.DictionaryItemDto;
import com.heymom.backend.utils.DtoUtils;

@Service
public class DictionaryService {
	@Autowired
	private DictionaryItemDao dictionaryItemDao;

	public List<DictionaryItemDto> listAll() {
		return DtoUtils.dictionaryItemDtoUtil.toDTO(dictionaryItemDao.findAllAvailable());
	}

}
