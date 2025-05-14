package com.example.E_Library_API.dto.request;

import lombok.Data;

@Data
public class UsersEditRequest {
    String username;
    String phoneNumber;
    String email;
    String address;
}
