package projects.todolistapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projects.todolistapp.model.entity.Role;

@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}