package com.apm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	HttpAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	HttpLogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

	// refer -
	// https://crazygui.wordpress.com/2014/08/29/secure-rest-services-using-spring-security/

	protected void configure(HttpSecurity http) throws Exception {
			http
				.addFilterBefore(restFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().anyRequest().authenticated().and()
				.authenticationProvider(customAuthenticationProvider)
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.and()
			
			.formLogin()
				.permitAll()
				.loginProcessingUrl("/user/login")
				.and()
			
			.logout()
				.permitAll()
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
				.csrf()
				.disable()
				//.csrfTokenRepository(csrfTokenRepository())
				;
				
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
	
	@Bean 
	 public JsonUsernamePasswordAuthenticationFilter restFilter() throws Exception { 
		JsonUsernamePasswordAuthenticationFilter myFilter = new JsonUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/user/login")); 
	     myFilter.setAuthenticationManager(authenticationManager()); 
	 
	     return myFilter; 
	 } 

}