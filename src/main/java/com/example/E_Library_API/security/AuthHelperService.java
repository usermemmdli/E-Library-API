package com.example.E_Library_API.security;

import com.example.E_Library_API.dao.entity.Users;
import com.example.E_Library_API.dao.repository.jpa.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHelperService {
    private final UsersRepository usersRepository;

    public Users getAuthenticatedUser(String currentUserEmail) {
        return usersRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
