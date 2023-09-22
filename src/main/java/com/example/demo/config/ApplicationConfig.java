package com.example.demo.config;

import com.example.demo.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// Indicates that this class is a configuration class that may have @Bean annotated methods.
public class ApplicationConfig {
    // Injecting UserRepository to interact with the User entity in the database.
    private final UserRepository userRepository;
    
    // Constructor-based dependency injection of UserRepository.
    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Bean
    // Indicates that a method produces a bean to be managed by the Spring container.
    public UserDetailsService userDetailsService() {
        // Returning a lambda as an implementation of UserDetailsService interface.
        // This service is primarily used by Spring Security to load user-related data.
        return username -> userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Throwing an exception if a user is not found by email.
    }
    
    @Bean // Declaring DaoAuthenticationProvider as a Spring Bean.
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Creating an instance of DaoAuthenticationProvider which allows retrieval of UserDetails using a username.
        authProvider.setUserDetailsService(userDetailsService()); // Setting the UserDetailsService for the authentication provider to load user-specific data.
        authProvider.setPasswordEncoder(passwordEncoder()); // Setting the PasswordEncoder for the authentication provider to encode passwords.
        return authProvider; // Returning the configured DaoAuthenticationProvider instance.
    }
    
    @Bean // Declaring AuthenticationManager as a Spring Bean.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Obtaining the AuthenticationManager from the passed AuthenticationConfiguration.
        return config.getAuthenticationManager(); // It allows to authenticate a user within the security context.
    }
    
    @Bean // Declaring PasswordEncoder as a Spring Bean.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Returning an instance of BCryptPasswordEncoder which is used to encode passwords using the BCrypt hashing algorithm.
    }
}
