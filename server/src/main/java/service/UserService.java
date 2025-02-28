package service;
import dataaccess.*;

public class UserService {
    //register method within userService-> calling DAO
    private final UserDAO data;

    public UserService(UserDAO data) {
        this.data = data;
    }
}

