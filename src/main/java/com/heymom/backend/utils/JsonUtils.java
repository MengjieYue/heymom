package com.heymom.backend.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils<T> {
	private static ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object o) throws JsonParseException, JsonMappingException, IOException {
		return mapper.writeValueAsString(o);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object toObject(String vlaue, Class valueType) throws JsonParseException, JsonMappingException,
	IOException {
		return mapper.readValue(vlaue, valueType);
	}
}
