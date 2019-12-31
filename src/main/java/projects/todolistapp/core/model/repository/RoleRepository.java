package projects.todolistapp.core.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projects.todolistapp.core.model.entity.Role;

@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}