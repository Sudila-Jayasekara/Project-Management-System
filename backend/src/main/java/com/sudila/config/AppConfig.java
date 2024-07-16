package com.sudila.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    // Configures security filters and rules for HTTP requests
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configures session management to be stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configures authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").authenticated() // Requires authentication for endpoints starting with /api
                        .anyRequest().permitAll() // Allows access to other endpoints without authentication
                )
                // Adds JWT token validation filter before Basic Authentication filter
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // Disables CSRF protection
                .csrf(csrf -> csrf.disable())
                // Configures CORS (Cross-Origin Resource Sharing) policy
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    // Configures CORS policy with allowed origins, methods, credentials, headers, and exposed headers
    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000", // Allows requests from React app
                        "http://localhost:5173", // Allows requests from Vite app
                        "http://localhost:4200"  // Allows requests from Angular app
                ));
                cfg.setAllowedMethods(Collections.singletonList("*")); // Allows all HTTP methods
                cfg.setAllowCredentials(true); // Allows credentials like cookies and authorization headers
                cfg.setAllowedHeaders(Collections.singletonList("*")); // Allows all headers
                cfg.setExposedHeaders(Arrays.asList("Authorization")); // Exposes Authorization header
                cfg.setMaxAge(3600L); // Sets max age (in seconds) for pre-flight requests caching

                return cfg;
            }
        };
    }

    // Configures password encoder bean for encrypting passwords
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
