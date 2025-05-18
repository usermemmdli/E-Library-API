package com.example.E_Library_API.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BooksRequest {
    @NotNull
    String title;
    @NotNull
    String author;
    @NotNull
    String description;
    @NotNull
    String isbn;
    @NotNull
    String genre;
    @NotNull
    String coverImage;
    @NotNull
    String language;
    @NotNull
    Boolean isAvailable;
    @NotNull
    String publishedDate;
}
