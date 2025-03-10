package service;

import dataaccess.*;
import handler.errors.UnauthorizedException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class AuthService {
    private AuthDAO authDAO;

    public AuthService() {
        try{
            authDAO = new SQLAuthDAO();
        }
        catch(Exception e){
            authDAO = new MemoryAuthDAO();
        }
    }

    //why am I not calling createAuth???
    public AuthData createAuth(AuthData authData) throws DataAccessException {
        if (authData.authToken() == null || authData.authToken().isEmpty()) {
            throw new DataAccessException("Invalid auth token");
        }
        authDAO.createAuth(String.valueOf(authData));
        return authDAO.getAuth(authData.authToken());
    }
    public AuthData login(UserData user) throws DataAccessException {
        AuthData existingAuth = authDAO.getAuthByUsername(user.getUsername());
        if (existingAuth != null) {
            authDAO.deleteAuth(existingAuth.authToken());
        }
        return authDAO.createAuth(user.getUsername());
    }

    public AuthData getUserByAuthToken(String authToken) throws DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        System.out.println("Looking up auth token: " + authToken);
        if (auth == null) {
            System.out.println("Auth token not found!");
        } else {
            System.out.println("Auth found for user: " + auth.getUsername());
        }
        return auth;
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

    public String validate(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null || !authData.authToken().equals(authToken)) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        return authToken;
    }


    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void clear() throws Exception {
        authDAO.clear();
    }
}