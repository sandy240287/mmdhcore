package com.apm.utils.exception;

public class OrganizationNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613095633334981497L;

	private String errorCode;

	public OrganizationNotFoundException(final String code, final String message) {
        super(message);
        this.errorCode = code;
    }

	public String getCode() {
		return errorCode;
	}
}