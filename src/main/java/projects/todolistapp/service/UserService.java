package projects.todolistapp.service;

import projects.todolistapp.model.entity.User;

public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByConfirmationToken(String confirmationToken);
    void saveUser(User user);
    String getLoggedInUsername();
}