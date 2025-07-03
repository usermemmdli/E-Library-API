package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.response.BooksResponse;
import com.example.E_Library_API.dto.response.pagination.BooksPaginationResponse;
import com.example.E_Library_API.security.AuthHelperService;
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
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartController.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CartService cartService;
    @MockitoBean
    private AuthHelperService authHelperService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER", "SUPERUSER"})
    void testGetBookFromCart() throws Exception {
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
        when(cartService.getBookFromCart(currentUserEmail)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/cart")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));

        verify(cartService, times(1)).getBookFromCart(currentUserEmail);
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER", "SUPERUSER"})
    void testDeleteBookFromCart() throws Exception {
        String id = "100";
        String currentUserEmail = "user@example.com";

        doNothing().when(authHelperService).getCurrentUserEmail();
        doNothing().when(cartService).deleteBookFromCart(id, currentUserEmail);

        mockMvc.perform(delete("/api/v1/cart")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteBookFromCart(id, currentUserEmail);
    }
}
