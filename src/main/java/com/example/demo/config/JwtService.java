package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// This service class is for managing JSON Web Tokens (JWT)
@Service
public class JwtService {
    
    // Secret Key used for signing the JWTs
    private static final String SECRET_KEY = "N419ocL2eUxBSOES2zhek5agPzKJjrDErza1lW3O2ViPyeKRgPtfp09ekRnHcQwJ";
    
    // Extracts the username (subject) from the given token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    // Extracts a claim from the given token using the provided claims resolver function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    // Generates a token for the given UserDetails object with no additional claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    
    // Generates a token with additional claims for the given UserDetails object
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims) // Set additional claims
                .setSubject(userDetails.getUsername()) // Set subject as the username of the user
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set issued at to the current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Set expiration time
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign the token with the secret key
                .compact(); // Compact it to a String
    }
    
    // Validates the token against the UserDetails object
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extract the username from the token
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // Validate the username and check for expiration
    }
    
    // Checks if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Check if the expiration date is before the current date
    }
    
    // Extracts the expiration date from the given token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    // Extracts all claims from the given token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Set the signing key for validating the token
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the body containing the claims
    }
    
    // Returns the Key object created from the decoded secret key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decode the base64 encoded secret key
        return Keys.hmacShaKeyFor(keyBytes); // Create a HMAC SHA key from the decoded bytes
    }
}

