package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF so your POST requests work from the dashboard
            .csrf(csrf -> csrf.disable())
            
            // 2. Allow all requests to be seen, but require login for the app
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            
            // 3. Keep your Google Login working
            .oauth2Login(withDefaults());

        return http.build();
    }
}