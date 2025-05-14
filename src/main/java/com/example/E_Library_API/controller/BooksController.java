package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.response.pagination.BooksPaginationResponse;
import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BooksController {
    private final BooksService booksService;
    private final AuthHelperService authHelperService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','SUPERUSER')")
    public ResponseEntity<BooksPaginationResponse> getBooksPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "count", defaultValue = "5") int count) {
        String currentUserEmail = authHelperService.getCurrentUserEmail();
        BooksPaginationResponse booksPaginationResponse = booksService.getBooksPagination(page, count, currentUserEmail);
        return ResponseEntity.ok(booksPaginationResponse);
    }

}
