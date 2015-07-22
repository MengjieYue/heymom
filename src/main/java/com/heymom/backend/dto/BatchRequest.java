package com.heymom.backend.dto;

import java.util.Map;

public class BatchRequest {
	private String method;
	private Map<String, Object> params;
	private String resource;

	public String getMethod() {
		return method;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getResource() {
		return resource;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
