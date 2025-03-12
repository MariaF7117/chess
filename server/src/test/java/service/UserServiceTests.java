package service;

import dataaccess.*;
import handler.errors.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {

    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = new MemoryUserDAO();
        userService = new UserService();
        userDAO.clear();
    }

    @Test
    void createUserFailureEmptyUsername() {
        UserData user = new UserData("", "password", "email@example.com");
        assertThrows(BadRequestException.class, () -> userService.createUser(user));
    }


    @Test
    void isValidPasswordFailureInvalidPassword() throws DataAccessException, BadRequestException, UserExistsException {
        UserData user = new UserData("testUser", "password", "email@example.com");
        userService.createUser(user);

        UserData wrongUser = new UserData("testUser", "wrongPassword", "email@example.com");
        assertThrows(UnauthorizedException.class, () -> userService.isValidPassword(wrongUser));
    }

}