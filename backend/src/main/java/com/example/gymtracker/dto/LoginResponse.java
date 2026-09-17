package com.example.gymtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {

    private String token;
}