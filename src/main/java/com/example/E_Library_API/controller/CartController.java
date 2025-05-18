package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.response.pagination.BooksPaginationResponse;
import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final AuthHelperService authHelperService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','SUPERUSER')")
    public ResponseEntity<BooksPaginationResponse> getBookFromCart() {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        BooksPaginationResponse booksResponse = cartService.getBookFromCart(currentUserEmail);
        return ResponseEntity.ok(booksResponse);
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyRole('USER','SUPERUSER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookFromCart(String id) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        cartService.deleteBookFromCart(id, currentUserEmail);
    }
}
