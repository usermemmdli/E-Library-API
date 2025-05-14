package com.example.E_Library_API.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersAccountsResponse {
    private String username;
    private String phoneNumber;
    private String email;
    private String address;
}
