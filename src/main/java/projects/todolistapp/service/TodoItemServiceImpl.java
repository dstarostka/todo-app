package projects.todolistapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.todolistapp.model.DTO.TodoItemDTO;
import projects.todolistapp.model.entity.TodoItem;
import projects.todolistapp.model.entity.User;
import projects.todolistapp.model.repository.TodoItemRepository;
import projects.todolistapp.model.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    private TodoItemRepository todoItemRepository;
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public TodoItemServiceImpl(TodoItemRepository todoItemRepository, UserService userService, UserRepository userRepository) {
        this.todoItemRepository = todoItemRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public List<TodoItem> getItemsByUsername() {
        List<TodoItem> items = new ArrayList();
        for(TodoItem item : todoItemRepository.findByUserUsername(userService.getLoggedInUsername())) {
            items.add(item);
        }
        return items;
    }

    @Override
    public TodoItem getItem(int id) {
        return todoItemRepository.findById(id);
    }

    @Override
    public TodoItem addItem(TodoItem item) {
        User user = userRepository.findByUsername(userService.getLoggedInUsername());
        item.setUser(user);

        return todoItemRepository.save(item);
    }

    public TodoItem updateItem(int id, TodoItemDTO item) {
        TodoItem itemToUpdate = TodoItem.builder()
                .id(id)
                .title(item.getTitle())
                .details(item.getDetails())
                .deadline(item.getDeadline())
                .build();

        return todoItemRepository.save(itemToUpdate);
    }

    @Override
    public void removeItem(int id) {
        todoItemRepository.deleteById(id);
    }
}