package com.example.demo.user;

import com.example.demo.departments.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private int age;
    private LocalDate startDate;
    private String phoneNumber;
    private String address;
    private Department department;
    private String position;
    private Role role;
    private Status status;
}
