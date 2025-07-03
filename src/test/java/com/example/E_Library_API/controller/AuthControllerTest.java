package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.request.LoginRequest;
import com.example.E_Library_API.dto.request.SignUpRequest;
import com.example.E_Library_API.dto.response.JwtResponse;
import com.example.E_Library_API.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;
    @Mock
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSignUp() throws Exception {
        //Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("admin");
        signUpRequest.setPhoneNumber("2133");
        signUpRequest.setPassword("test123");
        signUpRequest.setEmail("admin@admin.com");
        signUpRequest.setAddress("address");

        doNothing().when(authService).signUpUser(signUpRequest);

        //Actual & Assert
        mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(authService, times(1)).signUpUser(signUpRequest);
    }

    @Test
    void testLogin() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        JwtResponse jwtResponse = new JwtResponse("mockAccessToken", "mockRefreshToken");

        when(authService.loginUser(any(LoginRequest.class))).thenReturn(jwtResponse);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("mockAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("mockRefreshToken"));

        verify(authService, times(1)).loginUser(any(LoginRequest.class));
    }
}
