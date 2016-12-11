package com.mmdh.security;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmdh.models.MMDHUser;
import com.mmdh.repos.MMDHUserRepository;
import com.mmdh.utils.MMDHResponse;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private MMDHUserRepository userRepo;

	private final ObjectMapper mapper;

	@Autowired
	AuthFailureHandler(
			@Qualifier("mappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		String lastUserName = (String) request
				.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);

		MMDHUser user = null;
		if (StringUtils.hasLength(lastUserName))
			user = userRepo.findByUsername(lastUserName);

		if (user != null) {
			user.setInvalidLoginCount(user.getInvalidLoginCount() + 1);
			userRepo.save(user);
		}

		// allow CORS
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		// all options request should be returned with 200 OK
		if(request.getMethod().equalsIgnoreCase("OPTIONS"))
			response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter writer = response.getWriter();
		MMDHResponse apiResponse = new MMDHResponse("AUTH_FAILURE", exception.getMessage()).error();
		mapper.writeValue(writer, apiResponse);
		writer.flush();

	}
}