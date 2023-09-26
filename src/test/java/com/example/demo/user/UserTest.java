package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class UserTest {
    
    @Test
    public void testAgeCalculation() {
        User user = User.builder()
                        .dob(LocalDate.of(1990, 1, 1))
                        .build();
        
        assertEquals(33, user.getAge()); // Assuming the current year is 2023
    }
    
    @Test
    public void testAgeCalculationWithNullDob() {
        User user = User.builder().build();
        
        assertEquals(0, user.getAge());
    }
    
    @Test
    public void testAuthoritiesMapping() {
        User user = User.builder()
                        .role(Role.ADMIN) // Assuming an ADMIN role exists in your Role enum
                        .build();
        
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));
    }
    
    @Test
    public void testPasswordHandling() {
        User user = User.builder().build();
        user.setPassword("hashed_password");
        
        assertEquals("hashed_password", user.getPassword());
    }
}
