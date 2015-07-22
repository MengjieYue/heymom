package com.heymom.backend.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heymom.backend.entity.DictionaryItem;
import com.heymom.backend.entity.DictionaryValue;

public class DictionaryItemDto {
	private Integer id;
	private String name;
	private List<DictionaryValueDto> values;

	public DictionaryItemDto() {
		super();
	}

	public DictionaryItemDto(DictionaryItem entity) throws JsonParseException, JsonMappingException, IOException {
		BeanUtils.copyProperties(entity, this);
		if (entity.getValues() != null) {
			values = new ArrayList<DictionaryValueDto>();
			for (DictionaryValue value : entity.getValues())
				values.add(new DictionaryValueDto(value));
		}
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<DictionaryValueDto> getValues() {
		return values;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValues(List<DictionaryValueDto> values) {
		this.values = values;
	}

	public DictionaryItem toEntity() throws JsonParseException, JsonMappingException, IOException {
		DictionaryItem entity = new DictionaryItem();
		BeanUtils.copyProperties(this, entity);
		if (values != null) {
			List<DictionaryValue> entityValues = new ArrayList<DictionaryValue>();
			for (DictionaryValueDto dto : values)
				entityValues.add(dto.toEntity());
			entity.setValues(entityValues);
		}
		return entity;
	}
}
