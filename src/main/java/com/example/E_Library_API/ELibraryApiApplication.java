package com.example.E_Library_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.E_Library_API.dao.repository.jpa")
@EnableMongoRepositories(basePackages = "com.example.E_Library_API.dao.repository.mongo")
public class ELibraryApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ELibraryApiApplication.class, args);
    }
}
