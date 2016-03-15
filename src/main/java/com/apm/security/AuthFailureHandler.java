package com.apm.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.apm.repos.APMUserRepository;
import com.apm.repos.models.APMUser;
import com.apm.utils.APMResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private APMUserRepository userRepo;
	
	private final ObjectMapper mapper;
	
	@Autowired
	AuthFailureHandler(@Qualifier("mappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		String lastUserName = (String)request.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);

		APMUser user = null;
		if (StringUtils.hasLength(lastUserName))
			user = userRepo.findByUsername(lastUserName);

		if (user != null) {
			user.setInvalidLoginCount(user.getInvalidLoginCount() + 1);
			userRepo.save(user);
		}

		PrintWriter writer = response.getWriter();
		APMResponse apiResponse = new APMResponse("AUTH_FAILURE", exception.getMessage()).error();
		mapper.writeValue(writer, apiResponse);
		writer.flush();
		
	}
}