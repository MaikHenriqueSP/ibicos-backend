package br.com.ibicos.ibicos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ibicos.ibicos.service.JwtService;
import br.com.ibicos.ibicos.service.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
		
	@Autowired
	private JwtService jwtService;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, userDetailsServiceImpl);
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		auth
			.userDetailsService(userDetailsServiceImpl)			
			.passwordEncoder(passwordEncoder());
	}
	
	
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/login", "/signUp", "/recover", "/verify")
				.permitAll()
				.anyRequest()
				.authenticated()
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
}
