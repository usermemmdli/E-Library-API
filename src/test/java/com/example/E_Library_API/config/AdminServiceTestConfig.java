package com.example.E_Library_API.config;

import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.AdminService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AdminServiceTestConfig {

    @Bean
    public AdminService adminService() {
        return Mockito.mock(AdminService.class);
    }

    @Bean
    public AuthHelperService authHelperService() {
        return Mockito.mock(AuthHelperService.class);
    }
}

