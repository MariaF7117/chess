package dataaccess;

import model.AuthData;

public class SQLAuthDAO implements AuthDAO{
    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public AuthData getAuthByToken(String authToken) {
        return null;
    }

    @Override
    public AuthData getAuthByUsername(String username) throws DataAccessException {
        return null;
    }
}
