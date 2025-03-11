package dataaccess;

import model.AuthData;
import service.AuthService;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {

    private final Map<String, AuthData> authentications = new HashMap<>();

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData createAuth(AuthData authData) throws DataAccessException {
        System.out.println("Creating auth for user: " + authData.getUsername() + " | Token: " + authData.authToken());
        authentications.put(authData.authToken(), authData);
        return authData;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return authentications.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        authentications.remove(authToken);
    }

    @Override
    public void clear() throws DataAccessException {
        authentications.clear();
    }

    @Override
    public AuthData getAuthByToken(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuthByUsername(String username) throws DataAccessException {
        for (AuthData auth : authentications.values()) {
            if (auth.getUsername().equals(username)) {
                return auth;
            }
        }
        return null;
    }
}
