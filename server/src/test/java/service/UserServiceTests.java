package service;

import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import handler.errors.*;

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
    void createUserSuccess() throws DataAccessException, BadRequestException, UserExistsException {
        UserData user = new UserData("testUser", "password", "email@example.com");
        UserData createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
    }

    @Test
    void isValidUserSuccess() throws DataAccessException, BadRequestException, UserExistsException {
        UserData user = new UserData("testUser", "password", "email@example.com");
        userService.createUser(user);

        assertDoesNotThrow(() -> userService.isValidUser(user));
    }

    @Test
    void isValidPasswordSuccess() throws DataAccessException, BadRequestException, UserExistsException, UnauthorizedException {
        UserData user = new UserData("testUser", "password", "email@example.com");
        userService.createUser(user);

        assertDoesNotThrow(() -> userService.isValidPassword(user));
    }

    @Test
    void clearUserSuccess() throws DataAccessException, BadRequestException, UserExistsException {
        userService.createUser(new UserData("testUser", "password", "email@example.com"));

        userService.clear();
//changed this to UnothorizedException instead of DataAccessException... Hope this doesn't break anything in the future...
        assertThrows(UnauthorizedException.class, () -> userService.isValidUser(new UserData("testUser", "password", null)));
    }
}
