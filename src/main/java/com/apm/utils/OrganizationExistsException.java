package com.apm.utils;

public class OrganizationExistsException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613095633334981497L;

	public OrganizationExistsException(final String message) {
        super(message);
    }
}