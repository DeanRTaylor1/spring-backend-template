//package com.example.demo.user;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Configuration
//public class Seeders {
//
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository) {
//        return args -> {
//            User dean = new User(
//                    "Dean",
//                    "deanrtaylor@hotmail.com",
//                    LocalDate.of(1993, 5, 25)
//            );
//
//            User test =  new User(
//                    "Dean2",
//                    "deanrtaylor1@hotmail.com",
//                    LocalDate.of(1993, 5, 25)
//            );
//
//            userRepository.saveAll(List.of(dean, test));
//        };
//
//    }
//
//}
