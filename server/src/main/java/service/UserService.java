package service;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {
    private UserDAO userDAO;
    private AuthDAO authDAO;

    public UserData register(UserData registerUser) throws DataAccessException {
        if (userDAO.getUser(registerUser.getUsername()) != null) {
            throw new DataAccessException("User already exists");
        }
        else if (registerUser.getPassword() == null || registerUser.getPassword().isEmpty()) {
            throw new DataAccessException("empty password");
        }
        else {
            userDAO.createUser(registerUser);
            return userDAO.getUser(registerUser.getUsername());
        }
    }

    public AuthData login(AuthData loggedInUser) throws DataAccessException {
        authDAO.deleteAuth(loggedInUser.authToken());
        return authDAO.getAuth(loggedInUser.authToken());
    }
    public void logout(AuthData loggedOutUser) throws DataAccessException {
        authDAO.deleteAuth(loggedOutUser.authToken());
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
    }
}