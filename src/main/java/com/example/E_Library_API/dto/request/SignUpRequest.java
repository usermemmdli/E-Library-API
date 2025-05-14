package com.example.E_Library_API.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotNull
    private String username;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String address;
}
