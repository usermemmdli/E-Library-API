package com.example.E_Library_API.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsersDeleteAccountsRequest {
    @NotNull
    private String password;
}
