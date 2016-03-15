package com.apm.utils;

import java.io.Serializable;

public class APMResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5240295679187648958L;
	private String status;
	private final String code;
	private final String message;

	public APMResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public APMResponse success() {
		setStatus("success");
		return this;
	}

	public APMResponse error() {
		setStatus("error");
		return this;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
