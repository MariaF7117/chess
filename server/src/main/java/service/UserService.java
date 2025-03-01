package service;
import dataaccess.*;
import model.UserData;
import dataaccess.MemoryUserDAO;
import handler.errors.*;


public class UserService {
    private UserDAO userDAO = new MemoryUserDAO();
    //private AuthDAO authDAO = new MemoryAuthDAO();

    public UserData createUser(UserData registerUser) throws DataAccessException, BadRequestException, UserExistsException {

        if (registerUser.getUsername() == null || registerUser.getUsername().isEmpty()) {
            throw new BadRequestException("Username cannot be empty");
        }
        if (registerUser.getPassword() == null || registerUser.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be empty"); // Throwing BadRequestException
        }
        UserData user = userDAO.getUser(registerUser.getUsername());
        if (user != null) {
            throw new UserExistsException("User already exists");
        }
        else {
            userDAO.createUser(registerUser);
            return userDAO.getUser(registerUser.getUsername());
        }
    }

    public void isValidUser(UserData user) throws BadRequestException, UserExistsException, DataAccessException {
        UserData isValidUser = userDAO.getUser(user.getUsername());
        if (isValidUser == null || !user.getPassword().equals(isValidUser.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }
    public void isValidPassword(UserData user) throws UnauthorizedException, DataAccessException {
        UserData isValidPassword = userDAO.getUser(user.getUsername());
        if (isValidPassword == null || !user.getPassword().equals(isValidPassword.getPassword())) {
            throw new UnauthorizedException("unauthorized");
        }
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
    }
}