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
    void testCreateAuthFailure() {
        assertThrows(DataAccessException.class, () -> authDAO.createAuth((String) null));
    }


    @Test
    void testGetAuthFailure() throws DataAccessException {
        assertNull(authDAO.getAuth("invalidToken"));
    }

}