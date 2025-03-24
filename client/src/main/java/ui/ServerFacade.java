package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.AuthData;
import model.GameData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ServerFacade {
    private final HttpClient httpClient;
    private final Gson gson;
    private final String serverUrl;

    public ServerFacade() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.serverUrl = "http://localhost:8080";
    }

    public AuthData register(String username, String password, String email) throws Exception {
        return sendRequest("POST", "/user", Map.of("username", username, "password", password, "email", email), null, AuthData.class);
    }

    public AuthData login(String username, String password) throws Exception {
        return sendRequest("POST", "/session", Map.of("username", username, "password", password), null, AuthData.class);
    }

    public void logout(String authToken) {
        try {
            sendRequest("DELETE", "/session", null, authToken, Void.class);
        } catch (Exception e) {
            handleException(e, "Logout failed");
        }
    }

    public GameData createGame(String gameName, String authToken) throws Exception {
        return sendRequest("POST", "/game", Map.of("gameName", gameName), authToken, GameData.class);
    }

    public GameData joinGame(int gameID, ChessGame.TeamColor color, String authToken) throws Exception {
        return sendRequest("PUT", "/game", Map.of("playerColor", color, "gameID", gameID), authToken, GameData.class);
    }

    public GameData[] listGames(String authToken) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(serverUrl + "/game"))
                    .header("Authorization", authToken)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validateResponse(response);

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            return gson.fromJson(jsonObject.get("games"), GameData[].class);
        } catch (Exception e) {
            handleException(e, "Failed to list games");
            return new GameData[0];
        }
    }

    private <T> T sendRequest(String method, String endpoint, Object body, String authToken, Class<T> responseType) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(new URI(serverUrl + endpoint))
                .method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(gson.toJson(body), StandardCharsets.UTF_8))
                .header("Content-Type", "application/json");

        if (authToken != null) {
            requestBuilder.header("Authorization", authToken);
        }

        HttpResponse<String> response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        validateResponse(response);

        return responseType == Void.class ? null : gson.fromJson(response.body(), responseType);
    }

    private void validateResponse(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP request failed with status: " + response.statusCode() + " and body: " + response.body());
        }
    }
    public void clear() throws Exception {
        sendRequest("DELETE", "/db", null, null, null);
    }

        private void handleException(Exception e, String message) {
        System.err.println(message + ": " + e.getMessage());
    }
}