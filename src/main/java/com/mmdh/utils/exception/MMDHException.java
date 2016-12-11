package com.mmdh.utils.exception;

public class MMDHException  extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613095633334981497L;

	private String errorCode;

	public MMDHException(final String code, final String message) {
        super(message);
        this.errorCode = "mmdh_EXCEPTION";
    }

	public String getCode() {
		return errorCode;
	}
}