package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameData;
import spark.Request;
import spark.Response;
import service.AuthService;
import service.GameService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ListGamesHandler {
//    private final Gson serializer = new Gson();
//    ErrorHandler errorHandler = new ErrorHandler();

    public Object listGames(Request req, Response res, AuthService authService, GameService gameService) {
        res.type("application/json");
        try {
            String authToken = req.headers("authorization");
            authService.validate(authToken);

            Collection<GameData> games = gameService.getAllGames();
            if (games == null) {
                games = new ArrayList<>();
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(Map.of("games", games));


            res.status(200);
            return json;

        } catch (Exception e) {
            return new ErrorHandler().handleError(e, res, 401);
        }
    }
}