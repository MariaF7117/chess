package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class AuthService {
    private AuthDAO authDAO;
    private UserDAO userDAO;

    public AuthData createAuth(AuthData authData) throws DataAccessException {
        if (authData.authToken() == null || authData.authToken().isEmpty()) {
            throw new DataAccessException("Invalid auth token");
        }
        authDAO.createAuth(String.valueOf(authData));
        return authDAO.getAuth(authData.authToken());
    }
    public AuthData login(UserData user) throws DataAccessException {
        return authDAO.createAuth(user.getUsername());
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authDAO.getAuth(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        authDAO.deleteAuth(authToken);
    }

    public String validate(String authToken) throws Exception {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null) {
            throw new Exception("Error: unauthorized");
        }
        return authToken;
    }


    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void clear() throws Exception {
        userDAO.clear();
        authDAO.clear();
    }
}