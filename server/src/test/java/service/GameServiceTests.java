package service;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {
    private GameDAO gameDAO;
    @BeforeEach
    void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clear();
    }

    @Test
    void testCreateGame_Success() throws NullPointerException, DataAccessException {
        GameData game = new GameData(2,"I am white Username","I am black username","This is test gameName",new ChessGame());
        GameData createdGame = gameDAO.createGame(game);
        assertNotNull(createdGame);
        assertEquals("This is test gameName", createdGame.getGameName());
    }

    @Test
    void testCreateGame_Failure() {
        assertThrows(NullPointerException.class, () -> gameDAO.createGame(null));
    }

    @Test
    void testGetGame_Success() throws DataAccessException {
        GameData game = new GameData(2,"I am white Username","I am black username","This is test gameName",new ChessGame());
        GameData createdGame = gameDAO.createGame(game);
        assertNotNull(gameDAO.getGame(createdGame.getGameID()));
    }

    @Test
    void testGetGame_Failure() throws DataAccessException {
        assertNull(gameDAO.getGame(-1));
    }

}
