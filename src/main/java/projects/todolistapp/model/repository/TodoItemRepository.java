package projects.todolistapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projects.todolistapp.model.entity.TodoItem;

@Repository("todoItemRepository")
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    TodoItem findById(int id);
    void deleteById(int id);
    TodoItem save(TodoItem item);
}