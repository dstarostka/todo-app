package projects.todolistapp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.todolistapp.core.model.entity.TodoItem;
import projects.todolistapp.core.model.repository.TodoItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    public List<TodoItem> getItems() {
        List<TodoItem> items = new ArrayList();
        for(TodoItem item : todoItemRepository.findAll()) {
            items.add(item);
        }
        return items;
    }

    @Override
    public void addItem(TodoItem item) {
        todoItemRepository.save(item);
    }

    @Override
    public void removeItem(int id) {
        todoItemRepository.deleteById(id);
    }

    @Override
    public TodoItem getItem(int id) {
        return todoItemRepository.findById(id);
    }
}