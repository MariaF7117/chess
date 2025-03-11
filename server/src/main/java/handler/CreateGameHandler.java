package handler;

import com.google.gson.Gson;
import model.GameData;
import spark.Request;
import spark.Response;
import service.AuthService;
import handler.errors.*;
import service.GameService;

public class CreateGameHandler {
    private final Gson serializer = new Gson();
    public ErrorHandler errorHandler = new ErrorHandler();

    public Object createGame(Request req, Response res, AuthService authService, GameService gameService) {
        res.type("application/json");
        try {
            String authToken = req.headers("authorization");
            if (authToken == null || authToken.isEmpty()) {
                throw new UnauthorizedException("no token");
            }
            authService.validate(authToken);

            GameData newGame = serializer.fromJson(req.body(), GameData.class);
            GameData createdGame = gameService.createGame(newGame);
            if (createdGame == null) {
                throw new BadRequestException("Failed to create game");
            }
            res.status(200);
            return serializer.toJson(new GameResponse(createdGame.getGameID()));
        }
        catch (UnauthorizedException exception) {
            return errorHandler.handleError(exception, res, 401);
        }
        catch (BadRequestException exception) {
            return errorHandler.handleError(exception, res, 400);
        }
        catch (Exception exception) {
            return errorHandler.handleError(exception, res, 500);
        }
    }

    private static class GameResponse {
        int gameID;
        GameResponse(int gameID) { this.gameID = gameID; }
    }
}