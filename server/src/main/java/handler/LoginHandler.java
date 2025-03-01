package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import spark.Request;
import spark.Response;
import service.AuthService;
import service.UserService;
import model.AuthData;
import handler.errors.*;

public class LoginHandler {
    private final Gson serializer = new Gson();

    public Object login(Request req, Response res, UserService userService, AuthService authService) {
        res.type("application/json");
        try {
            UserData user = serializer.fromJson(req.body(), UserData.class);

            AuthData auth = authService.login(user);

            res.status(200);
            return serializer.toJson(auth);
        }
        catch (UnauthorizedException e) {
            return new ErrorHandler().handleError(e, res, 401);
        }
        catch (Exception e) {
            return new ErrorHandler().handleError(e, res, 500);
        }
    }

    private static class LoginRequest {
        String username;
        String password;
    }
}