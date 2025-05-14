package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.request.UsersChangePasswordRequest;
import com.example.E_Library_API.dto.request.UsersDeleteAccountsRequest;
import com.example.E_Library_API.dto.request.UsersEditRequest;
import com.example.E_Library_API.dto.response.UsersAccountsResponse;
import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UsersService usersService;
    private final AuthHelperService authHelperService;

    @GetMapping("/accounts")
    @PreAuthorize("hasAnyRole('ADMIN','USER','SUPERUSER')")
    public ResponseEntity<UsersAccountsResponse> getUserAccount() {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        UsersAccountsResponse usersAccountsResponse = usersService.getUserAccount(currentUserEmail);
        return ResponseEntity.ok(usersAccountsResponse);
    }

    @PutMapping("/accounts/edit")
    @PreAuthorize("hasAnyRole('ADMIN','USER','SUPERUSER')")
    @ResponseStatus(HttpStatus.OK)
    public void editUserAccount(@RequestBody UsersEditRequest usersEditRequest) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        usersService.editUserAccount(usersEditRequest, currentUserEmail);
    }

    @PatchMapping("/accounts/change-password")
    @PreAuthorize("hasAnyRole('ADMIN','USER','SUPERUSER')")
    @ResponseStatus(HttpStatus.OK)
    public void changeUserPassword(@RequestBody UsersChangePasswordRequest usersChangePasswordRequest) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        usersService.changeUserPassword(usersChangePasswordRequest, currentUserEmail);
    }

    @DeleteMapping("/accounts/delete")
    @PreAuthorize("hasAnyRole('USER','SUPERUSER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserAccount(@RequestBody UsersDeleteAccountsRequest usersDeleteAccountsRequest) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        usersService.deleteUserAccount(usersDeleteAccountsRequest, currentUserEmail);
    }
}
