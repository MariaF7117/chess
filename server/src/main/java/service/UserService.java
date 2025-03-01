package service;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    private UserDAO userDAO;
    private AuthDAO authDAO;

    public UserData createUser(UserData registerUser) throws DataAccessException {
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

    public void logout(AuthData logOutUser) throws DataAccessException {
        authDAO.deleteAuth(logOutUser.authToken());
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
    }
}