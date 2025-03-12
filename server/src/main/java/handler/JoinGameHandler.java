package handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import handler.errors.BadRequestException;
import handler.errors.UnauthorizedException;
import handler.errors.UserExistsException;
import model.AuthData;
import model.GameData;
import spark.Request;
import spark.Response;
import service.AuthService;
import service.GameService;

public class JoinGameHandler {
    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    public Object joinGame(Request req, Response res, AuthService authService, GameService gameService) {
        res.type("application/json");
        try {
            String authToken = req.headers("Authorization");
            authService.validate(authToken);
            AuthData user = authService.getUserByAuthToken(authToken);

            String username = user.getUsername();
            JsonElement bodyJsonElement = JsonParser.parseString(req.body());
            JsonObject jsonObject = bodyJsonElement.getAsJsonObject();

            String playerColor;
            int gameId;
            try {
                playerColor = jsonObject.get("playerColor").getAsString();
                gameId = jsonObject.get("gameID").getAsInt();
            }
            catch (Exception e) {
                return errorHandler.handleError(e, res, 400);
            }
            GameData gameData = gameService.joinGame(gameId, playerColor, username);
            res.status(200);
            return serializer.toJson(gameData);
        }
        catch (BadRequestException e) {
            return errorHandler.handleError(e, res, 400);
        }
        catch (UserExistsException e) {
            return errorHandler.handleError(e, res, 403);
        }
        catch (UnauthorizedException e) {
            return errorHandler.handleError(e, res, 401);
        }
        catch (Exception e) {
            return errorHandler.handleError(e, res, 500);
        }
    }

}