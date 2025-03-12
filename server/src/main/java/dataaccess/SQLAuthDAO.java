package dataaccess;

import model.AuthData;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    public SQLAuthDAO() throws DataAccessException {
        DatabaseManager.configureDatabase(createStatements);
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        var authToken = UUID.randomUUID().toString();
        var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        executeUpdate(statement, authToken, username);
        return new AuthData(authToken, username);
    }

    @Override
    public AuthData createAuth(AuthData authData) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username FROM auth WHERE authToken = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new AuthData(authToken, rs.getString("username"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to read auth data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken = ?";
        executeUpdate(statement, authToken);
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM auth";
        executeUpdate(statement);
    }

    @Override
    public AuthData getAuthByToken(String authToken) throws DataAccessException {
        return getAuth(authToken);
    }

    @Override
    public AuthData getAuthByUsername(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM auth WHERE authToken = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, getAuthByToken(username).getAuthToken());
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new AuthData(rs.getString("authToken"), username);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving auth by username: " + e.getMessage());
        }
        return null;
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) {ps.setString(i + 1, p);}
                    else if (param instanceof Integer p) {ps.setInt(i + 1, p);}
                    else if (param instanceof Long p) {ps.setLong(i + 1, p);}
                    else if (param instanceof Double p) {ps.setDouble(i + 1, p);}
                    else if (param instanceof Boolean p) {ps.setBoolean(i + 1, p);}
                    else if (param == null) {ps.setNull(i + 1, java.sql.Types.NULL);}
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to update database: " + e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
        CREATE TABLE IF NOT EXISTS auth (
          authToken VARCHAR(256) NOT NULL,
          username VARCHAR(256) NOT NULL,
          PRIMARY KEY (authToken),
          FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """
    };

}
