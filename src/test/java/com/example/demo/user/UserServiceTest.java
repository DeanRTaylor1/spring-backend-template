package com.example.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getUsers() {
        // Setup mock behavior
        when(userRepository.count()).thenReturn(5L);
        when(userRepository.findAll()).thenReturn(List.of(new User())); // Add mock users as needed
        
        // Call the method under test
        List<UserResponse> result = userService.getUsers();
        
        // Assert and verify
        assertEquals(1, result.size()); // Adjust based on mock users
        verify(userRepository).count();
        verify(userRepository).findAll();
    }
    
    @Test
    void addNewUserWithExistingEmail() {
        // Setup mock behavior
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        
        // Assert and verify
        assertThrows(IllegalStateException.class, () -> userService.addNewUser(user));
        verify(userRepository).findByEmail("test@example.com");
    }
    
    @Test
    void addNewUserWithNewEmail() {
        // Setup mock behavior
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        
        // Call the method under test
        userService.addNewUser(user);
        
        // Verify
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).save(user);
    }
}
