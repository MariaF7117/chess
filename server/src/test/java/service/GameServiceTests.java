package service;

import dataaccess.*;
import handler.errors.BadRequestException;
import handler.errors.UserExistsException;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {

    private GameService gameService;
    private GameDAO gameDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        gameDAO = new MemoryGameDAO();
        gameService = new GameService();
        gameDAO.clear();
    }


    @Test
    void createGameFailureInvalidName() {
        GameData game = new GameData(1, "", null, null, null);
        assertThrows(BadRequestException.class, () -> gameService.createGame(game));
    }


    @Test
    void joinGameFailureInvalidColor() throws DataAccessException {
        GameData game = gameDAO.createGame(new GameData(1, "testGame", null, null, null));

        assertThrows(BadRequestException.class, () -> gameService.joinGame(game.getGameID(), "BLUE", "testUser"));
    }


    @Test
    void clearGameSuccess() throws DataAccessException {
        gameService.clear();
        assertEquals(0, gameService.getAllGames().size());
    }
}
