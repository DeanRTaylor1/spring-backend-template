package com.example.demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//Component sets the class as an injectable bean
//Service is semantically better but has the same functionality as @Component
@Service
public class UserService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Autowired
    
    public List<UserResponse> getUsers() {
        logger.info("Getting all users, current user count is {}", userRepository.count());
        
        List<User> users =  userRepository.findAll();
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }
    
    
    public UserResponse addNewUser(User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
        
        userRepository.save(user);
        
        return this.convertToUserResponse(user);
    }
    
    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                           .id(user.getId())
                           .firstName(user.getFirstName())
                           .lastName(user.getLastName())
                           .email(user.getEmail())
                           .dob(user.getDob())
                           .age(user.getAge())
                           .startDate(user.getStartDate())
                           .phoneNumber(user.getPhoneNumber())
                           .address(user.getAddress())
                           .department(user.getDepartment())
                           .position(user.getPosition())
                           .role(user.getRole())
                           .status(user.getStatus())
                           .build();
    }
}
