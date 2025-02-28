package handler;
import service.GameService;
import spark.*;
import service.UserService;
import com.google.gson.Gson;
import service.AuthService;

import javax.swing.text.html.HTMLEditorKit;


public class ClearApplicationHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler = new

    public Object clearApplication(Response res, UserService userService, GameService gameService, AuthService authService){
        res.type("application/json");
        try {
            userService.clear();
            gameService.clear();
            authService.clear();
            res.status(200);
            res.body("");
            return serializer.toJson(new Object());
        }
        catch (Exception e) {
            return errorHandler.handleError(e, res, 500);
        }
    }
}
