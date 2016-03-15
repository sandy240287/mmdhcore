package com.apm.service;

public class InvalidUserIdNameCombinationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1038052590743885245L;

	private String errorCode;
	
	public InvalidUserIdNameCombinationException(final String code, final String message) {
        super(message);
        this.errorCode = code;
    }

	public String getCode() {
		return errorCode;
	}
}
