package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTests{
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        authDAO = new SQLAuthDAO();
        gameDAO = new SQLGameDAO();
        userDAO = new SQLUserDAO();
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
    }
        @Test
    void testCreateAuth_Success() throws DataAccessException {
        AuthData auth = authDAO.createAuth("testUser");
        assertNotNull(auth);
        assertEquals("testUser", auth.getUsername());
    }

    @Test
    void testCreateAuth_Failure() {
        assertThrows(DataAccessException.class, () -> authDAO.createAuth((String) null));
    }

    @Test
    void testGetAuth_Success() throws DataAccessException {
        AuthData auth = authDAO.createAuth("testUser");
        assertNotNull(authDAO.getAuth(auth.getAuthToken()));
    }

    @Test
    void testGetAuth_Failure() throws DataAccessException {
        assertNull(authDAO.getAuth("invalidToken"));
    }

    @Test
    void testDeleteAuth_Success() throws DataAccessException {
        AuthData auth = authDAO.createAuth("testUser");
        authDAO.deleteAuth(auth.getAuthToken());
        assertNull(authDAO.getAuth(auth.getAuthToken()));
    }
}