//package service;
//
//import dataaccess.*;
//import model.UserData;
//import model.AuthData;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import handler.errors.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserServiceTests {
//
//    private UserService userService;
//    private AuthService authService;
//    private UserDAO userDAO;
//
//    @BeforeEach
//    public void setup() throws DataAccessException {
//        userDAO = new MemoryUserDAO();
//        userService = new UserService();
//        authService = new AuthService();
//        userDAO.clear();
//        authService.clear();
//    }
//
//    @Test
//    void createUserSuccess() throws DataAccessException, BadRequestException, UserExistsException {
//        UserData user = new UserData("testUser", "password", "email@example.com");
//
//        AuthData authData = userService.createUser(user);
//
//        assertNotNull(authData);
//        assertNotNull(authData.authToken());
//    }
//
//    @Test
//    void createUserFailsIfUserExists() throws DataAccessException, BadRequestException, UserExistsException {
//        UserData user = new UserData("testUser", "password", "email@example.com");
//        userService.createUser(user);
//
//        assertThrows(UserExistsException.class, () -> userService.createUser(user));
//    }
//
//    @Test
//    void loginSuccess() throws DataAccessException, BadRequestException, UserExistsException, UnauthorizedException {
//        UserData user = new UserData("testUser", "password", "email@example.com");
//        userService.createUser(user);
//
//        AuthData loggedInUser = userService.login(user);
//
//        assertNotNull(loggedInUser);
//        assertEquals("testUser", loggedInUser.getUsername());
//    }
//
//    @Test
//    void loginFailsWithWrongPassword() throws DataAccessException, BadRequestException, UserExistsException {
//        UserData user = new UserData("testUser", "password", "email@example.com");
//        userService.createUser(user);
//
//        UserData wrongPasswordUser = new UserData("testUser", "wrongPassword", "email@example.com");
//
//        assertThrows(UnauthorizedException.class, () -> userService.login(wrongPasswordUser));
//    }
//
//    @Test
//    void loginFailsIfUserDoesNotExist() {
//        UserData nonExistentUser = new UserData("fakeUser", "password", "email@example.com");
//
//        assertThrows(UnauthorizedException.class, () -> userService.login(nonExistentUser));
//    }
//
//    @Test
//    void clearUserSuccess() throws DataAccessException, BadRequestException, UserExistsException {
//        userService.createUser(new UserData("testUser", "password", "email@example.com"));
//
//        userService.clear();
//
//        assertThrows(UnauthorizedException.class, () -> userService.login(new UserData("testUser", "password", null)));
//    }
//}
