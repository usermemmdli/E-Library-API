package com.example.E_Library_API.service;

import com.example.E_Library_API.dao.entity.Users;
import com.example.E_Library_API.dao.repository.jpa.UsersRepository;
import com.example.E_Library_API.dto.request.UsersChangePasswordRequest;
import com.example.E_Library_API.dto.request.UsersDeleteAccountsRequest;
import com.example.E_Library_API.dto.request.UsersEditRequest;
import com.example.E_Library_API.dto.response.UsersAccountsResponse;
import com.example.E_Library_API.mapper.UsersMapper;
import com.example.E_Library_API.security.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthHelperService authHelperService;

    public UsersAccountsResponse getUserAccount(String currentUserEmail) {
        Users users = authHelperService.getAuthenticatedUser(currentUserEmail);
        return usersMapper.toUsersAccountsResponse(users);
    }

    public void editUserAccount(UsersEditRequest usersEditRequest, String currentUserEmail) {
        Users users = authHelperService.getAuthenticatedUser(currentUserEmail);
        users.setUsername(usersEditRequest.getUsername());
        users.setPhoneNumber(usersEditRequest.getPhoneNumber());
        users.setEmail(usersEditRequest.getEmail());
        users.setAddress(usersEditRequest.getAddress());
        users.setUpdatedAt(Date.from(Instant.now()));
        usersRepository.save(users);
    }

    public void changeUserPassword(UsersChangePasswordRequest usersChangePasswordRequest, String currentUserEmail) {
        Users users = authHelperService.getAuthenticatedUser(currentUserEmail);
        if (passwordEncoder.matches(usersChangePasswordRequest.getOldPassword(), users.getPassword())) {
            users.setPassword(passwordEncoder.encode(usersChangePasswordRequest.getNewPassword()));
        } else {
            throw new RuntimeException("Old password is incorrect");
        }
        users.setUpdatedAt(Date.from(Instant.now()));
        usersRepository.save(users);
    }

    public void deleteUserAccount(UsersDeleteAccountsRequest usersDeleteAccountsRequest, String currentUserEmail) {
        Users users = authHelperService.getAuthenticatedUser(currentUserEmail);
        if (passwordEncoder.matches(usersDeleteAccountsRequest.getPassword(), users.getPassword())) {
            usersRepository.delete(users);
        } else {
            throw new RuntimeException("Password is incorrect");
        }
    }
}
