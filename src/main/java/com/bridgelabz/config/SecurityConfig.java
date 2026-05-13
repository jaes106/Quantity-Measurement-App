package com.bridgelabz.config;

import com.bridgelabz.auth.JwtAuthenticationFilter;
import com.bridgelabz.auth.OAuth2LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final OAuth2LoginSuccessHandler successHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter,
            OAuth2LoginSuccessHandler successHandler) {

        this.jwtFilter = jwtFilter;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http

                // Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                // JWT = stateless authentication
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/oauth2/**",
                                "/login/**",
                                "/auth/**",
                                "/actuator/**",
                                "/h2-console/**"
                        ).permitAll()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                // Google OAuth2 login
                .oauth2Login(oauth -> oauth
                        .successHandler(successHandler)
                )

                // Allow H2 console iframe
                .headers(headers ->
                        headers.frameOptions(frame -> frame.sameOrigin())
                )

                // Register JWT filter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }
}