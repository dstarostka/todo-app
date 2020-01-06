package projects.todolistapp.service;

import projects.todolistapp.model.entity.User;

public interface UserService {
    User findByEmail(String email);
    User findByConfirmationToken(String confirmationToken);
    void saveUser(User user);
}