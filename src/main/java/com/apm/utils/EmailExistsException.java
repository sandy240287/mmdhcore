package com.apm.utils;

public class EmailExistsException extends Throwable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1038052590743885245L;

	public EmailExistsException(final String message) {
        super(message);
    }

}