package service;

import dataaccess.*;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import handler.errors.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {

    private GameService gameService;
    private GameDAO gameDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        gameDAO = new MemoryGameDAO();
        gameService = new GameService(gameDAO);
        gameDAO.clear();
    }

    @Test
    void createGameSuccess() throws DataAccessException, BadRequestException {
        GameData newGame = new GameData(0, null, null, "Chess Battle");
        GameData createdGame = gameService.createGame(newGame);

        assertNotNull(createdGame);
        assertEquals("Chess Battle", createdGame.getGameName());
    }

    @Test
    void joinGameSuccess() throws DataAccessException, BadRequestException, UserExistsException {
        GameData newGame = new GameData(0, null, null, "Chess Battle");
        GameData createdGame = gameService.createGame(newGame);

        GameData updatedGame = gameService.joinGame(createdGame.getGameID(), "WHITE", "player1");

        assertEquals("player1", updatedGame.getWhiteUsername());
    }

    @Test
    void getGameSuccess() throws DataAccessException, BadRequestException {
        GameData newGame = new GameData(0, null, null, "Chess Battle");
        GameData createdGame = gameService.createGame(newGame);

        GameData retrievedGame = gameService.getGame(createdGame.getGameID());

        assertNotNull(retrievedGame);
        assertEquals(createdGame.getGameID(), retrievedGame.getGameID());
    }

    @Test
    void getAllGamesSuccess() throws DataAccessException, BadRequestException {
        gameService.createGame(new GameData(0, null, null, "Game 1"));
        gameService.createGame(new GameData(0, null, null, "Game 2"));

        Collection<GameData> games = gameService.getAllGames();

        assertEquals(2, games.size());
    }

    @Test
    void deleteGameSuccess() throws DataAccessException, BadRequestException {
        GameData newGame = new GameData(0, null, null, "Chess Battle");
        GameData createdGame = gameService.createGame(newGame);

        gameService.deleteGame(createdGame.getGameID());

        assertThrows(DataAccessException.class, () -> gameService.getGame(createdGame.getGameID()));
    }

    @Test
    void clearGamesSuccess() throws DataAccessException, BadRequestException {
        gameService.createGame(new GameData(0, null, null, "Chess Battle"));

        gameService.clear();

        assertTrue(gameService.getAllGames().isEmpty());
    }
}