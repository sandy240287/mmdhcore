package com.apm.utils;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.apm.repos.models.APMUser;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7317848474573275285L;
	
	private APMUser user;
	private Locale locale;

	public OnRegistrationCompleteEvent(APMUser user, Locale locale) {
		super(user);		
		this.user = user;
		this.locale = locale;
	}

	public APMUser getUser() {
		return user;
	}

	public Locale getLocale() {
		return locale;
	}
	
}
