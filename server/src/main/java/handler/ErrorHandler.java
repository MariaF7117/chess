package handler;
import com.google.gson.Gson;
import spark.Response;

public class ErrorHandler {
    private final Gson serializer = new Gson();
    public String handleError(Exception error, Response res, int statusCode) {
        res.status(statusCode);
        return serializer.toJson(new ErrorResponse(error.getMessage()));
    }
    private static class ErrorResponse {
        String message;
        ErrorResponse(String message) { this.message = message; }
    }
}
