package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the controller for authentication-related endpoints, providing
 * methods to register and authenticate users.
 * <p>
 * It handles HTTP requests to the "/api/v1/auth" path.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    
    /**
     * Handles user registration requests.
     *
     * @param request the registration details of the user.
     * @return A {@link ResponseEntity} containing an {@link AuthenticationResponse} representing the registration outcome.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    /**
     * Handles user authentication requests.
     *
     * @param request the authentication details of the user.
     * @return A {@link ResponseEntity} containing an {@link AuthenticationResponse} representing the authentication outcome.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}

