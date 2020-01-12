package projects.todolistapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.todolistapp.model.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);
    User findByEmail(String email);
    User findByUsername(String username);
    User findByConfirmationToken(String confirmationToken);
}