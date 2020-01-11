package projects.todolistapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import projects.todolistapp.model.DTO.TodoItemDTO;
import projects.todolistapp.model.entity.TodoItem;
import projects.todolistapp.service.TodoItemService;
import projects.todolistapp.service.UserServiceImpl;
import projects.todolistapp.util.Mappings;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = Mappings.ITEMS, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TodoItemsController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping
    public ResponseEntity<CollectionModel<TodoItem>> getItems() {
        String loggedInUsername = UserServiceImpl.getLoggedInUserName();

        List<TodoItem> items = todoItemService.getItemsByUsername(loggedInUsername);
        items.forEach(item -> item.add(linkTo(TodoItemsController.class).slash(item.getId()).withSelfRel()));
        Link link = linkTo(TodoItemsController.class).withSelfRel();
        CollectionModel<TodoItem> collectionModel = new CollectionModel<>(items, link);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TodoItem>> getItem(@PathVariable int id) {
        TodoItem item = todoItemService.getItem(id);
        item.add(linkTo(TodoItemsController.class).slash(id).withSelfRel());
        Link link = linkTo(TodoItemsController.class).withSelfRel();
        EntityModel<TodoItem> todoItemEntityModel = new EntityModel<>(item, link);

        return new ResponseEntity<>(todoItemEntityModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<TodoItem>> createItem(@RequestBody TodoItemDTO itemDTO) {
        TodoItem itemToSave = TodoItem.builder()
                .title(itemDTO.getTitle())
                .details(itemDTO.getDetails())
                .deadline(itemDTO.getDeadline())
                .build();

        TodoItem savedItem = todoItemService.addItem(itemToSave);

        Link link = linkTo(TodoItemsController.class).slash(savedItem.getId()).withSelfRel();
        EntityModel<TodoItem> todoItemEntityModel = new EntityModel<>(savedItem, link);

        return new ResponseEntity<>(todoItemEntityModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TodoItem>> updateItem(@PathVariable int id, @RequestBody TodoItemDTO itemDTO) {
        TodoItem updatedItem = todoItemService.updateItem(id, itemDTO);

        Link link = linkTo(TodoItemsController.class).slash(id).withSelfRel();
        EntityModel<TodoItem> todoItemEntityModel = new EntityModel<>(updatedItem, link);

        return new ResponseEntity<>(todoItemEntityModel, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable int id) {
        todoItemService.removeItem(id);
        Link link = linkTo(TodoItemsController.class).withSelfRel();

        return new ResponseEntity<>(link, HttpStatus.OK);
    }
}