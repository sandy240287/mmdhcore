package com.apm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
	AuthSuccessHandler authSuccessHandler;
	@Autowired
	AuthFailureHandler authFailureHandler;
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
			.authenticationProvider(customAuthenticationProvider)
			.exceptionHandling()
			// .authenticationEntryPoint(authenticationEntryPoint)
			.and()

			.formLogin()
				.permitAll().and()
				.formLogin()
				//.loginProcessingUrl("/user/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler).and()
			.logout()
				.permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(logoutSuccessHandler)
				.logoutSuccessUrl("/login").and()
				
				.authorizeRequests().antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll()
				.anyRequest().authenticated().and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf()
				.csrfTokenRepository(csrfTokenRepository());

	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

}