package dataaccess;

import model.UserData;
import java.util.Optional;

public interface UserDAO {
    UserData createUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    void clear() throws DataAccessException;
}
