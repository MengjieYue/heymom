package com.heymom.backend.dto;

public class APIResult<T> {
	private T body;
	private int returnCode = 0;
	private String stackTrace;

	public APIResult() {
		super();
	}

	public APIResult(int returnCode, String stackTrace) {
		super();
		this.returnCode = returnCode;
		this.stackTrace = stackTrace;
	}

	public APIResult(T body) {
		super();
		this.body = body;
	}

	public T getBody() {
		return body;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

}
