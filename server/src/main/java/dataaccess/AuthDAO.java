package dataaccess;

import model.AuthData;
import java.util.Optional;

public interface AuthDAO {
    void createAuth(AuthData auth) throws DataAccessException;
    Optional<AuthData> getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
}