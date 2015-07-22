package com.heymom.backend.common;

public class HeymomException extends RuntimeException {
	private static final long serialVersionUID = 2134223938478410255L;
	public static int UNKNOWN = 999999;
	private int errorCode;

	public HeymomException(int i) {
		errorCode = i;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
