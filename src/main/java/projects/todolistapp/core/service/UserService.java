package projects.todolistapp.core.service;

import projects.todolistapp.core.model.entity.User;

public interface UserService {

    User findByEmail(String email);
    User findByConfirmationToken(String confirmationToken);
    void saveUser(User user);
}