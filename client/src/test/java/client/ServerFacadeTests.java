package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.AuthData;
import model.GameData;
import ui.ServerFacade;
import chess.ChessGame;

public class ServerFacadeTests {
    private ServerFacade server;

    @BeforeEach
    void setup() throws Exception {
        server = new ServerFacade("http://localhost:8080");
        server.clear();
    }

    @Test
    void testRegister_Success() throws Exception {
        AuthData auth = server.register("testUser", "password", "test@example.com");
        assertNotNull(auth);
        assertNotNull(auth.authToken());
    }

    @Test
    void testRegister_Failure() {
        assertThrows(Exception.class, () -> {
            server.register("", "password", "test@example.com");
        });
    }

    @Test
    void testLogin_Success() throws Exception {
        server.register("testUser", "password", "test@example.com");
        AuthData auth = server.login("testUser", "password");
        assertNotNull(auth);
        assertNotNull(auth.authToken());
    }

    @Test
    void testLogin_Failure() {
        assertThrows(Exception.class, () -> {
            server.login("wrongUser", "password");
        });
    }

    @Test
    void testLogout_Success() throws Exception {
        AuthData auth = server.register("testUser", "password", "test@example.com");
        assertDoesNotThrow(() -> server.logout(auth.authToken()));
    }

    @Test
    void testLogout_Failure() {
        assertDoesNotThrow(() -> server.logout("invalidToken"));
    }

    @Test
    void testCreateGame_Success() throws Exception {
        AuthData auth = server.register("testUser", "password", "test@example.com");
        GameData game = server.createGame("Test Game", auth.authToken());
        assertNotNull(game);
        assertEquals("Test Game", game.getGameName());
    }

    @Test
    void testCreateGame_Failure() {
        assertThrows(Exception.class, () -> {
            server.createGame("Test Game", "invalidToken");
        });
    }

    @Test
    void testJoinGame_Success() throws Exception {
        AuthData auth = server.register("testUser", "password", "test@example.com");
        GameData game = server.createGame("Test Game", auth.authToken());
        GameData joinedGame = server.joinGame(game.getGameID(), ChessGame.TeamColor.WHITE, auth.authToken());
        assertNotNull(joinedGame);
    }

    @Test
    void testJoinGame_Failure() {
        assertThrows(Exception.class, () -> {
            server.joinGame(9999, ChessGame.TeamColor.BLACK, "invalidToken");
        });
    }

    @Test
    void testListGames_Success() throws Exception {
        AuthData auth = server.register("testUser", "password", "test@example.com");
        server.createGame("Game 1", auth.authToken());
        server.createGame("Game 2", auth.authToken());
        GameData[] games = server.listGames(auth.authToken());
        assertTrue(games.length >= 2);
    }

    @Test
    void testListGames_Failure() {
        assertThrows(Exception.class, () -> {
            server.listGames("invalidToken");
        });
    }
}
