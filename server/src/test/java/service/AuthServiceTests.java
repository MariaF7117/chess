package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTests {

    private AuthService authService;
    private AuthDAO authDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new MemoryAuthDAO();
        authService = new AuthService();
        authDAO.clear();
    }


    @Test
    void loginSuccess() throws DataAccessException {
        UserData user = new UserData("testUser", "password", "email@example.com");
        AuthData authData = authService.login(user);

        assertNotNull(authData);
        assertNotNull(authData.authToken());
    }


    @Test
    void clearAuthSuccess() throws Exception {
        authService.createAuth(new AuthData(AuthService.generateToken(), "testUser"));

        authService.clear();

        assertNull(authService.getUserByAuthToken("invalidToken"));
    }
}