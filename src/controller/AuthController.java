package controller;

import dao.UserDAO;
import model.User;

public class AuthController {
    private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    public boolean register(String fullName, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password); // Add hashing here for production
        return userDAO.registerUser(user);
    }

    public User login(String email, String password) {
        return userDAO.authenticateUser(email, password);
    }

    public boolean adminLogin(String username, String password) {
        return userDAO.authenticateAdmin(username, password);
    }
}
