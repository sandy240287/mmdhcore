package com.apm;

public final class Mappings {
	private Mappings(){}
	
	public static final String API_BASE_PATH = "/v1";
	
	public static final String API_USERS_PATH = API_BASE_PATH + "/users";
	public static final String API_USERS_ROLES_PATH = "/{userId}/roles";
	public static final String API_USERS_PASSWORDPROFILE_PATH = "/{userId}/passwordProfile";
	
	public static final String API_ROLES_PATH = API_BASE_PATH + "/roles";
	//public static final String API_ROLES_PRIVILEDGES_PATH = "/{roleId}/priviledges";
	
	public static final String API_ORGANIZATION_PATH = API_BASE_PATH + "/organizations";
	
	public static final String API_USER_CONFIRM_REGISTRATION = "/{userId}/registrationConfirm";

	public static final String LOGIN_URL = "LOGIN_URL:http://[hostname:port]/user/login";
	
}
