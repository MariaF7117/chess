package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import handler.errors.UnauthorizedException;
import model.GameData;
import spark.Request;
import spark.Response;
import service.AuthService;
import service.GameService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ListGamesHandler {

    private final Gson serializer = new Gson();

    public Object listGames(Request req, Response res, AuthService authService, GameService gameService) {
        res.type("application/json");
        try {
            String authToken = req.headers("authorization");

//            if (authToken == null || authToken.isEmpty()) {
//                System.out.println("[ERROR] No authorization token provided.");
//                throw new UnauthorizedException("No authorization token provided.");
//            }

            authService.validate(authToken);

            Collection<GameData> games = gameService.getAllGames();
//            if (games == null) {
//                games = new ArrayList<>();
//            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            JsonArray jsonArray = gson.toJsonTree(games).getAsJsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("games", jsonArray);
//            String json = gson.toJson(Map.of("games", games));


            res.status(200);
            return serializer.toJson(jsonObject);

        }catch (UnauthorizedException e) {
            System.out.println("[ERROR] Unauthorized: " + e.getMessage());
            return new ErrorHandler().handleError(e, res, 401);
        }
        catch (Exception e) {
            return new ErrorHandler().handleError(e, res, 500);
        }
    }
}