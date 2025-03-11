package handler;

import com.google.gson.Gson;
import handler.errors.BadRequestException;
import handler.errors.UserExistsException;
import model.UserData;
import service.AuthService;
import spark.Request;
import spark.Response;
import service.UserService;
import model.AuthData;
import handler.errors.*;

public class RegisterHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    public Object registerNewUser(Request req, Response res, UserService userService, AuthService authService) {
        res.type("application/json");
        try {
            UserData newUser = serializer.fromJson(req.body(), UserData.class);
            UserData createdUser = userService.createUser(newUser);
            AuthData newAuth = authService.loginUser(createdUser);

            res.status(200);
            return serializer.toJson(newAuth);

        }
        catch (BadRequestException e) {
            return errorHandler.handleError(e, res, 400);
        }
        catch (UserExistsException e) {
            return errorHandler.handleError(e, res, 403);
        }
        catch (Exception e) {
            return errorHandler.handleError(e, res, 500);
        }
    }
}