package com.example.E_Library_API.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
