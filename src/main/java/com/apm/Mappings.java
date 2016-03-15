package com.apm;

public final class Mappings {
	private Mappings(){}
	
	public static final String API_BASE_PATH = "/v1";
	
	public static final String API_USERS_PATH = API_BASE_PATH + "/users";
	
	public static final String API_ORGANIZATION_PATH = API_BASE_PATH + "/organizations";

	public static final String LOGIN_URL = "LOGIN_URL:http://[hostname:port]/user/login";
	public static final String REDIRECT_URL_AFTER_REGISTRATION_CONFIRMATION = "https://apmui.herokuapp.com";
	
}
