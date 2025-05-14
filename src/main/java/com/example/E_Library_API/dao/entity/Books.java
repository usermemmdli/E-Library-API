package com.example.E_Library_API.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@Document(collection = "books")
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Books {
    @Id
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
    Date createdAt;
    Date updatedAt;
}
