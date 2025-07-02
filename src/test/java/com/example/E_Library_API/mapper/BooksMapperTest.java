package com.example.E_Library_API.mapper;

import com.example.E_Library_API.dao.entity.Books;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BooksMapperTest {
    @Autowired
    private BooksMapper booksMapper;

    @Test
    void testToBooksResponse() {
        //Arrange
        var request = new Books();
        request.setId("id");
        request.setTitle("title");
        request.setAuthor("author");
        request.setGenre("genre");
        request.setDescription("description");
        request.setLanguage("language");
        request.setCoverImage("coverImage");
        request.setIsAvailable(true);
        request.setPublishedDate("publishedDate");

        //Actual
        var actual = booksMapper.toBooksResponse(request);

        //Assert
        assert actual.getId().equals("id");
        assert actual.getTitle().equals("title");
        assert actual.getAuthor().equals("author");
        assert actual.getGenre().equals("genre");
        assert actual.getDescription().equals("description");
        assert actual.getLanguage().equals("language");
        assert actual.getCoverImage().equals("coverImage");
        assert actual.getIsAvailable() == true;
        assert actual.getPublishedDate().equals("publishedDate");
    }
}
