package com.example.E_Library_API.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

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
    String genre;
    String description;
    String isbn;
    String coverImage;
    String language;
    Boolean isAvailable;
    String publishedDate;
    Date createdAt;
    Date updatedAt;
}
