package com.example.E_Library_API.controller;

import com.example.E_Library_API.dto.request.BooksAvailableRequest;
import com.example.E_Library_API.dto.request.BooksRequest;
import com.example.E_Library_API.dto.request.BooksUpdateRequest;
import com.example.E_Library_API.security.AuthHelperService;
import com.example.E_Library_API.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureTestDatabase
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminService adminService;
    @MockitoBean
    private AuthHelperService authHelperService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN", "SUPERADMIN"})
    void testAddBooks() throws Exception {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("title");
        booksRequest.setAuthor("author");
        booksRequest.setGenre("genre");
        booksRequest.setIsAvailable(true);
        booksRequest.setDescription("description");
        booksRequest.setCoverImage("coverImage");
        booksRequest.setLanguage("language");
        booksRequest.setPublishedDate("publishedDate");

        when(authHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        doNothing().when(adminService).addBooks(any(), eq("user@example.com"));

        mockMvc.perform(post("/api/v1/admin/books/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booksRequest)))
                .andExpect(status().isCreated());

        verify(adminService, times(1)).addBooks(any(BooksRequest.class), eq("user@example.com"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN", "SUPERADMIN"})
    void testUpdateBooks() throws Exception {
        BooksUpdateRequest booksUpdateRequest = new BooksUpdateRequest();
        booksUpdateRequest.setId("id");
        booksUpdateRequest.setTitle("title");
        booksUpdateRequest.setAuthor("author");
        booksUpdateRequest.setGenre("genre");
        booksUpdateRequest.setIsbn("isbn");
        booksUpdateRequest.setIsAvailable(true);
        booksUpdateRequest.setDescription("description");
        booksUpdateRequest.setCoverImage("coverImage");
        booksUpdateRequest.setLanguage("language");
        booksUpdateRequest.setPublishedDate("publishedDate");

        when(authHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        doNothing().when(adminService).updateBooks(booksUpdateRequest, "user@example.com");

        mockMvc.perform(put("/api/v1/admin/books/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booksUpdateRequest)))
                .andExpect(status().isOk());

        verify(adminService, times(1)).updateBooks(any(BooksUpdateRequest.class), any(String.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN", "SUPERADMIN"})
    void testUpdateAvailableBooks() throws Exception {
        BooksAvailableRequest booksAvailableRequest = new BooksAvailableRequest();
        booksAvailableRequest.setId("id");
        booksAvailableRequest.setIsAvailable(true);

        when(authHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        doNothing().when(adminService).updateBooksAvailable(booksAvailableRequest, "user@example.com");

        mockMvc.perform(put("/api/v1/admin/books/update-available")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booksAvailableRequest)))
                .andExpect(status().isOk());

        verify(adminService, times(1)).updateBooksAvailable(any(BooksAvailableRequest.class), any(String.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN", "SUPERADMIN"})
    void testDeleteBooks() throws Exception {
        String currentUserEmail = "user@example.com";

        when(authHelperService.getCurrentUserEmail()).thenReturn(currentUserEmail);
        doNothing().when(adminService).deleteBooks("100", currentUserEmail);

        mockMvc.perform(delete("/api/v1/admin/books/delete")
                        .param("100")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
