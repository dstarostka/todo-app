package projects.todolistapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.todolistapp.model.DTO.TodoItemDTO;
import projects.todolistapp.model.entity.TodoItem;
import projects.todolistapp.service.TodoItemService;
import projects.todolistapp.util.Mappings;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = Mappings.ITEMS, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping
    public ResponseEntity<CollectionModel<TodoItem>> getItems() {
        List<TodoItem> items = todoItemService.getItems();
        items.forEach(item -> item.add(linkTo(TodoItemController.class).slash(item.getId()).withSelfRel()));
        Link link = linkTo(TodoItemController.class).withSelfRel();
        CollectionModel<TodoItem> collectionModel = new CollectionModel<>(items, link);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TodoItem>> getItemById(@PathVariable int id) {
        TodoItem item = todoItemService.getItem(id);
        Link link = linkTo(TodoItemController.class).slash(id).withSelfRel();
        EntityModel<TodoItem> todoItemEntityModel = new EntityModel<>(item, link);

        return new ResponseEntity<>(todoItemEntityModel, HttpStatus.OK);
    }

    @PostMapping
    public TodoItem  addItem(@RequestBody TodoItemDTO itemDTO) {
        TodoItem item = TodoItem.builder()
                .title(itemDTO.getTitle())
                .details(itemDTO.getDetails())
                .deadline(itemDTO.getDeadline())
                .build();

        return todoItemService.addItem(item);
    }
}