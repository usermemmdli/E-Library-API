package com.example.E_Library_API.controller;

import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UsersService usersService;
    private final AuthHelperService authHelperService;

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam Long id) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        usersService.deleteUser(id, currentUserEmail);
    }
}
