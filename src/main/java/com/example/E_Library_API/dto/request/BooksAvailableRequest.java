package com.example.E_Library_API.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BooksAvailableRequest {
    @NotNull
    String id;
    @NotNull
    Boolean isAvailable;
}
