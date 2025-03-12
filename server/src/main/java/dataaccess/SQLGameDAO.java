package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO {
    private final Gson gson = new Gson();

    public SQLGameDAO() throws DataAccessException {
        DatabaseManager.configureDatabase(createStatements);

    }

    @Override
    public GameData createGame(GameData gameData) throws DataAccessException {
        ChessGame game = new ChessGame();
        int gameID = 0;
        String gameJson = gson.toJson(game);
        String sql = "INSERT INTO games (whiteUsername, blackUsername, gameName, chessGame) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, null);
                ps.setString(2, null);
                ps.setString(3, gameData.getGameName());
                ps.setString(4, gameJson);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        gameID = generatedId;
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Can't Create without adequate data.");
        }

        if (gameID == 0) {
            throw new DataAccessException("DataAccessException");
        }

        GameData newGameData = new GameData(
                gameID,
                null,
                null,
                gameData.getGameName(),
                game
        );

        return newGameData;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, chessGame FROM games WHERE gameID=?";
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
        String gameJson = rs.getString("chessGame");

        ChessGame chessGame = gson.fromJson(gameJson, ChessGame.class);

        whiteUsername = (whiteUsername != null) ? whiteUsername : null;
        blackUsername = (blackUsername != null) ? blackUsername : null;

        return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
    }

    @Override
    public GameData updateGame(GameData game) throws DataAccessException {
        String gameJson = String.valueOf(gson.toJsonTree(game));
        var statement = "UPDATE games SET whiteUsername = ?, blackUsername = ?, gameName = ?, chessGame = ? WHERE gameID = ?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, game.getWhiteUsername());
                ps.setString(2, game.getBlackUsername());
                ps.setString(3, game.getGameName());
                ps.setString(4, gameJson);
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
                    if (param instanceof String p) {ps.setString(i + 1, p);}
                    else if (param instanceof Integer p) {ps.setInt(i + 1, p);}
                    else if (param instanceof Long p) {ps.setLong(i + 1, p);}
                    else if (param instanceof Double p) {ps.setDouble(i + 1, p);}
                    else if (param instanceof Boolean p) {ps.setBoolean(i + 1, p);}
                    else if (param == null) {ps.setNull(i + 1, NULL);}
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
              gameID SERIAL,
              whiteUsername VARCHAR(256),
              blackUsername VARCHAR(256),
              gameName VARCHAR(256) NOT NULL,
              chessGame JSON NOT NULL,
              PRIMARY KEY (gameID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}