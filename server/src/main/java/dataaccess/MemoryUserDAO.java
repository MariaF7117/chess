package dataaccess;

import model.UserData;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryUserDAO implements UserDAO{

    private final Map<String, UserData> users = new HashMap<>();

    public MemoryUserDAO() {}

    @Override
    public UserData createUser(UserData user) throws DataAccessException {

        //why is newUser mad at me?
        UserData newUser = new UserData(user.getUsername(), user.getPassword(), user.getEmail());
        users.put(newUser.getUsername(), newUser);
        return newUser;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    @Override
    public void clear() throws DataAccessException {
        users.clear();
    }
}
