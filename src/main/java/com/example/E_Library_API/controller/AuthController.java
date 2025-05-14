package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.request.LoginRequest;
import com.example.E_Library_API.dto.request.SignUpRequest;
import com.example.E_Library_API.dto.response.JwtResponse;
import com.example.E_Library_API.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.signUpUser(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.loginUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
