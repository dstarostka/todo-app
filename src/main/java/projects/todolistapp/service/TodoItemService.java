package projects.todolistapp.service;

import projects.todolistapp.model.DTO.TodoItemDTO;
import projects.todolistapp.model.entity.TodoItem;
import java.util.List;

public interface TodoItemService {
    List<TodoItem> getItemsByUsername();
    TodoItem addItem(TodoItem item);
    TodoItem getItem(int id);
    TodoItem updateItem(int id, TodoItemDTO itemDTO);
    void removeItem(int id);
}