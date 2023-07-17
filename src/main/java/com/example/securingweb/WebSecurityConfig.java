package com.example.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home")
				.permitAll()
				.anyRequest()
				.authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/hello")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
//			 User.withDefaultPasswordEncoder()
			 User.builder()
				.username("user")
//				.password("password")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
		
		UserDetails admin =
//				 User.withDefaultPasswordEncoder()
				 User.builder()
					.username("admin")
//					.password("admin")
					.password(passwordEncoder().encode("admin"))
					.roles("ADMIN")
					.build();

		return new InMemoryUserDetailsManager(user,admin);
	}
	
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	    }
}
