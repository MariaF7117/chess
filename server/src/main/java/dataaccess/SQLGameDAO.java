package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{
    private final Gson gson = new Gson();

    public SQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        String sql = "INSERT INTO games (title, description) VALUES (?, ?)";
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            throw new DataAccessException("Unable to create database");
        }
        var statement = "INSERT INTO games (gameName, gameState) VALUES (?, ?)";
        var gameStateJson = gson.toJson(game.getGame());
        var id = executeUpdate(statement, game.getGameName(), gameStateJson);
        game.setGameID(id);
        return game;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, gameName, whiteUsername, blackUsername, gameState FROM games WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Unable to read game data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        ArrayList<Integer> gameIDs = getAllGameIDs();
        ArrayList<GameData> games = new ArrayList<>();

        // Loop through each game ID
        for (int gameID : gameIDs) {
            GameData gameData = getGame(gameID);
            if (gameData != null) {
                games.add(gameData);
            }
        }

        return games;
    }
    private ArrayList<Integer> getAllGameIDs() throws DataAccessException {
        String sql = "SELECT gameID FROM games";
        ArrayList<Integer> gameIDs = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                gameIDs.add(rs.getInt("gameID"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to get all games IDs: " + e.getMessage());
        }
        return gameIDs;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var gameName = rs.getString("gameName");

        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");

        whiteUsername = (whiteUsername != null) ? whiteUsername : null;
        blackUsername = (blackUsername != null) ? blackUsername : null;

        return new GameData(gameID,whiteUsername, blackUsername, gameName);
    }

    @Override
    public GameData updateGame(GameData game) throws DataAccessException {
        var statement = "UPDATE games SET gameName = ?, gameState = ?, whiteUsername = ?, blackUsername = ? WHERE gameID = ?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, game.getGameName());
                ps.setString(2, serializeGame(game.getGame()));
                ps.setString(3, game.getWhiteUsername());
                ps.setString(4, game.getBlackUsername());
                ps.setInt(5, game.getGameID());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DataAccessException("Game update failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating game: " + e.getMessage());
        }
        return game;
    }

    private String serializeGame(ChessGame game) {
        try{
            return game.toString();
        }
        catch (Exception e) {
            return new DataAccessException(String.format("Unable to serialize game: %s", e.getMessage())).toString();
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM games";
        executeUpdate(statement);
    }


    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof Long p) ps.setLong(i + 1, p);
                    else if (param instanceof Double p) ps.setDouble(i + 1, p);
                    else if (param instanceof Boolean p) ps.setBoolean(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();
                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to update database: " + e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              gameID INT AUTO_INCREMENT,
              gameName VARCHAR(256) NOT NULL,
              whiteUsername VARCHAR(256) DEFAULT NULL,
              blackUsername VARCHAR(256) DEFAULT NULL,
              gameState TEXT NOT NULL,
              PRIMARY KEY (gameID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to configure database: " + ex.getMessage());
        }
    }
}
