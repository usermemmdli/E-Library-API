package com.example.E_Library_API.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BooksResponse {
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
