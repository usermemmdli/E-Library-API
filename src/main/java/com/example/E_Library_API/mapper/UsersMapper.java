package com.example.E_Library_API.mapper;

import com.example.E_Library_API.dao.entity.Users;
import com.example.E_Library_API.dto.response.UsersAccountsResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public class UsersMapper {
    public UsersAccountsResponse toUsersAccountsResponse(Users users) {
        return UsersAccountsResponse.builder()
                .username(users.getUsername())
                .phoneNumber(users.getPhoneNumber())
                .email(users.getEmail())
                .address(users.getAddress())
                .build();
    }
}
