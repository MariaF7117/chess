package dataaccess;

import model.UserData;
import java.util.Optional;

public interface UserDAO {
    void createUser(UserData user) throws DataAccessException;
    Optional<UserData> getUser(String username) throws DataAccessException;
    void clear() throws DataAccessException;
}
