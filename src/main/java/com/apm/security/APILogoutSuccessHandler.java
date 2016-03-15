package com.apm.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.apm.utils.APMResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class APILogoutSuccessHandler implements LogoutSuccessHandler {

	private final ObjectMapper mapper;

	@Autowired
	APILogoutSuccessHandler(@Qualifier("mappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		
		PrintWriter writer = response.getWriter();
		APMResponse apiResponse = new APMResponse("LOGGED_OUT", "User has been logged out successfully").success();
		mapper.writeValue(writer, apiResponse);
		writer.flush();
		
		
	}
}