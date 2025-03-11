package dataaccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuth(String username) throws DataAccessException;

    AuthData createAuth(AuthData authData) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;

    AuthData getAuthByToken(String authToken) throws DataAccessException;

    AuthData getAuthByUsername(String username) throws DataAccessException;
}