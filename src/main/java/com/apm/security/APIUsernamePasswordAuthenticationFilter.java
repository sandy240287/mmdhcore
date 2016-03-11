package com.apm.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	@Autowired
	AuthSuccessHandler authSuccessHandler;
	@Autowired
	AuthFailureHandler authFailureHandler;
	
	private String usernameParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;

	private ObjectMapper objectMapper = new ObjectMapper();
	private boolean postOnly = true;

	protected APIUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		final UsernameAndPasswordParser usernameAndPasswordParser = new UsernameAndPasswordParser(request);
		usernameAndPasswordParser.parse();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				usernameAndPasswordParser.getTrimmedUsername(), usernameAndPasswordParser.getPassword());
		
		// set the supplied username as request attribute. May be used in AuthFailureHandler
		request.setAttribute(usernameParameter, usernameAndPasswordParser.getTrimmedUsername());
		return getAuthenticationManager().authenticate(authRequest);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		SecurityContextHolder.getContext().setAuthentication(authResult);
		getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		getFailureHandler().onAuthenticationFailure(request, response, failed);
	}
	
	@Override
	protected AuthenticationSuccessHandler getSuccessHandler() {
		return authSuccessHandler;
	}
	
	@Override
	protected AuthenticationFailureHandler getFailureHandler() {
		return authFailureHandler;
	}
	
	private class UsernameAndPasswordParser {
		private String username;

		private String password;

		private final HttpServletRequest request;

		private JsonNode credentialsNode;

		private UsernameAndPasswordParser(HttpServletRequest request) {

			this.request = request;
		}

		public void parse() {

			parseJsonFromRequestBody();
			if (jsonParsedSuccessfully()) {
				extractUsername();
				extractPassword();
			}
		}

		private void extractPassword() {

			this.password = extractValueByName(APIUsernamePasswordAuthenticationFilter.this.passwordParameter);
		}

		private void extractUsername() {

			this.username = extractValueByName(APIUsernamePasswordAuthenticationFilter.this.usernameParameter);
		}

		private String extractValueByName(String name) {

			String value = null;
			if (this.credentialsNode.has(name)) {
				JsonNode node = this.credentialsNode.get(name);
				if (node != null) {
					value = node.asText();
				}
			}
			return value;
		}

		private boolean jsonParsedSuccessfully() {

			return this.credentialsNode != null;
		}

		private void parseJsonFromRequestBody() {

			try {
				final ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(this.request);
				this.credentialsNode = APIUsernamePasswordAuthenticationFilter.this.objectMapper
						.readTree(servletServerHttpRequest.getBody());
			} catch (IOException e) {
				// ignoring
			}
		}

		private String getTrimmedUsername() {

			return this.username == null ? "" : this.username.trim();
		}

		private String getPassword() {

			return this.password == null ? "" : this.password;
		}

	}
}
