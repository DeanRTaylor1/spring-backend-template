package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// This class is a filter that will be applied to each request once. It is used for JWT authentication.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    // Injecting necessary services for JWT processing and user details retrieval.
    private final JwtService jwtService;
    
    private final UserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    // This method is responsible for filtering each request and authenticating users based on JWTs.
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // Extracting Authorization header from the incoming request.
        final String authHeader = request.getHeader("Authorization");
        
        // Initializing variables to hold the JWT and the user email extracted from the token.
        final String jwt;
        final String userEmail;
        
        // If there is no Authorization header, or it does not start with "Bearer ", the filter chain continues with the next filter.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extracting the JWT from the Authorization header.
        jwt = authHeader.substring(7);
        
        // Extracting the user email (username) from the JWT.
        userEmail = jwtService.extractUsername(jwt);
        
        // If the user email is not null and there is no authentication object in the SecurityContext, it proceeds with authentication.
        if (userEmail != null && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {
            
            // Loading the user details from the UserDetailsService.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            // If the token is valid, it creates an Authentication object and sets it in the SecurityContext.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }
        
        // Continuing with the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}

