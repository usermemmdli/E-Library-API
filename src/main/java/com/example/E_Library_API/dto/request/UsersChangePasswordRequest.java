package com.example.E_Library_API.dto.request;

import lombok.Data;

@Data
public class UsersChangePasswordRequest {
    String oldPassword;
    String newPassword;
}
