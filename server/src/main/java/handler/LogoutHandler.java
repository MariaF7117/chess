package handler;

import dataaccess.DataAccessException;
import handler.errors.UnauthorizedException;
import spark.Request;
import spark.Response;
import service.AuthService;
import com.google.gson.Gson;

public class LogoutHandler {
    private final Gson serializer = new Gson();
    public ErrorHandler errorHandler = new ErrorHandler();

    public Object logout(Request req, Response res, AuthService authService) throws UnauthorizedException{
        res.type("application/json");
        try {
            String authToken = req.headers("authorization");
            authService.deleteAuth(authToken);
            res.status(200);
            return serializer.toJson(new Object());

        } catch (UnauthorizedException e) {
            return errorHandler.handleError(e, res, 401);
        } catch ( DataAccessException e){
            return errorHandler.handleError(e, res, 500);
        }

    }
}