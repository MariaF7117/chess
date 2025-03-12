package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SQLGameDAOTests {
    private GameDAO gameDAO;
    @BeforeEach
    void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clear();
    }

    @Test
    void testCreateGameSuccess() throws NullPointerException, DataAccessException {
        GameData game = new GameData(2,"I am white Username","I am black username","This is test gameName",new ChessGame());
        GameData createdGame = gameDAO.createGame(game);
        assertNotNull(createdGame);
        assertEquals("This is test gameName", createdGame.getGameName());
    }

    @Test
    void testCreateGameFailure() {
        assertThrows(NullPointerException.class, () -> gameDAO.createGame(null));
    }

    @Test
    void testGetGameSuccess() throws DataAccessException {
        GameData game = new GameData(2,"I am white Username","I am black username","This is test gameName",new ChessGame());
        GameData createdGame = gameDAO.createGame(game);
        assertNotNull(gameDAO.getGame(createdGame.getGameID()));
    }

    @Test
    void testGetGameFailure() throws DataAccessException {
        assertNull(gameDAO.getGame(-1));
    }



}
