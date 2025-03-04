package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import handler.errors.*;

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
//
//    @Test
//    void createAuth_Success() throws DataAccessException {
//        AuthData authData = new AuthData(AuthService.generateToken(), "testUser");
//        AuthData createdAuth = authService.createAuth(authData);
//
//        assertNotNull(createdAuth);
//        assertEquals(authData.getUsername(), createdAuth.getUsername());
//    }

    @Test
    void login_Success() throws DataAccessException {
        UserData user = new UserData("testUser", "password", "email@example.com");
        AuthData authData = authService.login(user);

        assertNotNull(authData);
        assertNotNull(authData.authToken());
    }

//    @Test
//    void getUserByAuthToken_Success() throws DataAccessException {
//        AuthData authData = authService.createAuth(new AuthData(AuthService.generateToken(), "testUser"));
//
//        AuthData retrievedAuth = authService.getUserByAuthToken(authData.authToken());
//
//        assertNotNull(retrievedAuth);
//        assertEquals(authData.getUsername(), retrievedAuth.getUsername());
//    }
//
//    @Test
//    void deleteAuth_Success() throws DataAccessException, UnauthorizedException {
//        AuthData authData = authService.createAuth(new AuthData(AuthService.generateToken(), "testUser"));
//        authService.deleteAuth(authData.authToken());
//        assertThrows(DataAccessException.class, () -> authService.getUserByAuthToken(authData.authToken()));
//    }
//
//    @Test
//    void validateAuthToken_Success() throws DataAccessException, UnauthorizedException {
//        AuthData authData = authService.createAuth(new AuthData(AuthService.generateToken(), "testUser"));
//
//        String validatedToken = authService.validate(authData.authToken());
//
//        assertEquals(authData.authToken(), validatedToken);
//    }

    @Test
    void clearAuth_Success() throws Exception {
        authService.createAuth(new AuthData(AuthService.generateToken(), "testUser"));

        authService.clear();

        assertNull(authService.getUserByAuthToken("invalidToken"));
    }
}
