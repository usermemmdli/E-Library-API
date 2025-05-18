package com.example.E_Library_API.dao.repository.mongo;

import com.example.E_Library_API.dao.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Page<Cart> findByUserId(Long id, Pageable pageable);
    Optional<Cart> findByUserId(Long id);
}
