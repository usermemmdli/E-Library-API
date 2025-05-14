package com.example.E_Library_API.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BooksUpdateRequest {
    String id;
    String title;
    String author;
    String description;
    String isbn;
    String category;
    String coverImage;
    String language;
    Boolean isAvailable;
    LocalDate publishedDate;
}
