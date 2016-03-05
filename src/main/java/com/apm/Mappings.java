package com.apm;

public final class Mappings {
	private Mappings(){}
	
	public static final String API_BASE_PATH = "/v1";
	
	public static final String API_USERS_PATH = API_BASE_PATH + "/users";
	public static final String API_USERS_ROLES_PATH = "/{userId}/roles";
	public static final String API_USERS_PASSWORDPROFILE_PATH = "/{userId}/passwordProfile";
	
	public static final String API_ROLES_PATH = API_BASE_PATH + "/roles";
	public static final String API_ROLES_PRIVILEDGES_PATH = "/{roleId}/priviledges";
	
}
