package com.example.E_Library_API.service;

import com.example.E_Library_API.dao.repository.UsersRepository;
import com.example.E_Library_API.security.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final AuthHelperService authHelperService;

    public void deleteUser(Long id, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        usersRepository.deleteById(id);
    }
}
