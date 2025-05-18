package com.example.E_Library_API.service;

import com.example.E_Library_API.dao.entity.Books;
import com.example.E_Library_API.dao.repository.mongo.BooksRepository;
import com.example.E_Library_API.dto.response.BooksResponse;
import com.example.E_Library_API.dto.response.pagination.BooksPaginationResponse;
import com.example.E_Library_API.mapper.BooksMapper;
import com.example.E_Library_API.security.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;
    private final BooksMapper booksMapper;
    private final AuthHelperService authHelperService;

    public BooksPaginationResponse getBooksPagination(int page, int count, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        Pageable pageable = PageRequest.of(page, count);
        Page<Books> allBooks = booksRepository.findAll(pageable);
        List<BooksResponse> booksResponseList = new CopyOnWriteArrayList<>(allBooks.getContent()
                .stream()
                .map(booksMapper::toBooksResponse)
                .toList());
        return new BooksPaginationResponse(booksResponseList, allBooks.getTotalElements(), allBooks.getTotalPages(), allBooks.hasNext());
    }
}
