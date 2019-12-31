package projects.todolistapp.core.service;

import projects.todolistapp.core.model.entity.TodoItem;
import java.util.List;

public interface TodoItemService {

    List<TodoItem> getItems();

    void addItem(TodoItem item);

    void removeItem(int id);

    TodoItem getItem(int id);
}