package com.engelhard.todoapp.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

import com.engelhard.todoapp.utils.JwtTokenFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    // Define Token filter
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // Route protection method
    @Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Http login and cors disabled
		http.httpBasic(withDefaults()).csrf(csrf -> csrf.disable()).cors(cors->cors.disable()).cors(cors -> cors.disable());
		http.cors(withDefaults());

		// Route filter (routes that not require token)
		http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user/sign-up", "/api/user/sign-in", "api/user/greeting").permitAll()
                .anyRequest().authenticated());

		

		// UnAunthorized handler
		http.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex) -> {
			res.sendError(
					HttpServletResponse.SC_UNAUTHORIZED,
					"Auth fail!");
		}));

		// JWT filter
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
