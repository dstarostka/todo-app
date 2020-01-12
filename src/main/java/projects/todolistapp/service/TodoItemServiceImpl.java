package projects.todolistapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.todolistapp.exceptions.TodoItemNotFoundException;
import projects.todolistapp.model.DTO.TodoItemDTO;
import projects.todolistapp.model.entity.TodoItem;
import projects.todolistapp.model.entity.User;
import projects.todolistapp.model.repository.TodoItemRepository;
import projects.todolistapp.model.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository userRepository;

    public List<TodoItem> getItemsByUsername(String username) {
        List<TodoItem> items = new ArrayList();
        for(TodoItem item : todoItemRepository.findByUserUsername(username)) {
            items.add(item);
        }
        return items;
    }

    @Override
    public TodoItem getItem(int id) {
        return getTodoItemByUserIdOrThrow(id);
    }

    @Override
    public TodoItem addItem(TodoItem item) {
        String loggedInUsername = UserServiceImpl.getLoggedInUserName();
        User user = userRepository.findByUsername(loggedInUsername);
        item.setUser(user);

        return todoItemRepository.save(item);
    }

    public TodoItem updateItem(int id, TodoItemDTO item) {
        TodoItem itemToUpdate = getTodoItemByUserIdOrThrow(id);

        itemToUpdate.setTitle(item.getTitle());
        itemToUpdate.setDetails(item.getDetails());
        itemToUpdate.setDeadline(item.getDeadline());

        return todoItemRepository.save(itemToUpdate);
    }

    @Override
    public void removeItem(int id) {
        getTodoItemByUserIdOrThrow(id);
        todoItemRepository.deleteById(id);
    }

    private TodoItem getTodoItemByUserIdOrThrow(int id) {
        String loggedInUserName = UserServiceImpl.getLoggedInUserName();
        int loggedInUserId = userServiceImpl.getUserId(loggedInUserName);
        TodoItem todoItem = todoItemRepository.findById(id);

        if(loggedInUserId != todoItem.getUser().getId()) {
            throw new TodoItemNotFoundException("Todo item not found");
        }
        return todoItem;
    }
}