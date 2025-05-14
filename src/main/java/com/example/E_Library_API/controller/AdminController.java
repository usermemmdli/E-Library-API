package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.request.BooksAvailableRequest;
import com.example.E_Library_API.dto.request.BooksRequest;
import com.example.E_Library_API.dto.request.BooksUpdateRequest;
import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final AuthHelperService authHelperService;

    @PostMapping("/books/add")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBooks(@RequestBody @Valid BooksRequest booksRequest) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        adminService.addBooks(booksRequest, currentUserEmail);
    }

    @PutMapping("/books/update")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateBooks(@RequestBody @Valid BooksUpdateRequest booksUpdateRequest) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        adminService.updateBooks(booksUpdateRequest, currentUserEmail);
    }

    @PatchMapping("/books/update-available")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateBooksAvailable(@RequestBody BooksAvailableRequest booksAvailableRequest) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        adminService.updateBooksAvailable(booksAvailableRequest, currentUserEmail);
    }

    @DeleteMapping("/books/delete")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBooks(@RequestParam String id) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        adminService.deleteBooks(id, currentUserEmail);
    }
}
