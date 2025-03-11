//package service;
//
//import dataaccess.*;
//import model.AuthData;
//import model.UserData;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import dataaccess.DataAccessException;
//import handler.errors.UnauthorizedException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AuthServiceTests {
//
//    private AuthService authService;
//    private UserService userService;
//    private AuthDAO authDAO;
//    private UserDAO userDAO;
//
//    @BeforeEach
//    public void setup() throws DataAccessException {
//        authDAO = new MemoryAuthDAO();
//        userDAO = new MemoryUserDAO();
//        authService = new AuthService();
//        userService = new UserService();
//        authDAO.clear();
//        userDAO.clear();
//    }
//
//    @Test
//    void createAuthSuccess() throws DataAccessException {
//        AuthData authData = new AuthData(AuthService.generateToken(), "testUser");
//        AuthData createdAuth = authService.createAuth(authData);
//
//        assertNotNull(createdAuth);
//        assertNotNull(createdAuth.authToken());
//        assertEquals("testUser", createdAuth.getUsername());
//    }
//
//    @Test
//    void loginSuccess() throws DataAccessException, UnauthorizedException, handler.errors.BadRequestException, handler.errors.UserExistsException {
//        // Create and register a user
//        UserData user = new UserData("testUser", "password", "email@example.com");
//        userService.createUser(user);
//
//        // Log in user and generate auth token
//        AuthData authData = authService.loginUser(user);
//
//        assertNotNull(authData);
//        assertNotNull(authData.authToken());
//    }
//
//    @Test
//    void getUserByAuthTokenSuccess() throws DataAccessException {
//        AuthData authData = new AuthData(AuthService.generateToken(), "testUser");
//        authService.createAuth(authData);
//
//        AuthData retrievedAuth = authService.getUserByAuthToken(authData.authToken());
//
//        assertNotNull(retrievedAuth);
//        assertEquals("testUser", retrievedAuth.getUsername());
//    }
//
//    @Test
//    void getUserByAuthTokenFailsForInvalidToken() throws DataAccessException {
//        AuthData authData = authService.getUserByAuthToken("invalidToken");
//
//        assertNull(authData);
//    }
//
//    @Test
//    void deleteAuthSuccess() throws DataAccessException, UnauthorizedException {
//        AuthData authData = new AuthData(AuthService.generateToken(), "testUser");
//        authService.createAuth(authData);
//
//        authService.deleteAuth(authData.authToken());
//
//        assertNull(authService.getUserByAuthToken(authData.authToken()));
//    }
//
//    @Test
//    void clearAuthSuccess() throws Exception {
//        authService.createAuth(new AuthData(AuthService.generateToken(), "testUser"));
//
//        authService.clear();
//
//        assertNull(authService.getUserByAuthToken("invalidToken"));
//    }
//}
