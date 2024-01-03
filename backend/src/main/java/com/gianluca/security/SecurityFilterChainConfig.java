package com.gianluca.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gianluca.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

	private final AuthenticationProvider authenticationProvider;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.POST, "/api/v1/customer")
						.permitAll().anyRequest().authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

}
