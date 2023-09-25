package com.example.demo.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@Profile("test")
@Configuration
public class Seeders {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User dean = new User(
                    "test1",
                    "test1@test1.com",
                    LocalDate.of(1863, 5, 25)
            );

            User test =  new User(
                    "test2",
                    "test2@test2.com",
                    LocalDate.of(1542, 12, 2)
            );

            userRepository.saveAll(List.of(dean, test));
        };

    }

}
