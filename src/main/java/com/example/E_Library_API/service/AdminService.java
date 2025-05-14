package com.example.E_Library_API.service;

import com.example.E_Library_API.dao.entity.Books;
import com.example.E_Library_API.dao.repository.mongo.BooksRepository;
import com.example.E_Library_API.dto.request.BooksAvailableRequest;
import com.example.E_Library_API.dto.request.BooksRequest;
import com.example.E_Library_API.dto.request.BooksUpdateRequest;
import com.example.E_Library_API.security.AuthHelperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BooksRepository booksRepository;
    private final AuthHelperService authHelperService;

    public void addBooks(BooksRequest booksRequest, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        Books books = new Books();
        books.setTitle(booksRequest.getTitle());
        books.setAuthor(booksRequest.getAuthor());
        books.setDescription(booksRequest.getDescription());
        if (!booksRepository.existsByIsbn(booksRequest.getIsbn())) {
            books.setIsbn(booksRequest.getIsbn());
        } else {
            throw new RuntimeException("ISBN is already taken");
        }
        books.setCategory(booksRequest.getCategory());
        books.setCoverImage(booksRequest.getCoverImage());
        books.setLanguage(booksRequest.getLanguage());
        books.setIsAvailable(booksRequest.getIsAvailable());
        books.setPublishedDate(booksRequest.getPublishedDate());
        books.setCreatedAt(Date.from(Instant.now()));
        books.setUpdatedAt(null);
        booksRepository.save(books);
    }

    public void updateBooks(@Valid BooksUpdateRequest booksUpdateRequest, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        Books updatedBooks = booksRepository.findById(booksUpdateRequest.getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        updatedBooks.setTitle(booksUpdateRequest.getTitle());
        updatedBooks.setAuthor(booksUpdateRequest.getAuthor());
        updatedBooks.setDescription(booksUpdateRequest.getDescription());
        updatedBooks.setIsbn(booksUpdateRequest.getIsbn());
        updatedBooks.setCategory(booksUpdateRequest.getCategory());
        updatedBooks.setCoverImage(booksUpdateRequest.getCoverImage());
        updatedBooks.setLanguage(booksUpdateRequest.getLanguage());
        updatedBooks.setIsAvailable(booksUpdateRequest.getIsAvailable());
        updatedBooks.setPublishedDate(booksUpdateRequest.getPublishedDate());
        updatedBooks.setUpdatedAt(Date.from(Instant.now()));
        booksRepository.save(updatedBooks);
    }

    public void updateBooksAvailable(BooksAvailableRequest booksAvailableRequest, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        Books books = booksRepository.findById(booksAvailableRequest.getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        books.setIsAvailable(booksAvailableRequest.getIsAvailable());
        booksRepository.save(books);
    }

    public void deleteBooks(String id, String currentUserEmail) {
        System.err.println("----------------->> AdminService deleteBooks");
        authHelperService.getAuthenticatedUser(currentUserEmail);
        System.err.println("----------------->> AdminService deleteBooks");
        if (booksRepository.existsById(id)) {
            System.err.println("----------------->> AdminService deleteBooks");
            booksRepository.deleteById(id);
            System.err.println("----------------->> AdminService deleteBooks");
        } else {
            throw new RuntimeException("Book not found");
        }
    }
}
