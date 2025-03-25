package client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.AuthData;
import model.GameData;
import server.Server;
import ui.ServerFacade;
import chess.ChessGame;

public class ServerFacadeTests {
    private static ServerFacade serverFacade;
    private static Server server;

    @BeforeAll
    public static void init(){
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:0");
    }

    @AfterAll
    static void stopServer() throws Exception {
        serverFacade.clear();
        server.stop();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        AuthData auth = serverFacade.register("testUser0", "password0", "test0@example.com");
        assertNotNull(auth);
        assertNotNull(auth.authToken());
    }

    @Test
    void testRegisterFailure() {
        assertThrows(Exception.class, () -> {
            serverFacade.register("", "password", "test@example.com");
        });
    }

    @Test
    void testLoginSuccess() throws Exception {
        serverFacade.register("testUser1", "password1", "test1@example.com");
        AuthData auth = serverFacade.login("testUser1", "password1");
        assertNotNull(auth);
        assertNotNull(auth.authToken());
    }

    @Test
    void testLoginFailure() {
        assertThrows(Exception.class, () -> {
            serverFacade.login("wrongUser", "password");
        });
    }

    @Test
    void testLogoutSuccess() throws Exception {
        AuthData auth =  serverFacade.register("testUser3", "password3", "test3@example.com");
        serverFacade.login("testUser3", "password3");
        serverFacade.logout(auth.authToken());
        assertDoesNotThrow(() -> serverFacade.logout(auth.authToken()));
    }

    @Test
    void testLogoutFailure() {
        assertDoesNotThrow(() -> serverFacade.logout("invalidToken"));
    }

    @Test
    void testCreateGameSuccess() throws Exception {
        serverFacade.register("testUser9", "password9", "test9@email.com");
        AuthData returnData = serverFacade.login("testUser9", "password9");
        GameData returnGame = serverFacade.createGame("New Game", returnData.authToken());
        assert returnGame != null;
    }

    @Test
    void testCreateGameFailure() {
        assertThrows(Exception.class, () -> {
            serverFacade.createGame("Test Game", "invalidToken");
        });
    }

    @Test
    void testJoinGameSuccess() throws Exception {
        serverFacade.register("testUser6", "password6", "test6@example.com");
        AuthData auth = serverFacade.login("testUser6", "password6");
        GameData game = serverFacade.createGame("Test Game", auth.authToken());
        GameData joinedGame = serverFacade.joinGame(game.getGameID(), ChessGame.TeamColor.WHITE, auth.authToken());
        assertNotNull(joinedGame);
    }

    @Test
    void testJoinGameFailure() {
        assertThrows(Exception.class, () -> {
            serverFacade.joinGame(9999, ChessGame.TeamColor.BLACK, "invalidToken");
        });
    }

    @Test
    void testListGamesSuccess() throws Exception {
        AuthData auth = serverFacade.register("testUser", "password", "test@example.com");
        serverFacade.createGame("Game 1", auth.authToken());
        serverFacade.createGame("Game 2", auth.authToken());
        GameData[] games = serverFacade.listGames(auth.authToken());
        assertTrue(games.length >= 2);
    }

    @Test
    void testListGamesFailure() throws Exception{
        AuthData auth = serverFacade.register("testUser5", "password5", "test5@example.com");
        serverFacade.createGame("Game 1", auth.authToken());
        serverFacade.createGame("Game 2", auth.authToken());
        GameData[] games = serverFacade.listGames(auth.authToken());
        assertTrue(games.length >= 3);
    }
}
