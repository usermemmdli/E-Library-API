package com.example.E_Library_API.service;

import com.example.E_Library_API.dao.entity.Roles;
import com.example.E_Library_API.dao.entity.Users;
import com.example.E_Library_API.dao.repository.jpa.RolesRepository;
import com.example.E_Library_API.dao.repository.jpa.UsersRepository;
import com.example.E_Library_API.dto.request.LoginRequest;
import com.example.E_Library_API.dto.request.SignUpRequest;
import com.example.E_Library_API.dto.response.JwtResponse;
import com.example.E_Library_API.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public void signUpUser(@Valid SignUpRequest signUpRequest) {
        if (usersRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }
        Users users = new Users();

        Roles defaultRole = (Roles) rolesRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("No roles found"));

        users.setRoles(defaultRole);
        users.setUsername(signUpRequest.getUsername());
        users.setPhoneNumber(signUpRequest.getPhoneNumber());
        users.setEmail(signUpRequest.getEmail());
        users.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        users.setAddress(signUpRequest.getAddress());
        users.setCreatedAt(Timestamp.from(Instant.now()));
        usersRepository.save(users);
    }


    public JwtResponse loginUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Users users = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email or password is invalid"));
        String accessToken = jwtService.createAccessToken(users);
        String refreshToken = jwtService.createRefreshToken(users);

        return new JwtResponse(accessToken, refreshToken);
    }
}
