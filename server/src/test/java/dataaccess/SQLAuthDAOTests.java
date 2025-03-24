package dataaccess;

import model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SQLAuthDAOTests {

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


        //retrieve me a game that is not there for a negative test. - throw exception not in database

    }

