package handler;

import service.GameService;
import spark.Request;
import spark.Response;
import service.UserService;
import service.AuthService;
import com.google.gson.Gson;

public class ClearApplicationHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    public Object clearApplication(Response res, UserService userService, GameService gameService, AuthService authService){
        res.type("application/json");
        try {
            userService.clear();
            gameService.clear();
            authService.clear();
            res.status(200);
            return serializer.toJson(new Object());
        } catch (Exception e) {
            return errorHandler.handleError(e, res, 500);
        }
    }
}
