package handler;

import com.google.gson.Gson;
import model.GameData;
import spark.Request;
import spark.Response;
import service.AuthService;
import service.GameService;

public class JoinGameHandler {
    private final Gson serializer = new Gson();

    public Object joinGame(Request req, Response res, AuthService authService, GameService gameService) {
        res.type("application/json");
        try {
            String authToken = req.headers("authorization");
            authService.validate(authToken);
            JoinGame joinGame = serializer.fromJson(req.body(), JoinGame.class);
            GameData gameData = new GameData(joinGame.gameID, joinGame.playerColor, joinGame.otherPlayerColor, joinGame.gameName);
            res.status(200);
            return serializer.toJson(new Object());
        } catch (Exception e) {
            return new ErrorHandler().handleError(e, res, 400);
        }
    }

    private static class JoinGame {
        String playerColor;
        String otherPlayerColor;
        int gameID;
        String gameName;
    }
}