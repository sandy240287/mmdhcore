package com.apm.utils.exception;

public class InvalidVerificationTokenException extends Throwable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1038052590743885245L;

	private String errorCode;
	
	public InvalidVerificationTokenException(final String message, String string) {
        super(message);
    }
	
	public String getCode() {
		return errorCode;
	}

}