package service;

import dataaccess.*;
import handler.errors.UnauthorizedException;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class AuthService {
    private AuthDAO authDAO;
    private UserDAO userDAO;

    public AuthService() {
        try{
            authDAO = new SQLAuthDAO();
            userDAO = new SQLUserDAO();
        }
        catch(Exception e){
            authDAO = new MemoryAuthDAO();
            userDAO = new MemoryUserDAO();
        }
    }

//    public AuthData createAuth(AuthData authData) throws DataAccessException {
//        if (authData.authToken() == null || authData.authToken().isEmpty()) {
//            throw new DataAccessException("Invalid auth token");
//        }
//        authDAO.createAuth(String.valueOf(authData));
//        return authDAO.getAuth(authData.authToken());
//    }
    public AuthData loginUser(UserData user) throws DataAccessException {
        return authDAO.createAuth(user.getUsername());
    }

    public AuthData getUserByAuthToken(String authToken) throws DataAccessException {
        return authDAO.getAuthByToken(authToken);
    }

    public void deleteAuth(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData authData = authDAO.getAuthByToken(authToken);
        if (authData == null) {
            throw new UnauthorizedException("unauthorized");
        }
        else {
            authDAO.deleteAuth(authToken);
        }
    }

    public void validate(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null || !authData.authToken().equals(authToken)) {
            throw new UnauthorizedException("Error: unauthorized");
        }
    }


    public void clear() throws Exception {
        authDAO.clear();
    }
}