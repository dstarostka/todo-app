package projects.todolistapp.core.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projects.todolistapp.core.model.entity.TodoItem;

@Repository("todoItemRepository")
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    TodoItem findById(int id);
    void deleteById(int id);

}