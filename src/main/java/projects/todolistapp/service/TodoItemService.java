package projects.todolistapp.service;

import projects.todolistapp.model.entity.TodoItem;
import java.util.List;

public interface TodoItemService {

    List<TodoItem> getItems();

    TodoItem addItem(TodoItem item);

    void removeItem(int id);

    TodoItem getItem(int id);
}