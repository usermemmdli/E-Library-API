package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.request.UsersChangePasswordRequest;
import com.example.E_Library_API.dto.request.UsersDeleteAccountsRequest;
import com.example.E_Library_API.dto.request.UsersEditRequest;
import com.example.E_Library_API.dto.response.UsersAccountsResponse;
import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartController.class)
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsersService usersService;
    @MockitoBean
    private AuthHelperService authHelperService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN", "USER", "SUPERUSER"})
    void testGetUserAccounts() throws Exception {
        String currentUserEmail = "user@example.com";

        UsersAccountsResponse usersAccountsResponse = UsersAccountsResponse.builder()
                .username("username")
                .phoneNumber("phoneNumber")
                .address("address")
                .email("email")
                .build();

        doNothing().when(authHelperService).getCurrentUserEmail();
        when(usersService.getUserAccount(currentUserEmail)).thenReturn(usersAccountsResponse);

        mockMvc.perform(get("api/v1/users/accounts")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(usersAccountsResponse)));

        verify(usersService, times(1)).getUserAccount(currentUserEmail);
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN", "USER", "SUPERUSER"})
    void testEditUserAccount() throws Exception {
        UsersEditRequest usersEditRequest = new UsersEditRequest();
        usersEditRequest.setUsername("username");
        usersEditRequest.setPhoneNumber("phoneNumber");
        usersEditRequest.setEmail("email");
        usersEditRequest.setAddress("address");

        doNothing().when(authHelperService).getCurrentUserEmail();
        doNothing().when(usersService).editUserAccount(usersEditRequest, "user@example.com");

        mockMvc.perform(put("/api/v1/users/accounts/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usersEditRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(usersService, times(1)).editUserAccount(usersEditRequest, "user@example.com");
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN", "USER", "SUPERUSER"})
    void testChangeUserPassword() throws Exception {
        UsersChangePasswordRequest usersChangePasswordRequest = new UsersChangePasswordRequest();
        usersChangePasswordRequest.setOldPassword("oldPassword");
        usersChangePasswordRequest.setNewPassword("newPassword");

        doNothing().when(authHelperService).getCurrentUserEmail();
        doNothing().when(usersService).changeUserPassword(usersChangePasswordRequest, "user@example.com");

        mockMvc.perform(put("/api/v1/users/accounts/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usersChangePasswordRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(usersService, times(1)).changeUserPassword(usersChangePasswordRequest, "user@example.com");
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER", "SUPERUSER"})
    void testDeleteUserAccount() throws Exception {
        String id = "100";
        String currentUserEmail = "user@example.com";

        UsersDeleteAccountsRequest usersDeleteAccountsRequest = new UsersDeleteAccountsRequest();
        usersDeleteAccountsRequest.setPassword("password");

        doNothing().when(authHelperService).getCurrentUserEmail();
        doNothing().when(usersService).deleteUserAccount(usersDeleteAccountsRequest, currentUserEmail);

        mockMvc.perform(delete("/api/v1/users/accounts/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usersDeleteAccountsRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(usersService, times(1)).deleteUserAccount(usersDeleteAccountsRequest, currentUserEmail);
    }
}
