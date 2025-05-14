package com.example.E_Library_API.security;

import com.example.E_Library_API.dao.entity.Users;
import com.example.E_Library_API.dao.repository.jpa.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(users.getEmail())
                .password(users.getPassword())
                .authorities("ROLE_" + users.getRoles().getName())
                .build();
    }
}
