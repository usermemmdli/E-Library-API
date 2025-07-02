package com.example.E_Library_API.mapper;

import com.example.E_Library_API.dao.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersMapperTest {
    @Autowired
    private UsersMapper usersMapper;

    @Test
    void testToUsersAccountsResponse() {
        //Arrange
        var request = new Users();
        request.setUsername("username");
        request.setPhoneNumber("23231");
        request.setEmail("email@email");
        request.setAddress("address");

        //Actual
        var actual = usersMapper.toUsersAccountsResponse(request);

        //Assert
        assert actual.getUsername().equals("username");
        assert actual.getPhoneNumber().equals("23231");
        assert actual.getEmail().equals("email@email");
        assert actual.getAddress().equals("address");
    }
}
