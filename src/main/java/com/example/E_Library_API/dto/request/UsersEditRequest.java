package com.example.E_Library_API.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsersEditRequest {
    @NotNull
    String username;
    @NotNull
    String phoneNumber;
    @NotNull
    String email;
    @NotNull
    String address;
}
