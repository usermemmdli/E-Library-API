package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.response.BooksResponse;
import com.example.E_Library_API.dto.response.pagination.BooksPaginationResponse;
import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.BooksService;
import com.example.E_Library_API.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BooksController.class)
public class BooksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private BooksService booksService;
    @MockitoBean
    private CartService cartService;
    @MockitoBean
    private AuthHelperService authHelperService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER", "SUPERUSER"})
    void testGetBooksPagination() throws Exception {
        String currentUserEmail = "user@example.com";

        // Mock data
        BooksResponse book1 = BooksResponse.builder()
                .id("1")
                .author("author1")
                .title("title1")
                .description("description1")
                .build();
        BooksResponse book2 = BooksResponse.builder()
                .id("2")
                .author("author2")
                .title("title2")
                .description("description2")
                .build();

        BooksPaginationResponse mockResponse = new BooksPaginationResponse(
                List.of(book1, book2),
                2L,
                1,
                false
        );

        doNothing().when(authHelperService).getCurrentUserEmail();
        when(booksService.getBooksPagination(1, 2, currentUserEmail)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/books")
                        .param("page", String.valueOf(1))
                        .param("count", String.valueOf(2))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));

        verify(booksService, times(1)).getBooksPagination(1, 2, currentUserEmail);
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER", "SUPERUSER"})
    void testAddBookToCart() throws Exception {
        String id = "100";
        String currentUserEmail = "user@example.com";

        doNothing().when(authHelperService).getCurrentUserEmail();
        doNothing().when(cartService).addBookToCart(id, currentUserEmail);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BooksResponse.builder()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addBookToCart(id, currentUserEmail);
    }
}
