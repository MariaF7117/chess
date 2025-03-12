package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        userDAO = new SQLUserDAO();
        userDAO.clear();
    }

    @Test
    void testCreateUser_Success() throws DataAccessException {
        UserData user = new UserData("testUser", "password", "email@test.com");
        UserData createdUser = userDAO.createUser(user);
        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
    }

    @Test
    void testCreateUser_Failure() throws DataAccessException {
        UserData user = new UserData("testUser", "password", "email@test.com");
        userDAO.createUser(user);
        assertThrows(DataAccessException.class, () -> userDAO.createUser(user));
    }

    @Test
    void testGetUser_Success() throws DataAccessException {
        UserData user = new UserData("testUser", "password", "email@test.com");
        userDAO.createUser(user);
        assertNotNull(userDAO.getUser("testUser"));
    }

    @Test
    void testGetUser_Failure() throws DataAccessException {
        assertNull(userDAO.getUser("nonExistentUser"));
    }
}
