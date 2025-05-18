package com.example.E_Library_API.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BooksResponse {
    String id;
    String title;
    String author;
    String genre;
    String publishedDate;
    String description;
    String isbn;
    String coverImage;
    String language;
    Boolean isAvailable;
}
