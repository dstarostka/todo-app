package projects.todolistapp.model.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TodoItemDTO {
    private String title;
    private String details;
    private LocalDate deadline;
}
