package handler;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.AuthService;
import service.GameService;

public class ListGamesHandler {
    private final Gson serializer = new Gson();

    public Object listGames(Request req, Response res, AuthService authService, GameService gameService) {
        res.type("application/json");
        try {
            String authToken = req.headers("authorization");
            authService.validate(authToken);
            res.status(200);
            return serializer.toJson(gameService.getAllGames());
        } catch (Exception e) {
            return new ErrorHandler().handleError(e, res, 401);
        }
    }
}