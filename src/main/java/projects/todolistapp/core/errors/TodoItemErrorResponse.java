package projects.todolistapp.core.errors;

import lombok.Data;

@Data
public class TodoItemErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
