package com.example.E_Library_API.dto.response.pagination;

import com.example.E_Library_API.dto.response.BooksResponse;

import java.util.List;

public record BooksPaginationResponse(
        List<BooksResponse> booksResponse,
        long totalElements,
        int totalPages,
        boolean hasNextPages) {
}
