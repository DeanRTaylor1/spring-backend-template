package com.example.demo.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ErrorResponse {
    private List<String> messages;
    private LocalDateTime timestamp;
    private int status;
    
}
