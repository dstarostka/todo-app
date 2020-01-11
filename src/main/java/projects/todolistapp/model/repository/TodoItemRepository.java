package projects.todolistapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projects.todolistapp.model.entity.TodoItem;
import java.util.List;

@Repository("todoItemRepository")
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    TodoItem findById(int id);
    TodoItem save(TodoItem item);
    void deleteById(int id);
    List<TodoItem> findByUserUsername(String username);
}