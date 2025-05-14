package com.example.E_Library_API.dao.repository.mongo;

import com.example.E_Library_API.dao.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
}
