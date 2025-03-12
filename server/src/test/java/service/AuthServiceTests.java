package service;

import dataaccess.*;
import handler.errors.UnauthorizedException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTests {

    private AuthService authService;
    private AuthDAO authDAO;
    private UserDAO userDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        authService = new AuthService();
        authDAO.clear();
        userDAO.clear();
    }



    @Test
    void loginFailureUserNotFound() {
        UserData user = new UserData("nonexistentUser", "password", "email@example.com");

        assertThrows(DataAccessException.class, () -> authService.loginUser(user));
    }



    @Test
    void deleteAuthFailureInvalidToken() {
        assertThrows(UnauthorizedException.class, () -> authService.deleteAuth("invalidToken"));
    }

    @Test
    void clearAuthSuccess() throws Exception {
        authService.clear();
        assertNull(authService.getUserByAuthToken("invalidToken"));
    }
}
