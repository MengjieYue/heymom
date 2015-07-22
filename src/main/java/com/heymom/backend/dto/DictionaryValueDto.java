package com.heymom.backend.dto;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heymom.backend.entity.DictionaryValue;
import com.heymom.backend.utils.JsonUtils;

public class DictionaryValueDto {
	private Integer id;
	private DictionaryItemDto item;
	private String name;
	private Map<String, Object> value;

	public DictionaryValueDto() {
		super();
	}

	@SuppressWarnings("unchecked")
	public DictionaryValueDto(DictionaryValue entity) throws JsonParseException, JsonMappingException, IOException {
		BeanUtils.copyProperties(entity, this);
		value = (Map<String, Object>) JsonUtils.toObject(entity.getValue(), Map.class);
	}

	public Integer getId() {
		return id;
	}

	public DictionaryItemDto getItem() {
		return item;
	}

	public String getName() {
		return name;
	}

	public Map<String, Object> getValue() {
		return value;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItem(DictionaryItemDto item) {
		this.item = item;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Map<String, Object> value) {
		this.value = value;
	}

	public DictionaryValue toEntity() throws JsonParseException, JsonMappingException, IOException {
		DictionaryValue entity = new DictionaryValue();
		BeanUtils.copyProperties(this, entity);
		entity.setValue(JsonUtils.toJson(value));
		return entity;
	}
}
