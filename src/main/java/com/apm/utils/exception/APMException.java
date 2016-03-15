package com.apm.utils.exception;

public class APMException  extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613095633334981497L;

	private String errorCode;

	public APMException(final String code, final String message) {
        super(message);
        this.errorCode = "APM_EXCEPTION";
    }

	public String getCode() {
		return errorCode;
	}
}