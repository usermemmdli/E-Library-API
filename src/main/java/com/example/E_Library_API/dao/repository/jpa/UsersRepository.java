package com.example.E_Library_API.dao.repository.jpa;

import com.example.E_Library_API.dao.entity.Users;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String currentUserEmail);

    boolean existsByEmail(@NotNull String email);
}
