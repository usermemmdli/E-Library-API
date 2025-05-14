package com.example.E_Library_API.dao.repository.mongo;

import com.example.E_Library_API.dao.entity.Books;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends MongoRepository<Books, String> {
    boolean existsByIsbn(String isbn);

}
