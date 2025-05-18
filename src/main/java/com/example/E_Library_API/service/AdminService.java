package com.example.E_Library_API.service;

import com.example.E_Library_API.dao.entity.Books;
import com.example.E_Library_API.dao.repository.mongo.BooksRepository;
import com.example.E_Library_API.dto.request.BooksAvailableRequest;
import com.example.E_Library_API.dto.request.BooksRequest;
import com.example.E_Library_API.dto.request.BooksUpdateRequest;
import com.example.E_Library_API.enums.Genre;
import com.example.E_Library_API.enums.Language;
import com.example.E_Library_API.exception.EntityNotFoundException;
import com.example.E_Library_API.exception.InvalidValueException;
import com.example.E_Library_API.exception.IsbnAlreadyIsTaken;
import com.example.E_Library_API.security.AuthHelperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
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
            throw new IsbnAlreadyIsTaken("ISBN is already taken");
        }
        checkGenre(booksRequest.getGenre());
        books.setGenre(booksRequest.getGenre());
        books.setCoverImage(booksRequest.getCoverImage());
        checkLanguage(booksRequest.getLanguage());
        books.setLanguage(booksRequest.getLanguage());
        books.setIsAvailable(booksRequest.getIsAvailable());
        books.setPublishedDate(booksRequest.getPublishedDate());
        books.setCreatedAt(Date.from(Instant.now()));
        books.setUpdatedAt(null);
        booksRepository.save(books);
    }

    public void updateBooks(@Valid BooksUpdateRequest booksUpdateRequest, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        Books updatedBooks = booksRepository.findById(booksUpdateRequest.getId()).orElseThrow(() -> new RuntimeException("Book not found"));
        updatedBooks.setTitle(booksUpdateRequest.getTitle());
        updatedBooks.setAuthor(booksUpdateRequest.getAuthor());
        updatedBooks.setDescription(booksUpdateRequest.getDescription());
        updatedBooks.setIsbn(booksUpdateRequest.getIsbn());
        checkGenre(booksUpdateRequest.getGenre());
        updatedBooks.setGenre(booksUpdateRequest.getGenre());
        updatedBooks.setCoverImage(booksUpdateRequest.getCoverImage());
        checkLanguage(booksUpdateRequest.getLanguage());
        updatedBooks.setLanguage(booksUpdateRequest.getLanguage());
        updatedBooks.setIsAvailable(booksUpdateRequest.getIsAvailable());
        updatedBooks.setPublishedDate(booksUpdateRequest.getPublishedDate());
        updatedBooks.setUpdatedAt(Date.from(Instant.now()));
        booksRepository.save(updatedBooks);
    }

    public void updateBooksAvailable(BooksAvailableRequest booksAvailableRequest, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        Books books = booksRepository.findById(booksAvailableRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        books.setIsAvailable(booksAvailableRequest.getIsAvailable());
        booksRepository.save(books);
    }

    public void deleteBooks(String id, String currentUserEmail) {
        authHelperService.getAuthenticatedUser(currentUserEmail);
        if (booksRepository.existsById(id)) {
            booksRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Book not found");
        }
    }

    public void checkGenre(String genreName) {
        boolean exists = Arrays.stream(Genre.values())
                .anyMatch(g -> g.getDisplayName().equals(genreName));
        if (!exists) {
            throw new InvalidValueException("Invalid genre name: " + genreName);
        }
    }

    public void checkLanguage(String languageName) {
        boolean exists = Arrays.stream(Language.values())
                .anyMatch(l -> l.getLanguageName().equals(languageName));
        if (!exists) {
            throw new InvalidValueException("Invalid language name: " + languageName);
        }
    }
}
