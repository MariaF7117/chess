package dataaccess;

import handler.errors.UnauthorizedException;
import model.AuthData;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.UserData;
import service.AuthService;

public class MemoryAuthDAO implements AuthDAO {

    public MemoryAuthDAO() {}

    private final Map<AuthData, String> authentications = new HashMap<>();

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        AuthData auth = new AuthData(AuthService.generateToken(), username);
        System.out.println("Creating auth for user: " + username + " | Token: " + auth.authToken());
        authentications.put(auth, auth.authToken());
        return auth;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        for (AuthData auth : authentications.keySet()) {
            if (auth.authToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData auth = getAuth(authToken);
        authentications.remove(auth);
    }

    @Override
    public void clear() throws DataAccessException {
        authentications.clear();
    }

    @Override
    public AuthData getAuthByToken(String authToken) {
        for (AuthData auth : authentications.keySet()) {
            if (auth.authToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }
    @Override
    public AuthData getAuthByUsername(String username) throws DataAccessException {
        for (Map.Entry<AuthData, String> entry : authentications.entrySet()) {
            if (entry.getValue().equals(username)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
