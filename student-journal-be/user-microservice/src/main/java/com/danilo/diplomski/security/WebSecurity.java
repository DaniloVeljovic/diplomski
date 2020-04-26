package com.danilo.diplomski.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.danilo.diplomski.services.UserService;

@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
	//dozovljava da se pogode sve rute koje su imaju prefiks users
	private Environment environment;
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment env, UserService s, BCryptPasswordEncoder b)
	{
		this.environment=env;
		this.userService=s;
		this.bCryptPasswordEncoder=b;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").permitAll()
		//http.authorizeRequests().antMatchers("/**")
		//.hasIpAddress(environment.getProperty("gateway.ip"))
		.and()
		.addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
		
	}

	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,environment,authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		
		return authenticationFilter;
	}
	
	@Override 
	protected void configure(AuthenticationManagerBuilder auth ) throws Exception
	{
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

}
