package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//Component sets the class as an injectable bean
//Service is semantically better but has the same functionality as @Component
@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired

    public List<User> getUsers() {

    return userRepository.findAll();
}


    public void addNewUser(User user) {
       Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());

       if(userByEmail.isPresent()){
           throw new IllegalStateException("Email already exists");
        }

       userRepository.save(user);
    }
}
