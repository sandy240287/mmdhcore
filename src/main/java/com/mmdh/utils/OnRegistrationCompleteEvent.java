package com.mmdh.utils;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.mmdh.models.MMDHUser;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7317848474573275285L;
	
	private MMDHUser user;
	private Locale locale;

	public OnRegistrationCompleteEvent(MMDHUser user, Locale locale) {
		super(user);		
		this.user = user;
		this.locale = locale;
	}

	public MMDHUser getUser() {
		return user;
	}

	public Locale getLocale() {
		return locale;
	}
	
}
