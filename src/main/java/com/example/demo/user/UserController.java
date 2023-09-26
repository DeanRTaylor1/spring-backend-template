package com.example.demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> init() {
        return userService.getUsers();
    }

    //@RequestBody automatically maps the body to a User
    @PostMapping
    public UserResponse registerNewUser(@RequestBody User user){
        return userService.addNewUser(user);
    }
}
