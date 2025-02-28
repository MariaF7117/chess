package handler;

import com.google.gson.Gson;
import service.AuthService;
import spark.Request;
import spark.Response;
import service.UserService;
import model.AuthData;

public class RegisterHandler {
    private final Gson serializer = new Gson();

    public Object registerNewUser(Request req, Response res, UserService userService, AuthService authService) {
        res.type("application/json");
        try {
            UserRequest userRequest = serializer.fromJson(req.body(), UserRequest.class);
            AuthData authData = userService.register(userRequest.username, userRequest.password, userRequest.email);
            res.status(200);
            return serializer.toJson(authData);
        } catch (Exception e) {
            return new ErrorHandler().handleError(e, res, 400);
        }
    }

    private static class UserRequest {
        String username;
        String password;
        String email;
    }
}