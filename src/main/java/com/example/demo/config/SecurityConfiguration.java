package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Define configuration class, it indicates that this is a configuration class and can have @Bean annotated methods
@Configuration
// Enable Spring Security’s web security support
@EnableWebSecurity
public class SecurityConfiguration {
    
    // Declare AuthenticationProvider to be used for authentication
    private final AuthenticationProvider authenticationProvider;
    
    // Declare JwtAuthenticationFilter to filter incoming requests
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    public SecurityConfiguration(AuthenticationProvider authenticationProvider,
                                 JwtAuthenticationFilter jwtAuthFilter,
                                 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }
    // Define a Bean of type SecurityFilterChain, which holds the security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // Start configuring HttpSecurity
        return http
                // Disable CSRF (Cross-Site Request Forgery) protection
                .csrf(AbstractHttpConfigurer::disable)
                
                // Authorization configuration using the lambda DSL
                .authorizeHttpRequests(auth -> auth
                        // Define URL patterns that should be permitted without any security
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        
                        // For any other request, the user must be authenticated
                        .anyRequest()
                        .authenticated())
                
                // Define session management policy; here, it is stateless, meaning no session will be created or used by Spring Security
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // Set the AuthenticationProvider to be used
                .authenticationProvider(authenticationProvider)
                
                //Custom error handling for missing jwt
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                
                
                // Add the JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter to the filter chain
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                
                // Finalize and build the security configuration
                .build();
    }
}
