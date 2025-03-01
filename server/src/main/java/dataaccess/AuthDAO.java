package dataaccess;
import model.UserData;
import model.AuthData;
import java.util.Optional;

public interface AuthDAO {
    AuthData createAuth(String username) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
    AuthData getAuthByToken(String authToken);
}