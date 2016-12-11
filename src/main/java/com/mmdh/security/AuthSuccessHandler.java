package com.mmdh.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmdh.models.MMDHUser;
import com.mmdh.repos.MMDHUserRepository;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private MMDHUserRepository userRepo;

	private final ObjectMapper mapper;

	@Autowired
	AuthSuccessHandler(
			@Qualifier("mappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);

		String name = authentication.getName();
		MMDHUser user = userRepo.findByUsername(name);
		user.setInvalidLoginCount(0L);
		user.setLastLoginDate(GregorianCalendar.getInstance().getTime());
		userRepo.save(user);

		// allow CORS
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		// all options request should be returned with 200 OK
		if (request.getMethod().equalsIgnoreCase("OPTIONS"))
			response.setStatus(HttpServletResponse.SC_OK);

		// this is special response
		PrintWriter writer = response.getWriter();
		mapper.writeValue(writer, user);
		writer.flush();

	}
}
