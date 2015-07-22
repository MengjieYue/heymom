package com.heymom.backend.dto;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heymom.backend.entity.Delivery;
import com.heymom.backend.utils.JsonUtils;

public class DeliveryDto {
	private Map<String, String> contentMap;
	private Integer id;
	private String name;
	private Integer hotPriority;

	public DeliveryDto() {
		super();
	}

	@SuppressWarnings("unchecked")
	public DeliveryDto(Delivery entity) throws JsonParseException, JsonMappingException, IOException {
		BeanUtils.copyProperties(entity, this);
		contentMap = (Map<String, String>) JsonUtils.toObject(entity.getContent(), Map.class);
	}

	public Map<String, String> getContentMap() {
		return contentMap;
	}

	public Integer getId() {
		return id;
	}

	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Delivery toEntity() throws JsonParseException, JsonMappingException, IOException {
		Delivery entity = new Delivery();
		BeanUtils.copyProperties(this, entity);
		entity.setContent(JsonUtils.toJson(contentMap));
		return entity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHotPriority() {
		return hotPriority;
	}

	public void setHotPriority(Integer hotPriority) {
		this.hotPriority = hotPriority;
	}

}
