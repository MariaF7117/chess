package server;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import handler.ClearApplicationHandler;
import handler.LoginHandler;
import handler.LogoutHandler;
import handler.RegisterHandler;
import handler.ListGamesHandler;
import handler.CreateGameHandler;
import handler.JoinGameHandler;
import spark.*;
import service.GameService;
import service.UserService;
import service.AuthService;


public class Server {

    public int run(int desiredPort){
        UserService userService = new UserService();
        GameService gameService = new GameService();
        AuthService authService = new AuthService();

        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) -> (new ClearApplicationHandler()).clearApplication(res, userService, gameService, authService));
        Spark.post("/user", (req, res) -> (new RegisterHandler()).registerNewUser(req, res, userService, authService));
        Spark.post("/session", (req, res) -> (new LoginHandler()).login(req, res, userService, authService));
        Spark.delete("/session", (req, res) -> (new LogoutHandler()).logout(req, res, authService));
        Spark.get("/game", (req, res) -> (new ListGamesHandler()).listGames(req, res, authService, gameService));
        Spark.post("/game", (req, res) -> (new CreateGameHandler()).createGame(req, res, authService, gameService));
        Spark.put("/game", (req, res) -> (new JoinGameHandler()).joinGame(req, res, authService, gameService));


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}

