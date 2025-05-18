package com.example.E_Library_API.mapper;

import com.example.E_Library_API.dao.entity.Books;
import com.example.E_Library_API.dto.response.BooksResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public class BooksMapper {
    public BooksResponse toBooksResponse(Books books) {
        return BooksResponse.builder()
                .id(books.getId())
                .title(books.getTitle())
                .author(books.getAuthor())
                .genre(books.getGenre())
                .publishedDate(books.getPublishedDate())
                .description(books.getDescription())
                .isbn(books.getIsbn())
                .coverImage(books.getCoverImage())
                .language(books.getLanguage())
                .isAvailable(books.getIsAvailable())
                .build();
    }
}
