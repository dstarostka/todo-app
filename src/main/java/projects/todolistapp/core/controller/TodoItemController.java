package projects.todolistapp.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import projects.todolistapp.core.model.entity.TodoItem;
import projects.todolistapp.core.service.TodoItemService;
import projects.todolistapp.core.util.Mappings;
import projects.todolistapp.core.util.ViewNames;

import java.time.LocalDate;

@Slf4j
@Controller
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping(Mappings.ITEMS)
    String items(Model model) {
        model.addAttribute("items", todoItemService.getItems());

        return ViewNames.ITEMS;
    }

    @GetMapping(Mappings.ADD_ITEM)
    String addItem(Model model) {
        TodoItem item = new TodoItem("", "", LocalDate.now());
        model.addAttribute("todoItem", item);
        return ViewNames.ADD_ITEM;
    }

    @PostMapping(Mappings.ADD_ITEM)
    String submitItem(@ModelAttribute TodoItem item) {
        todoItemService.addItem(item);
        return "redirect:/" + ViewNames.ITEMS;
    }
}