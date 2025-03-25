package client;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;
import model.AuthData;
import model.GameData;
import chess.ChessGame;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private static String authToken;




    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }
    @Test
    public void testRegisterSuccess() throws Exception {
        AuthData authData = serverFacade.register("testUser", "password", "test@example.com");
        assertNotNull(authData);
        assertNotNull(authData.authToken());
        authToken = authData.authToken();
    }




}